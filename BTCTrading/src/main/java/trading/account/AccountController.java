package trading.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import helper.HttpResponse;
import helper.Repository;
import helper.ResponseGenerator;
import trading.Controller;
import trading.NoDataException;

@RestController
public class AccountController extends Controller{

	private ResponseGenerator response = new ResponseGenerator();

	AccountController(AccountRepository repository) {
		super(repository);
	}

	@PostMapping("/accounts")
	public ResponseEntity<String> createAccount(@RequestBody Account account) {		
		if(account.getAccount_id() != null){
			response.setStatus(HttpResponse.BAD_REQUEST);
			response.setMessage("Please provide only name and USB balance.");
		}else {
			response.clearResponse();
			if(account.getBalance_btc() != 0){ 
				account.setBalance_btc(0);
				response.setMessage("BTC balance can not be set upon account creation. BTC balance was set to 0.");
			}
			accountRepository.save(account);
			response.appendMessage("Account created: " + account.toString());
		}
		return response.getAndClearResponse();   
	}
	
	@GetMapping("/accounts/{account_id}")
	public ResponseEntity<String> fetchAccountDetails(@PathVariable("account_id") Long account_id) { 
		if(account_id <= 0){ 
			response.setStatus(HttpResponse.BAD_REQUEST);
			response.setMessage("Account id must be greater than 0.");	
		}else {
			response.setStatus(HttpResponse.OK);
			Optional<Account> account = accountRepository.findById(account_id);
			if(!account.isPresent()) {
				response.setMessage("Account with id " + account_id + " doesn't exist.");
			}else {
				response.setMessage(account.get().toString());
			}
		}
		return response.getAndClearResponse();
	}
	
	// convenience method
	@GetMapping("/allAccounts")
	public ResponseEntity<String> allAccounts() { 
		response.setStatus(HttpResponse.OK);
		List<Account> accounts = new ArrayList<>();
		try {
			accounts = getAll(Repository.ACCOUNT);
		} catch (NoDataException e) {
			response.setMessage(e.getMessage());
		}
		if(accounts.isEmpty()) {
			response.setMessage("There are no accounts.");
		}else {
			response.setMessage(accounts.toString());
		}
		return response.getAndClearResponse();
	}
	
}
