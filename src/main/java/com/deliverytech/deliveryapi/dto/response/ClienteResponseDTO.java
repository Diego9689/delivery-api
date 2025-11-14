package com.deliverytech.deliveryapi.dto.response;

import com.deliverytech.deliveryapi.entity.Cliente;

import lombok.Data;

@Data
public class ClienteResponseDTO {

    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String endereco;

    private Boolean ativo;
}