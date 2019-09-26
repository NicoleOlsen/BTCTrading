package trading.limitOrder;

import org.springframework.data.repository.CrudRepository;

public interface LimitOrderRepository extends CrudRepository<LimitOrder, Long> {
	LimitOrder findById(long id);
}