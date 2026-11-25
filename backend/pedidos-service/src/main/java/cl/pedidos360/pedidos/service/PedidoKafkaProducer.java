
package cl.pedidos360.pedidos.service;

import cl.pedidos360.pedidos.dto.PedidoEventDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class PedidoKafkaProducer {

    private final KafkaTemplate<String, PedidoEventDTO> kafkaTemplate;

    public PedidoKafkaProducer(KafkaTemplate<String, PedidoEventDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarPedidoCreado(Long pedidoId) {
        enviarEvento("pedidos.creados", pedidoId, "PEDIDO_CREADO");
    }

    public void enviarPedidoProcesado(Long pedidoId) {
        enviarEvento("pedidos.procesados", pedidoId, "PEDIDO_PROCESADO");
    }

    private void enviarEvento(String topic, Long pedidoId, String tipoEvento) {
        PedidoEventDTO evento = new PedidoEventDTO(
            pedidoId,
            tipoEvento,
            Instant.now().toString()
        );

        kafkaTemplate.send(topic, pedidoId.toString(), evento)
            .whenComplete((resultado, error) -> {
                if (error != null) {
                    System.err.println("Error al enviar evento a Kafka: " + error.getMessage());
                } else {
                    System.out.println("Evento enviado a Kafka. Tópico: " + topic
                        + ", Pedido ID: " + pedidoId);
                }
            });
    }
}
