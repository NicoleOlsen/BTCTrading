package helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import constants.Constants;

public class JsonHelperTest {
	
	private static JSONObject getJsonFromContent(String content, String toGet) throws ParseException {
		Object parsedContent = new JSONParser().parse(content);
		JSONObject jsonContent = (JSONObject) parsedContent;
		JSONObject jsonReturn = (JSONObject) jsonContent.get(toGet);
		assertNotNull(jsonReturn);
		return jsonReturn;
	}
	
	public static void assertJsonAccount(String content, String name, String usbBalance) throws Exception{
		JSONObject accountJson = getJsonFromContent(content, Constants.ACCOUNT);
		assertEquals((String) accountJson.get(Constants.NAME), name);
		assertEquals((String) accountJson.get(Constants.BALANCE_USD_SC), usbBalance);
		assertEquals((String) accountJson.get(Constants.BALANCE_BTC_SC), Constants.DOUBLE_ZERO);
	}
	
	public static void assertJsonMessage(String content, String expectedMessage) throws ParseException {
		JSONObject messageJson = getJsonFromContent(content, Constants.MESSAGE);
		assertEquals((String) messageJson.get(Constants.MESSAGE), expectedMessage);
	}
	
	public static void assertJsonNone(String content, String obj) throws ParseException {
		JSONObject orderJson = getJsonFromContent(content, obj);
		assertEquals((String) orderJson.get(obj), Constants.NONE);
	}
	
	public static void assertJsonOrder(String content, String priceLimit, String processed, String accountId) throws Exception{
		JSONObject orderJson = getJsonFromContent(content, Constants.ORDER);
		assertEquals((String) orderJson.get(Constants.PRICE_LIMIT), priceLimit);
		assertEquals((String) orderJson.get(Constants.PROCESSED), processed);
		assertEquals((String) orderJson.get(Constants.ACCOUNT_ID), accountId);
	}

}
