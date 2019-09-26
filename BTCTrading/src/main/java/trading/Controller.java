package trading;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import helper.Repository;
import trading.account.AccountRepository;
import trading.limitOrder.LimitOrderRepository;

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
	public <T> List<T> getAll(Repository repo) throws NoDataException{ 
		List<T> data = new ArrayList<>();
		Iterable<T> iterate = null;
		switch(repo){
			case ACCOUNT:
				iterate = (Iterable<T>) accountRepository.findAll();
				break; 
			case ORDER:
				iterate = (Iterable<T>) limitOrderRepository.findAll();
				break;
		}
		if(iterate == null) {
			throw new NoDataException("No data found for " + repo);
		}
		for (T item : iterate) {
			data.add(item);
		}
		return data;
	}
}
