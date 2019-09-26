package trading.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long account_id;
	@Column(nullable = false)
	private String name;
	@Column(columnDefinition = "double default 0")
	private double balance_usd;
	@Column(columnDefinition = "double default 0")
	private double balance_btc = 0;

	public Account() {
	}

	public static class Builder {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long account_id;
		@Column(nullable = false)
		private String name;
		@Column(columnDefinition = "double default 0")
		private double balance_usd;
		@Column(columnDefinition = "double default 0")
		private double balance_btc = 0;

		public Builder() {
		}

		public Builder withAccountId(Long account_id) {
			this.account_id = account_id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withUsdBalance(double balance_usd) {
			this.balance_usd = balance_usd;
			return this;
		}

		public Builder withBtcBalance(double balance_btc) {
			this.balance_btc = balance_btc;
			return this;
		}

		public Account build() {
			Account account = new Account();
			account.account_id = this.account_id;
			account.name = name;
			account.balance_usd = balance_usd;
			account.balance_btc = balance_btc;
			return account;
		}
	}

	public Long getAccount_id() {
		return account_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance_usd() {
		return balance_usd;
	}

	public void setBalance_usd(double balance_usd) {
		this.balance_usd = balance_usd;
	}

	public double getBalance_btc() {
		return balance_btc;
	}

	public void setBalance_btc(double balance_btc) {
		this.balance_btc = balance_btc;
	}

	public boolean isEmpty() {
		if (account_id == null) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Account [account_id=" + account_id + ", name=" + name + ", balance_usd=" + balance_usd
				+ ", balance_btc=" + balance_btc + "]";
	}

}
