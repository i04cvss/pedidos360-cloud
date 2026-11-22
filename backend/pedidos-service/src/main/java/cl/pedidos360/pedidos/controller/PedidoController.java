package cl.pedidos360.pedidos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.pedidos360.pedidos.model.Pedido;
import cl.pedidos360.pedidos.service.PedidoService;

@RestController 
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> listarPedido() {
        return pedidoService.listarPedido();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
        return pedidoService.buscarPedidoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pedido crearPedido(@RequestBody Pedido pedido) {
        return pedidoService.guardarPedido(pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        if (pedidoService.buscarPedidoPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
