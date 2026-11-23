
package cl.pedidos360.usuarios_service.config;

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
    public static final String USUARIOS_EXCHANGE = "usuarios.exchange";
    public static final String USUARIOS_QUEUE = "usuarios.queue";
    public static final String USUARIOS_ROUTING_KEY = "usuarios.creado";

    // Dead Letter Queue
    public static final String USUARIOS_DLX = "usuarios.dlx";
    public static final String USUARIOS_DLQ = "usuarios.dlq";
    public static final String USUARIOS_DLQ_ROUTING_KEY = "usuarios.error";

    // Exchange principal
    @Bean
    public DirectExchange usuariosExchange() {
        return new DirectExchange(USUARIOS_EXCHANGE);
    }

    // Dead Letter Exchange
    @Bean
    public DirectExchange usuariosDlx() {
        return new DirectExchange(USUARIOS_DLX);
    }

    // Cola principal
    @Bean
    public Queue usuariosQueue() {
        return QueueBuilder.durable(USUARIOS_QUEUE)
                .deadLetterExchange(USUARIOS_DLX)
                .deadLetterRoutingKey(USUARIOS_DLQ_ROUTING_KEY)
                .build();
    }

    // Cola de mensajes fallidos
    @Bean
    public Queue usuariosDlq() {
        return QueueBuilder.durable(USUARIOS_DLQ).build();
    }

    // Binding principal
    @Bean
    public Binding usuariosBinding() {
        return BindingBuilder
                .bind(usuariosQueue())
                .to(usuariosExchange())
                .with(USUARIOS_ROUTING_KEY);
    }

    // Binding de la DLQ
    @Bean
    public Binding usuariosDlqBinding() {
        return BindingBuilder
                .bind(usuariosDlq())
                .to(usuariosDlx())
                .with(USUARIOS_DLQ_ROUTING_KEY);
    }
}
