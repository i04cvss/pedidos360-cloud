
package cl.pedidos360.usuarios_service.service;

import cl.pedidos360.usuarios_service.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UsuarioConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(UsuarioConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.USUARIOS_QUEUE)
    public void recibirUsuario(String mensaje) {

        try {
            logger.info("Evento de usuario recibido: {}", mensaje);

            // Aquí irá el procesamiento real del evento.

            logger.info("Evento de usuario procesado correctamente");

        } catch (Exception e) {

            logger.error(
                    "Error al procesar evento de usuario: {}",
                    mensaje,
                    e
            );

            // Permite que Spring gestione los reintentos.
            throw new RuntimeException(
                    "Error procesando evento de usuario",
                    e
            );
        }
    }
}
