package limitOrder;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import helper.AbstractTest;

public class LimitOrderControllerTest extends AbstractTest {

	private final String uriOrders = "/orders";

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void okGetOrder() throws Exception {
		MvcResult mvcResult = getMvcGetResult(uriOrders, "2");
		int status = mvcResult.getResponse().getStatus();
		//assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		//assertEquals(content, "Account [account_id=1, name=Kim, balance_usd=798.89, balance_btc=0.0]");
	}

}
