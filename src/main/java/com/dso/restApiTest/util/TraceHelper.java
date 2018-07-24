package com.dso.restApiTest.util;

import com.dso.restApiTest.model.ComputerDTO;
import com.dso.restApiTest.model.ResponseDTO;

import io.restassured.response.Response;


public class TraceHelper {

	public String respondHttpTrace(Response resp) {
		String respString= "";
		respString = respString + " \n\t\t\t\t\t\t\t  - statusCode: " + resp.getStatusCode();
		respString = respString + " \n\t\t\t\t\t\t\t  -  errorCode: " + resp.getBody().as(ResponseDTO.class).getErrorCode();
		respString = respString + " \n\t\t\t\t\t\t\t  -  errorText: " + resp.getBody().as(ResponseDTO.class).getErrorDescription();	
	    return respString;
	}
	
	public String computerDTOTrace(ComputerDTO computerDTO) {
		String computerDTOString= "";
		computerDTOString = computerDTOString + " \n\t\t\t\t\t\t\t  - IdComputer: " + computerDTO.getIdComputer();
		computerDTOString = computerDTOString + " \n\t\t\t\t\t\t\t  -      Brand: " + computerDTO.getBrand();
		computerDTOString = computerDTOString + " \n\t\t\t\t\t\t\t  -      Model: " + computerDTO.getModel();
		computerDTOString = computerDTOString + " \n\t\t\t\t\t\t\t  -      Price: " + computerDTO.getPrice();
    	return computerDTOString;
	}
}
