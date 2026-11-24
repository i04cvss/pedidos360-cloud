
package cl.pedidos360.pedidos.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import cl.pedidos360.pedidos.config.RabbitMQConfig;

@Service
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PedidoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPedido(Long pedidoId) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PEDIDOS_EXCHANGE,
                RabbitMQConfig.PEDIDOS_ROUTING_KEY,
                pedidoId.toString()
        );

        System.out.println("ID del pedido enviado a RabbitMQ: " + pedidoId);
    }
}
