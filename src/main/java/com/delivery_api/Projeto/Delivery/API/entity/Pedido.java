package com.delivery_api.Projeto.Delivery.API.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Status do pedido (ex: PENDENTE, EM_PREPARO, ENTREGUE, CANCELADO)
    private String status;

    // Endereço de entrega (pode ser diferente do endereço do cliente)
    private String enderecoEntrega;

    // Data e hora do pedido
    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;

    // Valor total do pedido
    @Column(name = "valor_total")
    private Double valorTotal;

    // Campo para controle de cancelamento (soft delete)
    @Column(nullable = true)
    private Boolean ativo;

    public void cancelar() {
        this.ativo = false;
        this.status = "CANCELADO";
    }
}