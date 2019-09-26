package limitOrder;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import helper.AbstractTest;
import trading.limitOrder.LimitOrder;

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
		assertEquals(content, "Account with id 999 does not exists.");
	}

	@Test
	public void okGetOrderLimitNotExceeded() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("3", 200, uri);
		assertEquals(content.substring(0, 20).trim(), "Account [account_id=");
		assertEquals(content.substring(70, 93).trim(), "- LimitOrder [order_id=");
		assertEquals(content.substring(114, 129).trim(), "processed=false");
	}

	@Test
	public void okGetOrderLimitExceeded() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("2", 200, uri);
		assertEquals(content.substring(0, 20).trim(), "Account [account_id=");
		assertEquals(content.substring(70, 93).trim(), "- LimitOrder [order_id=");
		assertEquals(content.substring(117, 131).trim(), "processed=true");
	}

	@Test
	public void okGetOrderDoesNotExist() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("999", 200, uri);
		assertEquals(content, "Order with id 999 does not exist.");
	}

	@Test
	public void badRequestGetOrderNegativeId() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("-2", 400, uri);
		assertEquals(content, "Order id must be greater than 0.");
	}

	@Test
	public void okOrderCreated() throws Exception {
		LimitOrder order = new LimitOrder.Builder().withPriceLimit(3299.89).withAccountId(3L).build();
		String content = checkStatusAndReturnContentForPostResult(order, 200, uri);
		assertEquals(content.substring(0, 36).trim(), "Order created: LimitOrder [order_id=");
		assertEquals(content.substring(38, content.length()).trim(),
				"price_limit=3299.89, processed=false, account_id=3]");
	}

	@Test
	public void okOrderCreatedNegativeLimit() throws Exception {
		LimitOrder order = new LimitOrder.Builder().withPriceLimit(-3577.89).withAccountId(3L).build();
		String content = checkStatusAndReturnContentForPostResult(order, 400, uri);
		assertEquals(content, "Price limit must not be negative.");
	}

	@Test
	public void badRequestOrderNotCreatedDueToOrderIdBeingProvided() throws Exception {
		LimitOrder order = new LimitOrder.Builder().withOrderId(1L).withPriceLimit(-3577.89).withAccountId(3L).build();
		String content = checkStatusAndReturnContentForPostResult(order, 400, uri);
		assertEquals(content, "Please provide only price limit and account ID.");
	}

	@Test
	public void badRequestOrderNotCreatedDueToOrderBeingProcessed() throws Exception {
		LimitOrder order = new LimitOrder.Builder().withPriceLimit(-3577.89).isProcessed(true).withAccountId(3L)
				.build();
		String content = checkStatusAndReturnContentForPostResult(order, 400, uri);
		assertEquals(content, "Please provide only price limit and account ID.");
	}
}
