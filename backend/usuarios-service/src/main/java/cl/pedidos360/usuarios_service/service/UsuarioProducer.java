
package cl.pedidos360.usuarios_service.service;

import cl.pedidos360.usuarios_service.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UsuarioProducer {

    private final RabbitTemplate rabbitTemplate;

    public UsuarioProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarUsuarioCreado(Object usuario) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.USUARIOS_EXCHANGE,
                RabbitMQConfig.USUARIOS_ROUTING_KEY,
                usuario
        );

        System.out.println("Evento de usuario enviado a RabbitMQ");
    }
}
