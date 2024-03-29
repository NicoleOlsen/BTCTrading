package helper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.Constants;
import customexceptions.NoDataException;
import enums.Repository;
import trading.Application;
import trading.account.AccountRepository;
import trading.limitorder.LimitOrderRepository;

public abstract class Controller {

	static final Logger log = LoggerFactory.getLogger(Application.class);

	public LimitOrderRepository limitOrderRepository;
	public AccountRepository accountRepository;

	public Controller(AccountRepository repository) {
		this.accountRepository = repository;
	}

	public Controller(LimitOrderRepository limitOrderRepository, AccountRepository accountRepository) {
		this.limitOrderRepository = limitOrderRepository;
		this.accountRepository = accountRepository;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getAll(Repository repo) throws NoDataException {
		List<T> data = new ArrayList<>();
		Iterable<T> iterate = null;
		switch (repo) {
		case ACCOUNT:
			iterate = (Iterable<T>) accountRepository.findAll();
			break;
		case ORDER:
			iterate = (Iterable<T>) limitOrderRepository.findAll();
			break;
		}
		if (iterate == null) {
			throw new NoDataException(Constants.NO_DATA_FOUND_FOR + repo);
		}
		for (T item : iterate) {
			data.add(item);
		}
		return data;
	}
}
