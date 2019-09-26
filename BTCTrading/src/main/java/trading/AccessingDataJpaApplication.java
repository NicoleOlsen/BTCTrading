package trading;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import trading.account.Account;
import trading.account.AccountRepository;
import trading.limitOrder.LimitOrder;
import trading.limitOrder.LimitOrderRepository;

/**
 * This class is used solely for loading initial data due to in memory DB.
 */

@SpringBootApplication
public class AccessingDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataJpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(LimitOrderRepository limitOrderRepository, AccountRepository accountRepository) {
		return (args) -> {
			initializeAccounts(accountRepository);
			initializeLimitOrders(limitOrderRepository);
		};
	}

	private void initializeAccounts(AccountRepository repository) {
		repository.save(new Account.Builder().withName("Kim").withUsdBalance(798.89).build());
	}

	private void initializeLimitOrders(LimitOrderRepository repository) {
		repository.save(new LimitOrder.Builder().withPriceLimit(3546.78).withAccountId(3L).build());
	}

}