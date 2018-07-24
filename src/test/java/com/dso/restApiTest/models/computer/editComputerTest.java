package com.dso.restApiTest.models.computer;



import static org.junit.Assert.assertEquals;

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

public class editComputerTest {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(editComputerTest.class);
	TraceHelper traceHelper = new TraceHelper();
	
    @LocalServerPort
    private int port;
   
    
    @Test
    public void editComputerTestOK() {
    	
    	ComputerDTO editComputer = new ComputerDTO ();  	
    	editComputer.setBrand("apple");
    	editComputer.setModel("macbook-pro");
    	editComputer.setPrice(1505d);
    	Response resp=null;
    	log.debug(" Load local values for creating a new computer: " + traceHelper.computerDTOTrace(editComputer));
    	try {
    	     resp = RestAssured.given()
    		.baseUri(apiPath.BASE_RUL + port)
    		.contentType(ContentType.JSON)
    		.accept(ContentType.JSON)
    		.body(editComputer)
			.post(apiPath.COMPUTER_RESOURCE);
   	 
    	    editComputer =  resp.getBody().as(ResponseDTO.class).getEntity();
   
    	    if (resp != null && resp.getStatusCode() == HttpStatus.OK.value()) {
    			String deviceId=  resp.getBody().as(ResponseDTO.class).getEntity().getIdComputer();
    			String editPath=apiPath.COMPUTER_RESOURCE+"/"+deviceId;
    	    	editComputer.setBrand("editApple");
    	    	editComputer.setModel("editMacbook-pro");
    	    	editComputer.setPrice(1500d);
    	    	
    	    	resp = RestAssured.given()
    	        		.baseUri(apiPath.BASE_RUL + port)
    	        		.contentType(ContentType.JSON)
    	        		.accept(ContentType.JSON)
    	        		.body(editComputer)
    	        		.put(editPath);

    	    	resp.then().assertThat()
    			.statusCode(HttpURLConnection.HTTP_OK).and()
    			.body("entity", CoreMatchers.notNullValue())
    			.body("error", CoreMatchers.nullValue())
    			.body("entity.model", CoreMatchers.is(editComputer.getModel()))
    			.body("entity.brand", CoreMatchers.is(editComputer.getBrand()))
    			.body("entity.price", CoreMatchers.notNullValue());
    	    	
    	    	Double currentValue = resp.getBody().as(ResponseDTO.class).getEntity().getPrice(); 
    	    	Double expectedValue = editComputer.getPrice();    	
    	    	
    	    	assertEquals(expectedValue,currentValue);  	   
    	    	
    	    	log.info(" The computer with the values was edited: " + traceHelper.respondHttpTrace(resp) + traceHelper.computerDTOTrace(resp.getBody().as(ResponseDTO.class).getEntity())); 
    	    	
    	    }
    	    
    	
    	}finally {
    		if (resp != null && resp.getStatusCode() == HttpStatus.OK.value()) {
    			String deviceId=  resp.getBody().as(ResponseDTO.class).getEntity().getIdComputer();
    			String deletePath=apiPath.COMPUTER_RESOURCE+"/"+deviceId;
    			resp = RestAssured.given()
	    		.baseUri(apiPath.BASE_RUL + port)
	    		.contentType(ContentType.JSON)
	    		.accept(ContentType.JSON)
	    		.delete(deletePath);		
    			log.debug(" Deleted the computer: " + traceHelper.computerDTOTrace(editComputer) + traceHelper.respondHttpTrace(resp));	    

    		}
    	}	
    }
    
    
    @Test
    public void editComputerTestNotBrand() {
    	
    	ComputerDTO editComputer = new ComputerDTO ();  	
    	editComputer.setBrand("apple");
    	editComputer.setModel("macbook-pro");
    	editComputer.setPrice(1505d);
    	Response respAddComputer=null;
    	log.debug(" Load local values for creating a new computer: " + traceHelper.computerDTOTrace(editComputer));
    	try {
    		respAddComputer = RestAssured.given()
    		.baseUri(apiPath.BASE_RUL + port)
    		.contentType(ContentType.JSON)
    		.accept(ContentType.JSON)
    		.body(editComputer)
			.post(apiPath.COMPUTER_RESOURCE);
   	 
    	    editComputer =  respAddComputer.getBody().as(ResponseDTO.class).getEntity();
   
    	    if (respAddComputer != null && respAddComputer.getStatusCode() == HttpStatus.OK.value()) {
    			String deviceId=  respAddComputer.getBody().as(ResponseDTO.class).getEntity().getIdComputer();
    			String editPath=apiPath.COMPUTER_RESOURCE+"/"+deviceId;
    	    	editComputer.setBrand("");
    	    	editComputer.setModel("editMacbook-pro");
    	    	editComputer.setPrice(1500d);

    	    	Response respEditComputer = RestAssured.given()
    	        		.baseUri(apiPath.BASE_RUL + port)
    	        		.contentType(ContentType.JSON)
    	        		.accept(ContentType.JSON)
    	        		.body(editComputer)
    	        		.put(editPath);
    	    	
    	    	respEditComputer.then().assertThat()
    				.statusCode(HttpURLConnection.HTTP_BAD_REQUEST).and()
    				.body("entity", CoreMatchers.nullValue())
    				.body("errorDescription", CoreMatchers.notNullValue())
    				.body("errorCode", CoreMatchers.is(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_BRAND.getCode()))
    				.body("errorDescription", CoreMatchers.is(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_BRAND.getDesc()));	  
    	    	
    	    	log.info(" The computer with the values was edited: " + traceHelper.respondHttpTrace(respEditComputer) + traceHelper.computerDTOTrace(editComputer)); 
    	    	
    	    }
    	    
    	
    	}finally {
    		if (respAddComputer != null && respAddComputer.getStatusCode() == HttpStatus.OK.value()) {
    			String deviceId=  respAddComputer.getBody().as(ResponseDTO.class).getEntity().getIdComputer();
    			String deletePath=apiPath.COMPUTER_RESOURCE+"/"+deviceId;
    			respAddComputer = RestAssured.given()
	    		.baseUri(apiPath.BASE_RUL + port)
	    		.contentType(ContentType.JSON)
	    		.accept(ContentType.JSON)
	    		.delete(deletePath);		
    			log.debug(" Deleted the computer: " + traceHelper.computerDTOTrace(editComputer) + traceHelper.respondHttpTrace(respAddComputer));	    

    		}
    	}	 
    }
    
    @Test
    public void addComputerTestNotModel() {
    	
    	ComputerDTO editComputer = new ComputerDTO ();  	
    	editComputer.setBrand("apple");
    	editComputer.setModel("macbook-pro");
    	editComputer.setPrice(1505d);
    	Response respAddComputer=null;
    	log.debug(" Load local values for creating a new computer: " + traceHelper.computerDTOTrace(editComputer));
    	try {
    		respAddComputer = RestAssured.given()
    		.baseUri(apiPath.BASE_RUL + port)
    		.contentType(ContentType.JSON)
    		.accept(ContentType.JSON)
    		.body(editComputer)
			.post(apiPath.COMPUTER_RESOURCE);
   	 
    	    editComputer =  respAddComputer.getBody().as(ResponseDTO.class).getEntity();
   
    	    if (respAddComputer != null && respAddComputer.getStatusCode() == HttpStatus.OK.value()) {
    			String deviceId=  respAddComputer.getBody().as(ResponseDTO.class).getEntity().getIdComputer();
    			String editPath=apiPath.COMPUTER_RESOURCE+"/"+deviceId;
    	    	editComputer.setBrand("editApple");
    	    	editComputer.setModel("");
    	    	editComputer.setPrice(1500d);

    	    	Response respEditComputer = RestAssured.given()
    	        		.baseUri(apiPath.BASE_RUL + port)
    	        		.contentType(ContentType.JSON)
    	        		.accept(ContentType.JSON)
    	        		.body(editComputer)
    	        		.put(editPath);
    	    	
    	    	respEditComputer.then().assertThat()
    				.statusCode(HttpURLConnection.HTTP_BAD_REQUEST).and()
    				.body("entity", CoreMatchers.nullValue())
    				.body("errorDescription", CoreMatchers.notNullValue())
    				.body("errorCode", CoreMatchers.is(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_MODEL.getCode()))
    				.body("errorDescription", CoreMatchers.is(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_MODEL.getDesc()));	  
    	    	
    	    	log.info(" The computer with the values was edited: " + traceHelper.respondHttpTrace(respEditComputer) + traceHelper.computerDTOTrace(editComputer)); 
    	    	
    	    }
    	      	
    	}finally {
    		if (respAddComputer != null && respAddComputer.getStatusCode() == HttpStatus.OK.value()) {
    			String deviceId=  respAddComputer.getBody().as(ResponseDTO.class).getEntity().getIdComputer();
    			String deletePath=apiPath.COMPUTER_RESOURCE+"/"+deviceId;
    			respAddComputer = RestAssured.given()
	    		.baseUri(apiPath.BASE_RUL + port)
	    		.contentType(ContentType.JSON)
	    		.accept(ContentType.JSON)
	    		.delete(deletePath);		
    			log.debug(" Deleted the computer: " + traceHelper.computerDTOTrace(editComputer) + traceHelper.respondHttpTrace(respAddComputer));	    

    		}
    	}	 
    	
	  } 
    
    @Test
    public void addComputerTestNotPrice() {
    	ComputerDTO editComputer = new ComputerDTO ();  	
    	editComputer.setBrand("apple");
    	editComputer.setModel("macbook-pro");
    	editComputer.setPrice(1505d);
    	Response respAddComputer=null;
    	log.debug(" Load local values for creating a new computer: " + traceHelper.computerDTOTrace(editComputer));
    	try {
    		respAddComputer = RestAssured.given()
    		.baseUri(apiPath.BASE_RUL + port)
    		.contentType(ContentType.JSON)
    		.accept(ContentType.JSON)
    		.body(editComputer)
			.post(apiPath.COMPUTER_RESOURCE);
   	 
    	    editComputer =  respAddComputer.getBody().as(ResponseDTO.class).getEntity();
   
    	    if (respAddComputer != null && respAddComputer.getStatusCode() == HttpStatus.OK.value()) {
    			String deviceId=  respAddComputer.getBody().as(ResponseDTO.class).getEntity().getIdComputer();
    			String editPath=apiPath.COMPUTER_RESOURCE+"/"+deviceId;
    	    	editComputer.setBrand("editApple");
    	    	editComputer.setModel("editMacbook-pro");
    	    	editComputer.setPrice(null);

    	    	Response respEditComputer = RestAssured.given()
    	        		.baseUri(apiPath.BASE_RUL + port)
    	        		.contentType(ContentType.JSON)
    	        		.accept(ContentType.JSON)
    	        		.body(editComputer)
    	        		.put(editPath);
    	    	
    	    	respEditComputer.then().assertThat()
    				.statusCode(HttpURLConnection.HTTP_BAD_REQUEST).and()
    				.body("entity", CoreMatchers.nullValue())
    				.body("errorDescription", CoreMatchers.notNullValue())
    				.body("errorCode", CoreMatchers.is(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_PRICE.getCode()))
    				.body("errorDescription", CoreMatchers.is(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_PRICE.getDesc()));	  
    	    	
    	    	log.info(" The computer with the values was edited: " + traceHelper.respondHttpTrace(respEditComputer) + traceHelper.computerDTOTrace(editComputer)); 
    	    	
    	    }
    	      	
    	}finally {
    		if (respAddComputer != null && respAddComputer.getStatusCode() == HttpStatus.OK.value()) {
    			String deviceId=  respAddComputer.getBody().as(ResponseDTO.class).getEntity().getIdComputer();
    			String deletePath=apiPath.COMPUTER_RESOURCE+"/"+deviceId;
    			respAddComputer = RestAssured.given()
	    		.baseUri(apiPath.BASE_RUL + port)
	    		.contentType(ContentType.JSON)
	    		.accept(ContentType.JSON)
	    		.delete(deletePath);		
    			log.debug(" Deleted the computer: " + traceHelper.computerDTOTrace(editComputer) + traceHelper.respondHttpTrace(respAddComputer));	    

    		}
    	}	 
    	
	  } 
}
