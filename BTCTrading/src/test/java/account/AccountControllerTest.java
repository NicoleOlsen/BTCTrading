package account;

import static org.junit.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import constants.Constants;
import helper.AbstractTest;
import trading.account.Account;

public class AccountControllerTest extends AbstractTest {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void okGetAccount() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("1", 200, Constants.URI_ACCOUNTS);
		assertJsonAccount(content, "Kim", "798.89");
		assertJsonMessage(content, Constants.NONE);
	}

	@Test
	public void okGetAccountDoesNotExist() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("999", 200, Constants.URI_ACCOUNTS);
		assertJsonAccountNone(content);
		assertJsonMessage(content, Constants.ACCOUNT_WITH_ID + "999" + Constants.DOES_NOT_EXIST);
	}

	@Test
	public void badRequestGetAccountNegativeId() throws Exception {
		String content = checkStatusAndReturnContentForGetResult("-1", 400, Constants.URI_ACCOUNTS);
		assertJsonAccountNone(content);
		assertJsonMessage(content, Constants.ACCOUNT_ID_MUST_BE_GREATHER_THAN_ZERO);
	}

	@Test
	public void okAccountCreated() throws Exception {
		Account account = new Account.Builder().withName("Kim").withUsdBalance(7990.89).build();
		String content = checkStatusAndReturnContentForPostResult(account, 200, Constants.URI_ACCOUNTS);
		assertJsonAccount(content, "Kim", "7990.89");
		assertJsonMessage(content, Constants.ACCOUNT_CREATED);
	}

	@Test
	public void okAccountCreatedNegativeBalance() throws Exception {
		Account account = new Account.Builder().withName("Kim").withUsdBalance(-798.89).build();
		String content = checkStatusAndReturnContentForPostResult(account, 200, Constants.URI_ACCOUNTS);
		assertJsonAccount(content, "Kim", "-798.89");
		assertJsonMessage(content, Constants.ACCOUNT_CREATED);
	}

	@Test
	public void badRequestAccountNotCreated() throws Exception {
		Account account = new Account.Builder().withAccountId(3L).withName("Kim").withUsdBalance(798.89).build();
		String content = checkStatusAndReturnContentForPostResult(account, 400, Constants.URI_ACCOUNTS);
		assertJsonAccountNone(content);
		assertJsonMessage(content, Constants.PROVIDE_ONLY_NAME_AND_BALANCE);
	}

	@Test
	public void okBtcBalanceProvidedAccountCreated() throws Exception {
		Account account = new Account.Builder().withName("Kim").withUsdBalance(798.89).withBtcBalance(79.89).build();
		String content = checkStatusAndReturnContentForPostResult(account, 200, Constants.URI_ACCOUNTS);
		assertJsonAccount(content, "Kim", "798.89");
		assertJsonMessage(content, Constants.BALANCE_WAS_SET_TO_ZERO + " " + Constants.ACCOUNT_CREATED);
	}
	
	private void assertJsonAccountNone(String content) throws ParseException {
		JSONObject accountJson = getJsonFromContent(content, Constants.ACCOUNT);
		assertEquals((String) accountJson.get(Constants.ACCOUNT), Constants.NONE);
	}
	
	private void assertJsonAccount(String content, String name, String usbBalance) throws Exception{
		JSONObject accountJson = getJsonFromContent(content, Constants.ACCOUNT);
		assertEquals((String) accountJson.get(Constants.NAME), name);
		assertEquals((String) accountJson.get(Constants.BALANCE_USD_SC), usbBalance);
		assertEquals((String) accountJson.get(Constants.BALANCE_BTC_SC), "0.0");
	}
	
	private void assertJsonMessage(String content, String expectedMessage) throws ParseException {
		JSONObject messageJson = getJsonFromContent(content, Constants.MESSAGE);
		assertEquals((String) messageJson.get(Constants.MESSAGE), expectedMessage);
	}
}