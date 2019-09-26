package trading.limitOrder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LimitOrder{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long order_id;
	@Column(nullable = false)
	private Double price_limit;
	private boolean processed;
	@Column(nullable = false)
	private Long account_id;
	
	public LimitOrder() {}
	
	public static class Builder {
		
		@Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
		private Long order_id;
		@Column(nullable = false)
		private Double price_limit;
		private boolean processed;
		@Column(nullable = false)
		private Long account_id;
		
	    public Builder() {}
	    
	    public Builder withOrderId(Long order_id){
	    	this.order_id = order_id;
	        return this;  
	    }
	    
	    public Builder withPriceLimit(Double price_limit){
	    	this.price_limit = price_limit;
	        return this;  
	    }
	    
	    public Builder isProcessed(boolean processed){
	    	this.processed = processed;
	        return this;  
	    }
	    
	    public Builder withAccountId(Long account_id){
	    	this.account_id = account_id;
	        return this;  
	    }

	    public LimitOrder build(){
	    	LimitOrder order = new LimitOrder();
	    	order.order_id = this.order_id;
	    	order.price_limit = price_limit;
	    	order.processed = processed; 
	    	order.account_id = account_id;
	        return order;
	    }
	}
	
	public Long getOrder_id() {
		return order_id;
	}
	
	public double getPrice_limit() {
		return price_limit;
	}
	
	public void setPrice_limit(double price_limit) {
		this.price_limit = price_limit;
	}
	
	public boolean isProcessed() {
		return processed;
	}
	
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public Long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(Long account_id) {
		this.account_id = account_id;
	}

	@Override
	public String toString() {
		return "LimitOrder [order_id=" + order_id + ", price_limit=" + price_limit + ", processed=" + processed
				+ ", account_id=" + account_id + "]";
	}
	
}
