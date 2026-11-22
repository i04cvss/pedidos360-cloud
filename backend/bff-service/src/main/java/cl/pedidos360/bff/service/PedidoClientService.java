package cl.pedidos360.bff.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PedidoClientService {

    private final RestClient restClient;

    public PedidoClientService(
            @Value("${app.services.pedidos.url}") String pedidosUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(pedidosUrl)
                .build();
    }

    public List<Map<String, Object>> listarPedidos() {

        return restClient.get()
                .uri("/api/pedidos")
                .retrieve()
                .body(new ParameterizedTypeReference<
                        List<Map<String, Object>>>() {});
    }
}