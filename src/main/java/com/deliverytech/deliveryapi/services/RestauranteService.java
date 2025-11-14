package com.deliverytech.deliveryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.deliveryapi.entity.Produto;
import com.deliverytech.deliveryapi.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    /**
     * Cadastrar novo restaurante
     */
    public Restaurante cadastrar(Restaurante restaurante) {
        validarDados(restaurante);

        if (restauranteRepository.existsByNomeIgnoreCase(restaurante.getNome())) {
            throw new IllegalArgumentException("Já existe um restaurante com esse nome: " + restaurante.getNome());
        }

        restaurante.setAtivo(true);
        return restauranteRepository.save(restaurante);
    }

    /**
     * Buscar restaurante por ID
     */
    @Transactional(readOnly = true)
    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    /**
     * Listar restaurantes ativos
     */
    @Transactional(readOnly = true)
    public List<Restaurante> listarAtivos() {
        return restauranteRepository.findByAtivoTrue();
    }

    /**
     * Atualizar restaurante
     */
    public Restaurante atualizar(Long id, Restaurante atualizado) {
        Restaurante restaurante = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        if (!restaurante.getNome().equalsIgnoreCase(atualizado.getNome()) &&
                restauranteRepository.existsByNomeIgnoreCase(atualizado.getNome())) {
            throw new IllegalArgumentException("Nome já está em uso por outro restaurante: " + atualizado.getNome());
        }

        restaurante.setNome(atualizado.getNome());
        restaurante.setEndereco(atualizado.getEndereco());
        restaurante.setTelefone(atualizado.getTelefone());
        restaurante.setCategoria(atualizado.getCategoria());

        return restauranteRepository.save(restaurante);
    }

    /**
     * Inativar restaurante (soft delete)
     */
    public void inativar(Long id) {
        Restaurante restaurante = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        restaurante.inativar();
        restauranteRepository.save(restaurante);
    }

    /**
     * Buscar restaurantes por nome
     */
    @Transactional(readOnly = true)
    public List<Restaurante> buscarPorNome(String nome) {
        return restauranteRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Validações de negócio
     */
    private void validarDados(Restaurante restaurante) {
        if (restaurante.getNome() == null || restaurante.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (restaurante.getEndereco() == null || restaurante.getEndereco().trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço é obrigatório");
        }

        if (restaurante.getTelefone() == null || restaurante.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }

        if (restaurante.getCategoria() == null || restaurante.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }
    }
}