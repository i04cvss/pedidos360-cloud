
package cl.pedidos360.pedidos.admin;

import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaErrorConfig {

    @Bean
    public DefaultErrorHandler kafkaErrorHandler() {
        FixedBackOff backOff = new FixedBackOff(2000L, 3L);

        DefaultErrorHandler errorHandler =
                new DefaultErrorHandler(
                        (record, exception) -> {
                            System.err.println(
                                    "Evento enviado a recuperación. Topic: "
                                    + record.topic()
                                    + ", Offset: " + record.offset()
                            );
                            exception.printStackTrace();
                        },
                        backOff
                );

        errorHandler.setRetryListeners((record, ex, deliveryAttempt) ->
                System.out.println(
                        "Reintento Kafka " + deliveryAttempt
                        + " para tópico " + record.topic()
                )
        );

        return errorHandler;
    }
}
