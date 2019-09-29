package trading.limitorder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LimitOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;
	@Column(nullable = false)
	private Double priceLimit;
	private boolean processed;
	@Column(nullable = false)
	private Long accountId;

	public LimitOrder() {
	}

	public static class Builder {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long orderId;
		@Column(nullable = false)
		private Double priceLimit;
		private boolean processed;
		@Column(nullable = false)
		private Long accountId;

		public Builder() {
		}

		public Builder withOrderId(Long orderId) {
			this.orderId = orderId;
			return this;
		}

		public Builder withPriceLimit(Double priceLimit) {
			this.priceLimit = priceLimit;
			return this;
		}

		public Builder isProcessed(boolean processed) {
			this.processed = processed;
			return this;
		}

		public Builder withAccountId(Long accountId) {
			this.accountId = accountId;
			return this;
		}

		public LimitOrder build() {
			LimitOrder order = new LimitOrder();
			order.orderId = this.orderId;
			order.priceLimit = priceLimit;
			order.processed = processed;
			order.accountId = accountId;
			return order;
		}
	}

	public Long getOrderId() {
		return orderId;
	}

	public double getPriceLimit() {
		return priceLimit;
	}

	public void setPrice_limit(double priceLimit) {
		this.priceLimit = priceLimit;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "\"limitOrder\": { \"order_id\": \"" + orderId + "\", \"price_limit\": \"" + priceLimit
				+ "\", \"processed\": \"" + processed + "\", \"account_id\": \"" + accountId + "\" }";
	}

}
