package cl.pedidos360.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.pedidos360.pedidos.model.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

}
