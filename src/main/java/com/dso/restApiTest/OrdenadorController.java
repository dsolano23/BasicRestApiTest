package com.dso.restApiTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class OrdenadorController {
    static List<Ordenador> lista = new ArrayList<>();
    static {
 
        lista.add(new Ordenador("Yoga", "Lenovo", 800));
        lista.add(new Ordenador("Air", "Apple", 1000));
    }
 
    @GetMapping("/ordenadores")
    public List<Ordenador> buscarTodos() {
        return lista;
    }
    
	@GetMapping("/ordenadores/{modelo}")
    public ResponseEntity<Ordenador> buscarUno(@PathVariable String modelo) {
        Optional<Ordenador> ordenador = lista.stream().filter(o -> o.getModelo().equals(modelo)).findFirst();
        if (ordenador.isPresent()) {
             
            return new ResponseEntity<Ordenador>(ordenador.get(),HttpStatus.OK);
        }else {
         
            return new ResponseEntity<Ordenador>(HttpStatus.NOT_FOUND);
        }
         
         
    }
}
