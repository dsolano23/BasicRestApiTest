package com.dso.restApiTest.constants;

public enum InternalErrorCode {

   
    //Using the values of -100 to -199 for validating COMPUTER fields error
	INVALID_FIELD_VALUE_COMPUTER_ID_COMPUTER( -101, "Invalid Computer IdCumputer Value"),
	INVALID_FIELD_VALUE_COMPUTER_BRAND(-102, "Invalid Computer Brand Value"),
	INVALID_FIELD_VALUE_COMPUTER_MODEL(-103, "Invalid Computer Model Value"),
	INVALID_FIELD_VALUE_COMPUTER_PRICE(-104, "Invalid Computer Price Value"),
	
    //Using the values of -1000 to -1999 for validating COMPUTER operation error
	INVALID_ACTION_ID_COMPUTER_NOT_EXISTS(-1001, "IdCumputer no exist");
	
	private int code;
	
	private String desc;
	
	InternalErrorCode(Integer _code, String _desc){
		this.code= _code;
		this.desc= _desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	
	
}
