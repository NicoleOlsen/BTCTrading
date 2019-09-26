package account;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import helper.AbstractTest;
import trading.account.Account;

public class AccountControllerTest extends AbstractTest {

	private final String uri = "/accounts";

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void okGetAccount() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("1", 200, uri);
		assertEquals(content, "Account [account_id=1, name=Kim, balance_usd=798.89, balance_btc=0.0]");
	}

	@Test
	public void okGetAccountDoesNotExist() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("999", 200, uri);
		assertEquals(content, "Account with id 999 doesn't exist.");
	}

	@Test
	public void badRequestGetAccountNegativeId() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("-1", 400, uri);
		assertEquals(content, "Account id must be greater than 0.");
	}

	@Test
	public void okAccountCreated() throws Exception {
		Account account = new Account.Builder().withName("Kim").withUsdBalance(798.89).build();
		String content = checkStatusAndReturnContentForPostResult(account, 200, uri);
		assertEquals(content.substring(0, 38).trim(), "Account created: Account [account_id=");
		assertEquals(content.substring(40, content.length()).trim(), "name=Kim, balance_usd=798.89, balance_btc=0.0]");
	}

	@Test
	public void okAccountCreatedNegativeBalance() throws Exception {
		Account account = new Account.Builder().withName("Kim").withUsdBalance(-798.89).build();
		String content = checkStatusAndReturnContentForPostResult(account, 200, uri);
		assertEquals(content.substring(0, 38).trim(), "Account created: Account [account_id=");
		assertEquals(content.substring(40, content.length()).trim(), "name=Kim, balance_usd=-798.89, balance_btc=0.0]");
	}

	@Test
	public void badRequestAccountNotCreated() throws Exception {
		Account account = new Account.Builder().withAccountId(3L).withName("Kim").withUsdBalance(798.89).build();
		String content = checkStatusAndReturnContentForPostResult(account, 400, uri);
		assertEquals(content, "Please provide only name and USB balance.");
	}

	@Test
	public void okBtcBalanceProvidedAccountCreated() throws Exception {
		Account account = new Account.Builder().withName("Kim").withUsdBalance(798.89).withBtcBalance(79.89).build();
		String content = checkStatusAndReturnContentForPostResult(account, 200, uri);
		assertEquals(content.substring(0, 76).trim(),
				"BTC balance can not be set upon account creation. BTC balance was set to 0.");
		assertEquals(content.substring(76, 113).trim(), "Account created: Account [account_id=");
		assertEquals(content.substring(115, content.length()).trim(), "name=Kim, balance_usd=798.89, balance_btc=0.0]");
	}
}