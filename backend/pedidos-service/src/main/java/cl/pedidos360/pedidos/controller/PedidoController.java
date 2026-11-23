
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
import cl.pedidos360.pedidos.service.PedidoProducer;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoProducer pedidoProducer;

    public PedidoController(
            PedidoService pedidoService,
            PedidoProducer pedidoProducer) {
        this.pedidoService = pedidoService;
        this.pedidoProducer = pedidoProducer;
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
        Pedido pedidoGuardado = pedidoService.guardarPedido(pedido);

        pedidoProducer.enviarPedido(pedidoGuardado.toString());

        return pedidoGuardado;
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
