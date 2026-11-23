
package cl.pedidos360.pedidos.service;

import cl.pedidos360.pedidos.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PedidoConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(PedidoConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.PEDIDOS_QUEUE)
    public void recibirPedido(String mensaje) {

        try {
            logger.info("Pedido recibido desde RabbitMQ: {}", mensaje);

            // Aquí irá el procesamiento real del pedido.

            logger.info("Pedido procesado correctamente");

        } catch (Exception e) {

            logger.error(
                    "Error al procesar el pedido: {}",
                    mensaje,
                    e
            );

            // La excepción permite que Spring gestione
            // los reintentos configurados.
            throw new RuntimeException(
                    "Error procesando el pedido",
                    e
            );
        }
    }
}
