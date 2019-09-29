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
	private Long accountId;
	@Column(nullable = false)
	private String name;
	@Column(columnDefinition = "double default 0")
	private double balanceUsd;
	@Column(columnDefinition = "double default 0")
	private double balanceBtc = 0;

	public Account() {
	}

	public static class Builder {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long accountId;
		@Column(nullable = false)
		private String name;
		@Column(columnDefinition = "double default 0")
		private double balanceUsd;
		@Column(columnDefinition = "double default 0")
		private double balanceBtc = 0;

		public Builder() {
		}

		public Builder withAccountId(Long accountId) {
			this.accountId = accountId;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withUsdBalance(double balanceUsd) {
			this.balanceUsd = balanceUsd;
			return this;
		}

		public Builder withBtcBalance(double balanceBtc) {
			this.balanceBtc = balanceBtc;
			return this;
		}

		public Account build() {
			Account account = new Account();
			account.accountId = this.accountId;
			account.name = name;
			account.balanceUsd = balanceUsd;
			account.balanceBtc = balanceBtc;
			return account;
		}
	}

	public Long getAccountId() {
		return accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalanceUsd() {
		return balanceUsd;
	}

	public void setBalanceUsd(double balanceUsd) {
		this.balanceUsd = balanceUsd;
	}

	public double getBalanceBtc() {
		return balanceBtc;
	}

	public void setBalanceBtc(double balanceBtc) {
		this.balanceBtc = balanceBtc;
	}

	public boolean isEmpty() {
		if (accountId == null) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() { 
		return "\"account\": { \"account_id\": \"" + accountId + "\", \"name\": \"" + name + "\", \"balance_usd\": \""
				+ balanceUsd + "\", \"balance_btc\": \"" + balanceBtc + "\" }";
	}

}
