package com.dso.restApiTest.model;

import javax.validation.constraints.NotNull;

public class ComputerDTO {
 
	private String idComputer;
	@NotNull
	private String model;
	@NotNull
    private String brand;
	@NotNull
    private Double price;
     
    public ComputerDTO() {
        super();
    }
     
    public ComputerDTO(String model, String brand, Double price) {
        super();
        this.model = model;
        this.brand = brand;
        this.price = price;
    }
    
    public String getIdComputer() {
		return idComputer;
	}

	public void setIdComputer(String idComputer) {
		this.idComputer = idComputer;
	}
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
     
}
