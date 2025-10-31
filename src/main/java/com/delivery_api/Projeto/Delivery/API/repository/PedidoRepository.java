package com.delivery_api.Projeto.Delivery.API.repository;

import com.delivery_api.Projeto.Delivery.API.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar pedidos por status (ex: PENDENTE, ENTREGUE, CANCELADO)
    List<Pedido> findByStatusIgnoreCase(String status);

    // Buscar pedidos por ID do cliente
    List<Pedido> findByClienteId(Long clienteId);

    // Buscar pedidos ativos
    List<Pedido> findByAtivoTrue();
}