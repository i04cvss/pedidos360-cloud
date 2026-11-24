
package cl.pedidos360.pedidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pedidos360.pedidos.model.Pedido;
import cl.pedidos360.pedidos.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoProducer pedidoProducer;

    public PedidoService(
            PedidoRepository pedidoRepository,
            PedidoProducer pedidoProducer) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoProducer = pedidoProducer;
    }

    public List<Pedido> listarPedido() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Pedido> buscarPedidoConDetalles(Long id) {
        return pedidoRepository.buscarPorIdConDetalles(id);
    }

    @Transactional
    public Pedido guardarPedido(Pedido pedido) {
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        pedidoProducer.enviarPedido(pedidoGuardado.getId());
        return pedidoGuardado;
    }

    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
