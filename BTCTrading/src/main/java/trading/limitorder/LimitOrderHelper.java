package trading.limitorder;

import java.util.Optional;

import customexceptions.AccountNotFoundException;
import customexceptions.InsufficientFundsException;
import trading.account.Account;
import trading.account.AccountRepository;

public class LimitOrderHelper {

	public static Account getAccountFromOrder(LimitOrder lo, AccountRepository accountRepository)
			throws AccountNotFoundException {
		Long accId = lo.getAccountId();
		Optional<Account> accOpt = accountRepository.findById(accId);
		if (!accOpt.isPresent()) {
			throw new AccountNotFoundException("Account with id " + accId + " does not exists.");
		}
		Account acc = accOpt.get();
		return acc;
	}

	public static Account updateAccount(double currentPrice, Account acc) throws InsufficientFundsException {
		double usdBalance = acc.getBalanceUsd();
		if (usdBalance <= 0) {
			throw new InsufficientFundsException("Exchange is not possible due to insufficient funds.");
		}
		int newBtcBalance = (int) (usdBalance / currentPrice);
		double newUsdBalance = usdBalance - (newBtcBalance * currentPrice);
		acc.setBalanceBtc(newBtcBalance);
		acc.setBalanceUsd(newUsdBalance);
		return acc;
	}

}
