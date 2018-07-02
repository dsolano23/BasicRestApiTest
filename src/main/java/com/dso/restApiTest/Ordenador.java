package com.dso.restApiTest;

public class Ordenador {
 
    private String modelo;
    private String marca;
    private double precio;
     
     
    public Ordenador(String modelo, String marca, double precio) {
        super();
        this.modelo = modelo;
        this.marca = marca;
        this.precio = precio;
    }
    public Ordenador() {
        super();
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
     
}
