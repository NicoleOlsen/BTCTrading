package helper;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import trading.Application;
import trading.account.Account;
import trading.limitOrder.LimitOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public abstract class AbstractTest {
   protected MockMvc mvc;
   @Autowired
   WebApplicationContext webApplicationContext;
   
   protected void setUp() {
      mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
   }
   protected String mapToJson(Object obj) throws JsonProcessingException {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(obj);
   }
   protected <T> T mapFromJson(String json, Class<T> clazz)
      throws JsonParseException, JsonMappingException, IOException {
      
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(json, clazz);
   }
   
   protected MvcResult getMvcGetResult(String uri, String id) throws JsonProcessingException, Exception {
		return mvc.perform(MockMvcRequestBuilders.get(uri + "/" + id)
		         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	}
	
	protected <T> MvcResult getMvcPostResult(String uri, T obj) throws JsonProcessingException, Exception {
		String inputJson = mapToJson(obj);
		return mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	}
	
	protected String checkStatusAndReturnContentForGetResult(String orderId, int expectedStatus, String uri)
			throws JsonProcessingException, Exception, UnsupportedEncodingException {
		MvcResult mvcResult = getMvcGetResult(uri, orderId);
		int status = mvcResult.getResponse().getStatus();
		assertEquals(expectedStatus, status);
		return mvcResult.getResponse().getContentAsString();
	}

	protected <T> String checkStatusAndReturnContentForPostResult(T obj, int expectedStatus, String uri)
			throws JsonProcessingException, Exception, UnsupportedEncodingException {
		MvcResult mvcResult = getMvcPostResult(uri, obj);
		int status = mvcResult.getResponse().getStatus();
		assertEquals(expectedStatus, status);
		return mvcResult.getResponse().getContentAsString();
	}
}