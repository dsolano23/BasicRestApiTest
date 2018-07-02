package com.dso.restApiTest;


import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
 
import org.junit.Test;
 
public class RESTTest {
 
    @Test
    public void testLista() {
 
        get("/ordenadores").then().body("modelo[0]", equalTo("Yoga"));
    }
     
    @Test
    public void testOrdenador() {
 
        get("/ordenadores/Yoga").then().body("modelo", equalTo("Yoga"));
    }
 
}
