package helper;

public class JsonHelper {
	
	public static String getJsonBodyWithMessage(String message, String body) {
		return "{ \"message\": { \"message\": \"" + message + "\" } , " + body + " } ";
	}
	
}
