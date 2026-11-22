
package cl.pedidos360.bff.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prueba")
public class PruebaController {

    @GetMapping
    public String comprobarConexion() {
        return "Conexion con Pedidos360 BFF exitosa";
    }
}
