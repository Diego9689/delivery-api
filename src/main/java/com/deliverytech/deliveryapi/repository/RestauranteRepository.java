package com.deliverytech.deliveryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.deliverytech.deliveryapi.entity.Restaurante;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    // Buscar restaurantes ativos
    List<Restaurante> findByAtivoTrue();

    // Buscar restaurantes por nome (contendo, ignorando maiúsculas/minúsculas)
    List<Restaurante> findByNomeContainingIgnoreCase(String nome);

    // Verificar se já existe restaurante com determinado nome
    boolean existsByNomeIgnoreCase(String nome);
}