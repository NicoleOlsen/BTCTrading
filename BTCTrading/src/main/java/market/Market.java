package market;

import trading.Application;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import helper.GetPropertyValues;

public class Market {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static Quote getCurrentMarketData() {
		Quote quote = new Quote();
		String endpoint = getEndpoint();
		if (endpoint.isEmpty()) {
			log.error("Unable to connect to the endpoint.");
		} else {
			RestTemplate restTemplate = new RestTemplate();
			quote = restTemplate.getForObject(endpoint, Quote.class);
		}
		return quote;
	}

	private static String getEndpoint() {
		String endpoint = null;
		GetPropertyValues properties = new GetPropertyValues();
		try {
			endpoint = properties.getValue("btcendpoint");
		} catch (IOException e) {
			log.error("Unable to read properties file: " + e);
		}
		return endpoint;
	}
}
