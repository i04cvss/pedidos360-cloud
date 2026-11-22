package cl.pedidos360.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.pedidos360.pedidos.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {

}
