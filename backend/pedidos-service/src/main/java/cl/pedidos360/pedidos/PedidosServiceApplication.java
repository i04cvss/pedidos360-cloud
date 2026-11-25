
package cl.pedidos360.pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class PedidosServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PedidosServiceApplication.class, args);
    }

}
