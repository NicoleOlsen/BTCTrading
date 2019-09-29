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

import constants.Constants;
import customexceptions.NoDataException;
import enums.HttpResponse;
import enums.Repository;
import helper.Controller;
import helper.ResponseGenerator;
import helper.JsonHelper;

@RestController
public class AccountController extends Controller {

	private ResponseGenerator response = new ResponseGenerator();

	AccountController(AccountRepository repository) {
		super(repository);
	}

	@PostMapping("/accounts")
	public ResponseEntity<String> createAccount(@RequestBody Account account) {
		String submessage = "";
		if (account.getAccountId() != null) {
			response.setStatus(HttpResponse.BAD_REQUEST);
			response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.PROVIDE_ONLY_NAME_AND_BALANCE, Constants.ACCOUNT_NONE));
		} else {
			response.clearResponse();
			if (account.getBalanceBtc() != 0) {
				account.setBalanceBtc(0);
				submessage = Constants.BALANCE_WAS_SET_TO_ZERO + " ";
			}
			accountRepository.save(account);
			response.setMessage(JsonHelper.getJsonBodyWithMessage(submessage + Constants.ACCOUNT_CREATED, account.toString()));
		}
		return response.getAndClearResponse();
	}

	@GetMapping("/accounts/{account_id}")
	public ResponseEntity<String> fetchAccountDetails(@PathVariable("account_id") Long accountId) {
		if (accountId <= 0) {
			response.setStatus(HttpResponse.BAD_REQUEST);
			response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.ACCOUNT_ID_MUST_BE_GREATHER_THAN_ZERO, Constants.ACCOUNT_NONE));
		} else {
			response.setStatus(HttpResponse.OK);
			Optional<Account> account = accountRepository.findById(accountId);
			if (!account.isPresent()) {
				response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.ACCOUNT_WITH_ID + accountId + Constants.DOES_NOT_EXIST, Constants.ACCOUNT_NONE));
			} else {
				response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.NONE, account.get().toString()));
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
			response.setMessage(JsonHelper.getJsonBodyWithMessage(e.getMessage(), Constants.ACCOUNT_NONE));
		}
		if (accounts.isEmpty()) {
			response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.THERE_ARE_NO_ACCOUNTS, Constants.ACCOUNT_NONE));
		} else {
			response.setMessage(JsonHelper.getJsonBodyWithMessage(Constants.NONE, accounts.toString()));
		}
		return response.getAndClearResponse();
	}

}
