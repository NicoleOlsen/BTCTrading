package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import trading.Application;

public class GetPropertyValues {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	private String result;
	private InputStream inputStream;

	public String getValue(String property) throws IOException {
		try {
			Properties properties = new Properties();
			String propertyFileName = "resources/config.properties";
			inputStream = new FileInputStream(new File(propertyFileName));
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("Property file '" + propertyFileName + "' not found in the classpath.");
			}
			result = properties.getProperty(property);
		} catch (Exception e) {
			log.error("Unable to read properties file: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
}
