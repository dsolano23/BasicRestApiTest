package com.dso.restApiTest.models.computer;



import java.net.HttpURLConnection;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.dso.restApiTest.Application;
import com.dso.restApiTest.constants.InternalErrorCode;
import com.dso.restApiTest.constants.apiPath;
import com.dso.restApiTest.controller.ComputerController;
import com.dso.restApiTest.model.ComputerDTO;
import com.dso.restApiTest.model.ResponseDTO;
import com.dso.restApiTest.util.TraceHelper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;



@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = {Application.class, ComputerController.class},
		
		webEnvironment = WebEnvironment.DEFINED_PORT
		)

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class addComputerTest {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(addComputerTest.class);
	TraceHelper traceHelper = new TraceHelper();
	
    @LocalServerPort
    private int port;
   
    
    @Test
    public void addComputerTestOK() {
    	
    	ComputerDTO newComputer = new ComputerDTO ();  	
    	newComputer.setBrand("apple");
    	newComputer.setModel("macbook-pro");
    	newComputer.setPrice(1505d);
    	Response resp=null;
    	log.debug(" Load local values for creating a new computer: " + traceHelper.computerDTOTrace(newComputer));
    	try {
    	     resp = RestAssured.given()
    		.baseUri(apiPath.BASE_RUL + port)
    		.contentType(ContentType.JSON)
    		.accept(ContentType.JSON)
    		.body(newComputer)
			.post(apiPath.COMPUTER_RESOURCE);
    	
    	 resp.then().assertThat()
			.statusCode(HttpURLConnection.HTTP_OK).and()
			.body("entity", CoreMatchers.notNullValue())
			.body("error", CoreMatchers.nullValue())
			.body("entity.model", CoreMatchers.notNullValue());
    	 
    	    newComputer =  resp.getBody().as(ResponseDTO.class).getEntity();
    	    log.info(" A new computer with the values was created: " + traceHelper.respondHttpTrace(resp) + traceHelper.computerDTOTrace(newComputer));	    
			//.extract().response().getBody().as(ResponseDTO.class).getError().equals(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_MODEL.getDesc());
    	}finally {
    		if (resp != null && resp.getStatusCode() == HttpStatus.OK.value()) {
    			String deviceId=  resp.getBody().as(ResponseDTO.class).getEntity().getIdComputer();
    			String deletePath=apiPath.COMPUTER_RESOURCE+"/"+deviceId;
    			resp = RestAssured.given()
	    		.baseUri(apiPath.BASE_RUL + port)
	    		.contentType(ContentType.JSON)
	    		.accept(ContentType.JSON)
	    		.delete(deletePath);		
    			log.debug(" Deleted the computer: " + traceHelper.computerDTOTrace(newComputer) + traceHelper.respondHttpTrace(resp));	    

    		}
    	}	
    }
    
    
    @Test
    public void addComputerTestNotBrand() {
    	
    	ComputerDTO newComputer = new ComputerDTO ();  
    	newComputer.setBrand("");
    	newComputer.setModel("macbook-pro");
    	newComputer.setPrice(1505d);
    	Response resp=null;
    	
    	log.debug(" Load local values for creating a new computer: " + traceHelper.computerDTOTrace(newComputer));
		resp = RestAssured.given()
				.baseUri(apiPath.BASE_RUL + port)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(newComputer)
				.post(apiPath.COMPUTER_RESOURCE);
    	
    	 resp.then().assertThat()
			.statusCode(HttpURLConnection.HTTP_BAD_REQUEST).and()
			.body("entity", CoreMatchers.nullValue())
			.body("errorDescription", CoreMatchers.notNullValue())
			.body("errorCode", CoreMatchers.is(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_BRAND.getCode()))
			.body("errorDescription", CoreMatchers.is(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_BRAND.getDesc())); 
    	 log.info(" A new computer with the values was NOT created: " + traceHelper.respondHttpTrace(resp) + traceHelper.computerDTOTrace(newComputer));	    
	  
    }
    
    @Test
    public void addComputerTestNotModel() {
    	ComputerDTO newComputer = new ComputerDTO ();  	
    	newComputer.setBrand("apple");
    	newComputer.setModel("");
    	newComputer.setPrice(1505d);
    	Response resp=null;
    	
    	log.debug(" Load local values for creating a new computer: " + traceHelper.computerDTOTrace(newComputer));
    	resp = RestAssured.given()
    		.baseUri(apiPath.BASE_RUL + port)
    		.contentType(ContentType.JSON)
    		.accept(ContentType.JSON)
    		.body(newComputer)
			.post(apiPath.COMPUTER_RESOURCE);
    	
    	 resp.then().assertThat()
			.statusCode(HttpURLConnection.HTTP_BAD_REQUEST).and()
			.body("entity", CoreMatchers.nullValue())
			.body("errorDescription", CoreMatchers.notNullValue())
			.body("errorCode", CoreMatchers.is(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_MODEL.getCode()))
			.body("errorDescription", CoreMatchers.is(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_MODEL.getDesc()));
    	 log.info(" A new computer with the values was NOT created: " + traceHelper.respondHttpTrace(resp) + traceHelper.computerDTOTrace(newComputer));	    

	  } 
    
    @Test
    public void addComputerTestNotPrice() {
    	ComputerDTO newComputer = new ComputerDTO ();  	
    	newComputer.setBrand("apple");
    	newComputer.setModel("macbook-pro");
    	newComputer.setPrice(null);
    	Response resp = RestAssured.given()
    		.baseUri(apiPath.BASE_RUL + port)
    		.contentType(ContentType.JSON)
    		.accept(ContentType.JSON)
    		.body(newComputer)
			.post(apiPath.COMPUTER_RESOURCE);
    	
    	 resp.then().assertThat()
			.statusCode(HttpURLConnection.HTTP_BAD_REQUEST).and()
			.body("entity", CoreMatchers.nullValue())
			.body("errorDescription", CoreMatchers.notNullValue())
			.body("errorCode", CoreMatchers.is(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_PRICE.getCode()))
			.body("errorDescription", CoreMatchers.is(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_PRICE.getDesc()));
	  } 
}
