
package cl.pedidos360.bff.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.pedidos360.bff.service.PedidoClientService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {

    private final PedidoClientService pedidoClientService;

    public PedidosController(PedidoClientService pedidoClientService) {
        this.pedidoClientService = pedidoClientService;
    }

    @GetMapping
    public List<Map<String, Object>> listarPedidos() {
        return pedidoClientService.listarPedidos();
    }
}
