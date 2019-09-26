package account;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import helper.AbstractTest;
import trading.account.Account;

public class AccountControllerTest extends AbstractTest {

	private final String uriAccounts = "/accounts";

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	// TODO tests
	


//	curl -X POST localhost:8080/accounts -H 'Content-type:application/json' -d '{"name": "Sam", "balance_usd": "3478988"}'
//	curl -X POST localhost:8080/accounts -H 'Content-type:application/json' -d '{"account_id": "43", "name": "Sam", "balance_usd": "38", "balance_btc": "38"}'
//	curl -X POST localhost:8080/accounts -H 'Content-type:application/json' -d '{"name": "Sam", "balance_usd": "38", "balance_btc": "-8"}'
//	curl -X POST localhost:8080/accounts -H 'Content-type:application/json' -d '{"name": "Sam", "balance_usd": "-33", "balance_btc": "38"}'

	@Test
	public void okGetAccount() throws Exception {
		MvcResult mvcResult = getMvcGetResult(uriAccounts, "1");
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Account [account_id=1, name=Kim, balance_usd=798.89, balance_btc=0.0]");
	}

	@Test
	public void okGetAccountDoesNotExist() throws Exception {
		MvcResult mvcResult = getMvcGetResult(uriAccounts, "999");
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Account with id 999 doesn't exist.");
	}
	
	@Test
	public void badRequestGetAccountNegativeId() throws Exception {
		MvcResult mvcResult = getMvcGetResult(uriAccounts, "-2");
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Account id must be greater than 0.");
	}

	@Test
	public void okAccountCreated() throws Exception {
		Account account = new Account.Builder().withName("Kim").withUsdBalance(798.89).build();
		MvcResult mvcResult = getMvcPostResult(uriAccounts, account);
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content.substring(0, 38).trim(), "Account created: Account [account_id=");
		assertEquals(content.substring(40, content.length()).trim(), "name=Kim, balance_usd=798.89, balance_btc=0.0]");
	}
	
	@Test
	public void okAccountCreatedNegativeBalance() throws Exception {
		Account account = new Account.Builder().withName("Kim").withUsdBalance(-798.89).build();
		MvcResult mvcResult = getMvcPostResult(uriAccounts, account);
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content.substring(0, 38).trim(), "Account created: Account [account_id=");
		assertEquals(content.substring(40, content.length()).trim(), "name=Kim, balance_usd=-798.89, balance_btc=0.0]");
	}

	@Test
	public void badRequestAccountNotCreated() throws Exception {
		Account account = new Account.Builder().withAccountId(3L).withName("Kim").withUsdBalance(798.89).build();
		MvcResult mvcResult = getMvcPostResult(uriAccounts, account);
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Please provide only name and USB balance.");
	}

	@Test
	public void okBtcBalanceProvidedAccountCreated() throws Exception {
		Account account = new Account.Builder().withName("Kim").withUsdBalance(798.89).withBtcBalance(79.89).build();
		MvcResult mvcResult = getMvcPostResult(uriAccounts, account);
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content.substring(0, 76).trim(),
				"BTC balance can not be set upon account creation. BTC balance was set to 0.");
		assertEquals(content.substring(76, 113).trim(), "Account created: Account [account_id=");
		assertEquals(content.substring(115, content.length()).trim(), "name=Kim, balance_usd=798.89, balance_btc=0.0]");
	}
}