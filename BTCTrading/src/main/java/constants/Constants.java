package constants;

public class Constants {
	  
	// General constants
	public static final String SLASH = "/";
	public static final String NONE = "None";
	public static final String MESSAGE = "message";
	public static final String DOES_NOT_EXIST = " does not exist.";
	public static final String DOUBLE_ZERO = "0.0";
	public static final String COMMA_SPACE = ", ";
	public static final String NO_DATA_FOUND_FOR = "No data found for ";
	
	// URI constants
	public static final String URI_ACCOUNTS = "/accounts";
	
	// Account constants
	public static final String ACCOUNT_NONE = "\"account\": { \"account\": \"None\" }";

	
	// Account fields
	public static final String ACCOUNT = "account";
	public static final String NAME = "name";
	public static final String ACCOUNT_ID_SC = "account_id";
	public static final String BALANCE_USD_SC = "balance_usd";
	public static final String BALANCE_BTC_SC = "balance_btc";
	
	// Account messages
	public static final String ACCOUNT_WITH_ID = "Account with id ";
	public static final String ACCOUNT_CREATED = "Account created.";
	public static final String ACCOUNT_ID_MUST_BE_GREATHER_THAN_ZERO = "Account id must be greater than 0.";
	public static final String THERE_ARE_NO_ACCOUNTS = "There are no accounts.";
	public static final String PROVIDE_ONLY_NAME_AND_BALANCE = "Please provide only name and USD balance.";
	public static final String BALANCE_WAS_SET_TO_ZERO = "BTC balance can not be set upon account creation. BTC balance was set to 0.";

	// Order constants
	public static final String ORDER_NONE = "\"order\": { \"order\": \"None\" }";
	
	// Order fields
	public static final String ORDER = "order";
	public static final String PRICE_LIMIT = "price_limit";
	public static final String PROCESSED = "processed";
	public static final String ACCOUNT_ID = "account_id";
	
	// Order messages
	public static final String ORDER_CREATED = "Order created.";
	public static final String ORDER_WITH_ID = "Order with id ";
	public static final String PROVIDE_ONLY_PRICE_LIMIT_AND_ACCOUNT_ID = "Please provide only price limit and account ID.";
	public static final String PRICE_LIMIT_MUST_NOT_BE_NEGATIVE = "Price limit must not be negative.";
	public static final String ORDER_ID_MUST_BE_GREATER_THAN_ZERO = "Order id must be greater than 0.";
	public static final String MARKET_DATA_IS_NOT_AVAILABLE = "Market data is not available.";
	public static final String THERE_ARE_NO_ORDERS = "There are no orders.";
	public static final String EXCHANGE_NOT_POSSIBLE = "Exchange is not possible due to insufficient funds.";
}
