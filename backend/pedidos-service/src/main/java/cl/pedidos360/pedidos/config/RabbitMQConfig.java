
package cl.pedidos360.pedidos.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Nombres centralizados
    public static final String PEDIDOS_EXCHANGE = "pedidos.exchange";
    public static final String PEDIDOS_QUEUE = "pedidos.queue";
    public static final String PEDIDOS_ROUTING_KEY = "pedidos.creado";

    // Dead Letter Queue
    public static final String PEDIDOS_DLX = "pedidos.dlx";
    public static final String PEDIDOS_DLQ = "pedidos.dlq";
    public static final String PEDIDOS_DLQ_ROUTING_KEY = "pedidos.error";

    // Exchange principal
    @Bean
    public DirectExchange pedidosExchange() {
        return new DirectExchange(PEDIDOS_EXCHANGE);
    }

    // Dead Letter Exchange
    @Bean
    public DirectExchange pedidosDlx() {
        return new DirectExchange(PEDIDOS_DLX);
    }

    // Cola de mensajes fallidos
    @Bean
    public Queue pedidosDlq() {
        return QueueBuilder.durable(PEDIDOS_DLQ).build();
    }

    // Cola principal
    @Bean
    public Queue pedidosQueue() {
        return QueueBuilder.durable(PEDIDOS_QUEUE)
                .deadLetterExchange(PEDIDOS_DLX)
                .deadLetterRoutingKey(PEDIDOS_DLQ_ROUTING_KEY)
                .build();
    }

    // Binding de la cola principal
    @Bean
    public Binding pedidosBinding() {
        return BindingBuilder
                .bind(pedidosQueue())
                .to(pedidosExchange())
                .with(PEDIDOS_ROUTING_KEY);
    }

    // Binding de la DLQ
    @Bean
    public Binding pedidosDlqBinding() {
        return BindingBuilder
                .bind(pedidosDlq())
                .to(pedidosDlx())
                .with(PEDIDOS_DLQ_ROUTING_KEY);
    }
}
