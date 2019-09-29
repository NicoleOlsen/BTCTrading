package trading.limitorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import constants.Constants;
import customexceptions.AccountNotFoundException;
import customexceptions.InsufficientFundsException;
import customexceptions.NoDataException;
import enums.HttpResponse;
import enums.Repository;
import helper.Controller;
import helper.JsonHelper;
import helper.ResponseGenerator;
import market.Market;
import market.Quote;
import trading.Application;
import trading.account.Account;
import trading.account.AccountRepository;

@RestController
public class LimitOrderController extends Controller {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	private ResponseGenerator response = new ResponseGenerator();

	LimitOrderController(LimitOrderRepository limitOrderRepository, AccountRepository accountRepository) {
		super(limitOrderRepository, accountRepository);
	}

	@PostMapping("/limitOrders")
	public ResponseEntity<String> createLimitOrder(@RequestBody LimitOrder limitOrder) {
		if (limitOrder.getOrderId() != null || limitOrder.isProcessed()) {
			response.setStatus(HttpResponse.BAD_REQUEST);
			response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.PROVIDE_ONLY_PRICE_LIMIT_AND_ACCOUNT_ID, Constants.ORDER_NONE));
		} else if (limitOrder.getPriceLimit() < 0) {
			response.setStatus(HttpResponse.BAD_REQUEST);
			response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.PRICE_LIMIT_MUST_NOT_BE_NEGATIVE, Constants.ORDER_NONE));
		} else {
			limitOrderRepository.save(limitOrder);
			response.setStatus(HttpResponse.OK);
			response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.ORDER_CREATED, limitOrder.toString()));
		}
		return response.getAndClearResponse();
	}

	/*
	 * This method returns the order, which was requested by input parameter
	 * representing its ID. It also returns the corresponding account. Price limit
	 * is compared with the current market price before returning. If current price
	 * is lower than price limit, order will be executed. With order execution, we
	 * mark order as processed and populate account balances based on the current
	 * market price.
	 */
	@GetMapping("/limitOrders/{order_id}")
	public ResponseEntity<String> fetchOrderDetails(@PathVariable("order_id") Long orderId) {
		if (orderId <= 0) {
			response.setStatus(HttpResponse.BAD_REQUEST);
			response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.ORDER_ID_MUST_BE_GREATER_THAN_ZERO, Constants.ORDER_NONE));
		} else {
			response.setStatus(HttpResponse.OK);
			Optional<LimitOrder> loOpt = limitOrderRepository.findById(orderId);
			if (loOpt.isPresent()) {
				Quote quote = Market.getCurrentMarketData();
				if (quote != null && quote.isEmpty()) {
					response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.MARKET_DATA_IS_NOT_AVAILABLE, Constants.ORDER_NONE));
				} else {
					double currentPrice = Double.valueOf(quote.getPrice());
					LimitOrder lo = loOpt.get();
					double limit = lo.getPriceLimit();
					try {
						Account acc = LimitOrderHelper.getAccountFromOrder(lo, accountRepository);
						if (currentPrice < limit) {
							LimitOrderHelper.updateAccount(currentPrice, acc);
							lo.setProcessed(true);
						}
						response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.NONE, acc.toString() + Constants.COMMA_SPACE + lo.toString()));
					} catch (InsufficientFundsException | AccountNotFoundException e) {
						log.info(e.getMessage());
						response.setMessage(JsonHelper.getJsonBodyWithMessage(e.getMessage(), Constants.ORDER_NONE));
					}
				}
			} else {
				response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.ORDER_WITH_ID + orderId + Constants.DOES_NOT_EXIST, Constants.ORDER_NONE));
			}
		}
		return response.getAndClearResponse();
	}

	// convenience method
	@GetMapping("/allLimitOrders")
	public ResponseEntity<String> allLimitOrders() {
		response.setStatus(HttpResponse.OK);
		List<LimitOrder> orders = new ArrayList<>();
		try {
			orders = getAll(Repository.ORDER);
		} catch (NoDataException e) {
			response.setMessage(JsonHelper.getJsonBodyWithMessage(e.getMessage(), Constants.ORDER_NONE));
		}
		if (orders.isEmpty()) {
			response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.THERE_ARE_NO_ORDERS, Constants.ORDER_NONE));
		} else {
			response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.NONE, orders.toString()));
		}
		return response.getAndClearResponse();
	}

}
