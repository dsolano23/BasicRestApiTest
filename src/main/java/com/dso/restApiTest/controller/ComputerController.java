package com.dso.restApiTest.controller;


import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dso.restApiTest.constants.InternalErrorCode;
import com.dso.restApiTest.constants.apiPath;
import com.dso.restApiTest.model.ComputerDTO;
import com.dso.restApiTest.model.ErrorDTO;
import com.dso.restApiTest.model.ResponseDTO;


@RestController

public class ComputerController {
	
	
    private static HashMap<String, ComputerDTO> computerListDTO = new HashMap<String, ComputerDTO>();
    
    
    @PostMapping(path=apiPath.COMPUTER_RESOURCE, consumes= {MediaType.APPLICATION_JSON_VALUE}, produces= {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> addComputer(@RequestBody ComputerDTO newComputer) { 

    	String idComputer = UUID.randomUUID().toString();   	
    	newComputer.setIdComputer(idComputer);
    	ResponseDTO resp= new ResponseDTO();
    	if (newComputer.getBrand() == null || newComputer.getBrand().equalsIgnoreCase("") ) {
    		resp.setErrorDescription(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_BRAND.getDesc());
    		resp.setErrorCode(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_BRAND.getCode());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    	}else if (newComputer.getModel() == null || newComputer.getModel().equalsIgnoreCase("") ) {
    		resp.setErrorDescription(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_MODEL.getDesc());
    		resp.setErrorCode(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_MODEL.getCode());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    	}else if (newComputer.getPrice() == null || newComputer.getPrice() <= 0d ) {
    		resp.setErrorDescription(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_PRICE.getDesc());
    		resp.setErrorCode(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_PRICE.getCode());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    	}
    	
    	computerListDTO.put(idComputer, newComputer); 
    	resp.setEntity(newComputer);
    	return new ResponseEntity<ResponseDTO>(resp,HttpStatus.OK);
    }
    
    @PutMapping(path=apiPath.COMPUTER_RESOURCE + "/{uuid}", consumes= {MediaType.APPLICATION_JSON_VALUE}, produces= {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> updateComputer(@PathVariable("uuid") String uuid, @RequestBody ComputerDTO modifiedComputer) {
    	ResponseDTO resp= new ResponseDTO();
    	if ( uuid.equalsIgnoreCase(modifiedComputer.getIdComputer()) && computerListDTO.get(uuid) != null) {    		
    		if (modifiedComputer.getBrand() == null || modifiedComputer.getBrand().equalsIgnoreCase("") ) {
        		resp.setErrorDescription(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_BRAND.getDesc());
        		resp.setErrorCode(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_BRAND.getCode());
        		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        	}else if (modifiedComputer.getModel() == null || modifiedComputer.getModel().equalsIgnoreCase("") ) {
        		resp.setErrorDescription(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_MODEL.getDesc());
        		resp.setErrorCode(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_MODEL.getCode());
        		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        	}else if (modifiedComputer.getPrice() == null || modifiedComputer.getPrice() <= 0d ) {
        		resp.setErrorDescription(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_PRICE.getDesc());
        		resp.setErrorCode(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_PRICE.getCode());
        		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        	}     	
    	}	   	
    	computerListDTO.replace(uuid, modifiedComputer);  
    	modifiedComputer = computerListDTO.get(uuid);
    	resp.setEntity(modifiedComputer);
    	return new ResponseEntity<ResponseDTO>(resp,HttpStatus.OK);
    }
    
    @DeleteMapping(path=apiPath.COMPUTER_RESOURCE + "/{uuid}", consumes= {MediaType.APPLICATION_JSON_VALUE}, produces= {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ErrorDTO> deleteComputer(@PathVariable("uuid") String uuid) {
    	ErrorDTO resp= new ErrorDTO();
    	if ( uuid == null || uuid.equalsIgnoreCase("") ) {
    		resp.setErrorDescription(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_ID_COMPUTER.getDesc());
    		resp.setErrorCode(InternalErrorCode.INVALID_FIELD_VALUE_COMPUTER_ID_COMPUTER.getCode());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    	}else if (computerListDTO.get(uuid) == null) {
    		resp.setErrorDescription(InternalErrorCode.INVALID_ACTION_ID_COMPUTER_NOT_EXISTS.getDesc());
    		resp.setErrorCode(InternalErrorCode.INVALID_ACTION_ID_COMPUTER_NOT_EXISTS.getCode());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    	}else {
    		computerListDTO.remove(uuid);
    		return ResponseEntity.status(HttpStatus.OK).body(resp);
    	}
    }   
    
    @GetMapping(apiPath.COMPUTER_RESOURCE)
    public  HashMap<String, ComputerDTO> getComputerList() {
        return computerListDTO;
    }
     
    @GetMapping(apiPath.COMPUTER_RESOURCE + "/{uuid}")
    public ResponseEntity<ComputerDTO> getComputerByID(@PathVariable("uuid") String uuid) {
    	
        Optional<String> computer = computerListDTO.keySet().stream().filter(o -> o.equals(uuid)).findFirst();
        if (computer.isPresent()) {            
            return new ResponseEntity<ComputerDTO>(computerListDTO.get(uuid),HttpStatus.OK);
        }else {
         
            return new ResponseEntity<ComputerDTO>(HttpStatus.NOT_FOUND);
        }     
    }
    
}
