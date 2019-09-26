package market;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

	@JsonFormat(pattern = "yyyy-MM-ddTKK:mm:a")
	private String timestamp;
	private String price;

	public Quote() {
	}

	public String getTimeStamp() {
		return timestamp;
	}

	public void setTimeStamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public boolean isEmpty() {
		return price.isEmpty() || timestamp.isEmpty() ? true : false;
	}

	@Override
	public String toString() {
		return "Quote [timestamp=" + timestamp + ", price=" + price + "]";
	}

}
