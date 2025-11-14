package com.deliverytech.deliveryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.deliverytech.deliveryapi.entity.Pedido;
import com.deliverytech.deliveryapi.repository.ClienteRepository;
import com.deliverytech.deliveryapi.repository.PedidoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Cadastrar novo pedido
     */
    public Pedido cadastrar(Pedido pedido) {
        validarDadosPedido(pedido);

        // Definir data e status padrão
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus("PENDENTE");
        pedido.setAtivo(true);

        return pedidoRepository.save(pedido);
    }

    /**
     * Buscar pedido por ID
     */
    @Transactional(readOnly = true)
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    /**
     * Listar todos os pedidos
     */
    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    /**
     * Atualizar pedido
     */
    public Pedido atualizar(Long id, Pedido pedidoAtualizado) {
        Pedido pedido = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));

        validarDadosPedido(pedidoAtualizado);

        pedido.setEnderecoEntrega(pedidoAtualizado.getEnderecoEntrega());
        pedido.setStatus(pedidoAtualizado.getStatus());
        pedido.setValorTotal(pedidoAtualizado.getValorTotal());

        return pedidoRepository.save(pedido);
    }

    /**
     * Cancelar pedido (soft delete)
     */
    public void cancelar(Long id) {
        Pedido pedido = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));

        pedido.cancelar();
        pedidoRepository.save(pedido);
    }

    /**
     * Buscar pedidos por status
     */
    @Transactional(readOnly = true)
    public List<Pedido> buscarPorStatus(String status) {
        return pedidoRepository.findByStatusIgnoreCase(status);
    }

    /**
     * Buscar pedidos por cliente
     */
    @Transactional(readOnly = true)
    public List<Pedido> buscarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    /**
     * Validações de negócio
     */
    private void validarDadosPedido(Pedido pedido) {
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new IllegalArgumentException("Cliente é obrigatório");
        }

        if (!clienteRepository.existsById(pedido.getCliente().getId())) {
            throw new IllegalArgumentException("Cliente não existe: " + pedido.getCliente().getId());
        }

        if (pedido.getEnderecoEntrega() == null || pedido.getEnderecoEntrega().trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço de entrega é obrigatório");
        }

        if (pedido.getValorTotal() == null || pedido.getValorTotal() <= 0) {
            throw new IllegalArgumentException("Valor total deve ser maior que zero");
        }
    }
}