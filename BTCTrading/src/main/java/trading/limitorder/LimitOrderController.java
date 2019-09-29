package trading.limitOrder;

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

import customExceptions.AccountNotFoundException;
import customExceptions.InsufficientFundsException;
import customExceptions.NoDataException;
import helper.Controller;
import helper.HttpResponse;
import helper.Repository;
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
		if (limitOrder.getOrder_id() != null || limitOrder.isProcessed()) {
			response.setStatus(HttpResponse.BAD_REQUEST);
			response.setMessage("Please provide only price limit and account ID.");
		} else if (limitOrder.getPrice_limit() < 0) {
			response.setStatus(HttpResponse.BAD_REQUEST);
			response.setMessage("Price limit must not be negative.");
		} else {
			limitOrderRepository.save(limitOrder);
			response.setStatus(HttpResponse.OK);
			response.setMessage("Order created: " + limitOrder.toString());
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
	public ResponseEntity<String> fetchOrderDetails(@PathVariable("order_id") Long order_id) {
		if (order_id <= 0) {
			response.setStatus(HttpResponse.BAD_REQUEST);
			response.setMessage("Order id must be greater than 0.");
		} else {
			response.setStatus(HttpResponse.OK);
			Optional<LimitOrder> loOpt = limitOrderRepository.findById(order_id);
			if (loOpt.isPresent()) {
				Quote quote = Market.getCurrentMarketData();
				if (quote != null && quote.isEmpty()) {
					response.setMessage("Market data is not available.");
				} else {
					double currentPrice = Double.valueOf(quote.getPrice());
					LimitOrder lo = loOpt.get();
					double limit = lo.getPrice_limit();
					try {
						Account acc = LimitOrderHelper.getAccountFromOrder(lo, accountRepository);
						if (currentPrice < limit) {
							LimitOrderHelper.updateAccount(currentPrice, acc);
							lo.setProcessed(true);
						}
						response.setMessage(acc.toString() + " - " + lo.toString());
					} catch (InsufficientFundsException | AccountNotFoundException e) {
						log.info(e.getMessage());
						response.setMessage(e.getMessage());
					}
				}
			} else {
				response.setMessage("Order with id " + order_id + " does not exist.");
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
			response.setMessage(e.getMessage());
		}
		if (orders.isEmpty()) {
			response.setMessage("There are no orders.");
		} else {
			response.setMessage(orders.toString());
		}
		return response.getAndClearResponse();
	}

}
