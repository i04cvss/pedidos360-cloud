
package cl.pedidos360.rabbitmq_admin_service.controller;

import cl.pedidos360.rabbitmq_admin_service.service.RabbitAdminService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rabbitmq")
@Validated
public class RabbitAdminController {

    private final RabbitAdminService rabbitAdminService;

    public RabbitAdminController(RabbitAdminService rabbitAdminService) {
        this.rabbitAdminService = rabbitAdminService;
    }

    // Crear cola
    @PostMapping("/queues")
    public ResponseEntity<String> crearCola(
            @RequestParam @NotBlank String nombre) {

        rabbitAdminService.crearCola(nombre);
        return ResponseEntity.ok("Cola creada: " + nombre);
    }

    // Eliminar cola
    @DeleteMapping("/queues/{nombre}")
    public ResponseEntity<String> eliminarCola(
            @PathVariable @NotBlank String nombre) {

        boolean eliminada = rabbitAdminService.eliminarCola(nombre);

        if (!eliminada) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Cola eliminada: " + nombre);
    }

    // Crear exchange
    @PostMapping("/exchanges")
    public ResponseEntity<String> crearExchange(
            @RequestParam @NotBlank String nombre) {

        rabbitAdminService.crearExchange(nombre);
        return ResponseEntity.ok("Exchange creado: " + nombre);
    }

    // Eliminar exchange
    @DeleteMapping("/exchanges/{nombre}")
    public ResponseEntity<String> eliminarExchange(
            @PathVariable @NotBlank String nombre) {

        rabbitAdminService.eliminarExchange(nombre);
        return ResponseEntity.ok("Exchange eliminado: " + nombre);
    }

    // Crear binding
    @PostMapping("/bindings")
    public ResponseEntity<String> crearBinding(
            @RequestParam @NotBlank String cola,
            @RequestParam @NotBlank String exchange,
            @RequestParam @NotBlank String routingKey) {

        rabbitAdminService.crearBinding(cola, exchange, routingKey);
        return ResponseEntity.ok("Binding creado correctamente");
    }

    // Eliminar binding
    @DeleteMapping("/bindings")
    public ResponseEntity<String> eliminarBinding(
            @RequestParam @NotBlank String cola,
            @RequestParam @NotBlank String exchange,
            @RequestParam @NotBlank String routingKey) {

        rabbitAdminService.eliminarBinding(cola, exchange, routingKey);
        return ResponseEntity.ok("Binding eliminado correctamente");
    }
}
