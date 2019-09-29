package trading.limitOrder;

import java.util.Optional;

import customExceptions.AccountNotFoundException;
import customExceptions.InsufficientFundsException;
import trading.account.Account;
import trading.account.AccountRepository;

public class LimitOrderHelper {

	public static Account getAccountFromOrder(LimitOrder lo, AccountRepository accountRepository)
			throws AccountNotFoundException {
		Long accId = lo.getAccount_id();
		Optional<Account> accOpt = accountRepository.findById(accId);
		if (!accOpt.isPresent()) {
			throw new AccountNotFoundException("Account with id " + accId + " does not exists.");
		}
		Account acc = accOpt.get();
		return acc;
	}

	public static Account updateAccount(double currentPrice, Account acc) throws InsufficientFundsException {
		double usdBalance = acc.getBalance_usd();
		if (usdBalance <= 0) {
			throw new InsufficientFundsException("Exchange is not possible due to insufficient funds.");
		}
		int newBtcBalance = (int) (usdBalance / currentPrice);
		double newUsdBalance = usdBalance - (newBtcBalance * currentPrice);
		acc.setBalance_btc(newBtcBalance);
		acc.setBalance_usd(newUsdBalance);
		return acc;
	}

}
