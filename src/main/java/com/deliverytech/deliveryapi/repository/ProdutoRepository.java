package com.deliverytech.deliveryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.deliveryapi.entity.Produto;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Buscar produtos ativos
    List<Produto> findByAtivoTrue();

    // Buscar produtos por nome (contendo, ignorando maiúsculas/minúsculas)
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    // Verificar se já existe produto com determinado nome
    boolean existsByNomeIgnoreCase(String nome);
}