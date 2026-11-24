
package cl.pedidos360.pedidos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import cl.pedidos360.pedidos.config.RabbitMQConfig;
import cl.pedidos360.pedidos.model.Pedido;

@Service
public class PedidoConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(PedidoConsumer.class);

    private final PedidoService pedidoService;

    public PedidoConsumer(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RabbitListener(queues = RabbitMQConfig.PEDIDOS_QUEUE)
    public void recibirPedido(String mensaje) {

        try {
            Long pedidoId = Long.parseLong(mensaje);

            Pedido pedido = pedidoService.buscarPedidoConDetalles(pedidoId)
                    .orElseThrow(() -> new RuntimeException(
                            "No se encontró el pedido con ID: " + pedidoId
                    ));

            logger.info("Procesando pedido: {}", pedido.getNumeroPedido());
            logger.info("Cliente: {}", pedido.getClienteId());
            logger.info("Total: {}", pedido.getTotal());
            logger.info("Estado: {}", pedido.getEstado());

            int cantidadDetalles = pedido.getDetalles().size();

            logger.info(
                    "Pedido {} procesado correctamente. Cantidad de productos: {}",
                    pedido.getNumeroPedido(),
                    cantidadDetalles
            );

        } catch (Exception e) {
            logger.error("Error al procesar el pedido: {}", mensaje, e);

            throw new RuntimeException(
                    "Error procesando pedido desde RabbitMQ",
                    e
            );
        }
    }
}
