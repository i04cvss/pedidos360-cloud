
package cl.pedidos360.rabbitmq_admin_service.service;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

@Service
public class RabbitAdminService {

    private final RabbitAdmin rabbitAdmin;

    public RabbitAdminService(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    // Validar nombres
    private void validarNombre(String nombre, String campo) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El campo " + campo + " no puede estar vacío"
            );
        }

        if (!nombre.matches("[a-zA-Z0-9._-]+")) {
            throw new IllegalArgumentException(
                    "El campo " + campo + " contiene caracteres inválidos"
            );
        }
    }

    // Crear cola
    public void crearCola(String nombre) {
        validarNombre(nombre, "nombre de cola");

        Queue queue = new Queue(nombre, true);
        rabbitAdmin.declareQueue(queue);
    }

    // Eliminar cola
    public boolean eliminarCola(String nombre) {
        validarNombre(nombre, "nombre de cola");
        return rabbitAdmin.deleteQueue(nombre);
    }

    // Crear exchange
    public void crearExchange(String nombre) {
        validarNombre(nombre, "nombre de exchange");

        DirectExchange exchange = new DirectExchange(nombre, true, false);
        rabbitAdmin.declareExchange(exchange);
    }

    // Eliminar exchange
    public void eliminarExchange(String nombre) {
        validarNombre(nombre, "nombre de exchange");
        rabbitAdmin.deleteExchange(nombre);
    }

    // Crear binding
    public void crearBinding(
            String nombreCola,
            String nombreExchange,
            String routingKey) {

        validarNombre(nombreCola, "nombre de cola");
        validarNombre(nombreExchange, "nombre de exchange");
        validarNombre(routingKey, "routing key");

        Binding binding = new Binding(
                nombreCola,
                Binding.DestinationType.QUEUE,
                nombreExchange,
                routingKey,
                null
        );

        rabbitAdmin.declareBinding(binding);
    }

    // Eliminar binding
    public void eliminarBinding(
            String nombreCola,
            String nombreExchange,
            String routingKey) {

        validarNombre(nombreCola, "nombre de cola");
        validarNombre(nombreExchange, "nombre de exchange");
        validarNombre(routingKey, "routing key");

        Binding binding = new Binding(
                nombreCola,
                Binding.DestinationType.QUEUE,
                nombreExchange,
                routingKey,
                null
        );

        rabbitAdmin.removeBinding(binding);
    }
}
