package trading.limitorder;

import java.util.Optional;

import constants.Constants;
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
			throw new AccountNotFoundException(Constants.ACCOUNT_WITH_ID + accId + Constants.DOES_NOT_EXIST);
		}
		Account acc = accOpt.get();
		return acc;
	}

	public static Account updateAccount(double currentPrice, Account acc) throws InsufficientFundsException {
		double usdBalance = acc.getBalanceUsd();
		if (usdBalance <= 0) {
			throw new InsufficientFundsException(Constants.EXCHANGE_NOT_POSSIBLE);
		}
		int newBtcBalance = (int) (usdBalance / currentPrice);
		double newUsdBalance = usdBalance - (newBtcBalance * currentPrice);
		acc.setBalanceBtc(newBtcBalance);
		acc.setBalanceUsd(newUsdBalance);
		return acc;
	}

}
