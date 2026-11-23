package cl.pedidos360.pedidos.service;

import cl.pedidos360.pedidos.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PedidoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPedido(Object pedido) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PEDIDOS_EXCHANGE,
                RabbitMQConfig.PEDIDOS_ROUTING_KEY,
                pedido
        );

        System.out.println("Mensaje de pedido enviado a RabbitMQ");
    }
}
