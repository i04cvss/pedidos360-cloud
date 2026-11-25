
package cl.pedidos360.pedidos.service;

import cl.pedidos360.pedidos.dto.PedidoEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PedidoKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PedidoKafkaConsumer.class);

    private final PedidoKafkaProducer pedidoKafkaProducer;

    public PedidoKafkaConsumer(PedidoKafkaProducer pedidoKafkaProducer) {
        this.pedidoKafkaProducer = pedidoKafkaProducer;
    }

    @KafkaListener(topics = "pedidos.creados", groupId = "pedidos-service-group")
    public void recibirPedidoCreado(PedidoEventDTO evento) {
        logger.info("Evento recibido desde Kafka: pedidos.creados");
        logger.info("ID del pedido: {}", evento.getPedidoId());
        logger.info("Tipo de evento: {}", evento.getTipoEvento());

        if (evento.getPedidoId() == null || evento.getTipoEvento() == null) {
            logger.error("Evento inválido: faltan datos obligatorios");
            return;
        }

        logger.info("Pedido {} recibido correctamente", evento.getPedidoId());

        pedidoKafkaProducer.enviarPedidoProcesado(evento.getPedidoId());
    }

    @KafkaListener(topics = "pedidos.procesados", groupId = "pedidos-service-group")
    public void recibirPedidoProcesado(PedidoEventDTO evento) {
        logger.info("Evento recibido desde Kafka: pedidos.procesados");
        logger.info("ID del pedido procesado: {}", evento.getPedidoId());
        logger.info("Tipo de evento: {}", evento.getTipoEvento());
        logger.info("Pedido procesado correctamente");
    }
}
