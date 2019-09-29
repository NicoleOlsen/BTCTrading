package limitOrder;

import org.junit.Before;
import org.junit.Test;

import constants.Constants;
import helper.AbstractTest;
import helper.JsonHelperTest;
import trading.limitorder.LimitOrder;

public class LimitOrderControllerTest extends AbstractTest {

	private final String uri = "/limitOrders";

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void okGetOrderAccountDoesNotExist() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("4", 200, uri);
		JsonHelperTest.assertJsonNone(content, Constants.ORDER);
		JsonHelperTest.assertJsonMessage(content, Constants.ACCOUNT_WITH_ID + "999" + Constants.DOES_NOT_EXIST);
	}

	@Test
	public void okGetOrderLimitNotExceeded() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("3", 200, uri);
		JsonHelperTest.assertJsonNone(content, Constants.MESSAGE);
		JsonHelperTest.assertJsonAccount(content, "Kim", "798.89");
		JsonHelperTest.assertJsonOrder(content, "0.78", "false", "1");
	}

	@Test
	public void okGetOrderLimitExceeded() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("2", 200, uri);
		JsonHelperTest.assertJsonNone(content, Constants.MESSAGE);
		JsonHelperTest.assertJsonAccount(content, "Kim", "798.89");
		JsonHelperTest.assertJsonOrder(content, "9999.78", "true", "1");
	}

	@Test
	public void okGetOrderDoesNotExist() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("999", 200, uri);
		JsonHelperTest.assertJsonNone(content, Constants.ORDER);
		JsonHelperTest.assertJsonMessage(content, Constants.ORDER_WITH_ID + "999" + Constants.DOES_NOT_EXIST);  
	}

	@Test
	public void badRequestGetOrderNegativeId() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("-2", 400, uri);
		JsonHelperTest.assertJsonNone(content, Constants.ORDER);
		JsonHelperTest.assertJsonMessage(content, Constants.ORDER_ID_MUST_BE_GREATER_THAN_ZERO);
	}

	@Test
	public void okOrderCreated() throws Exception {
		LimitOrder order = new LimitOrder.Builder().withPriceLimit(3299.89).withAccountId(3L).build();
		String content = checkStatusAndReturnContentForPostResult(order, 200, uri);
		JsonHelperTest.assertJsonMessage(content, Constants.ORDER_CREATED);
		JsonHelperTest.assertJsonOrder(content, "3299.89", "false", "3");
	}

	@Test
	public void okOrderCreatedNegativeLimit() throws Exception {
		LimitOrder order = new LimitOrder.Builder().withPriceLimit(-3577.89).withAccountId(3L).build();
		String content = checkStatusAndReturnContentForPostResult(order, 400, uri);
		JsonHelperTest.assertJsonNone(content, Constants.ORDER);
		JsonHelperTest.assertJsonMessage(content, Constants.PRICE_LIMIT_MUST_NOT_BE_NEGATIVE);
	}

	@Test
	public void badRequestOrderNotCreatedDueToOrderIdBeingProvided() throws Exception {
		LimitOrder order = new LimitOrder.Builder().withOrderId(1L).withPriceLimit(-3577.89).withAccountId(3L).build();
		String content = checkStatusAndReturnContentForPostResult(order, 400, uri);
		JsonHelperTest.assertJsonNone(content, Constants.ORDER);
		JsonHelperTest.assertJsonMessage(content, Constants.PROVIDE_ONLY_PRICE_LIMIT_AND_ACCOUNT_ID);
	}

	@Test
	public void badRequestOrderNotCreatedDueToOrderBeingProcessed() throws Exception {
		LimitOrder order = new LimitOrder.Builder().withPriceLimit(-3577.89).isProcessed(true).withAccountId(3L)
				.build();
		String content = checkStatusAndReturnContentForPostResult(order, 400, uri);
		JsonHelperTest.assertJsonNone(content, Constants.ORDER);
		JsonHelperTest.assertJsonMessage(content, Constants.PROVIDE_ONLY_PRICE_LIMIT_AND_ACCOUNT_ID);
	}
}
