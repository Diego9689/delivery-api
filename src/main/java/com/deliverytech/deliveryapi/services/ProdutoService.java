package com.deliverytech.deliveryapi.services;

import com.deliverytech.deliveryapi.entity.Produto;

import com.deliverytech.deliveryapi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Cadastrar novo produto
     */
    public Produto cadastrar(Produto produto) {
        validarDadosProduto(produto);

        // Verificar se já existe produto com mesmo nome
        if (produtoRepository.existsByNomeIgnoreCase(produto.getNome())) {
            throw new IllegalArgumentException("Produto já cadastrado: " + produto.getNome());
        }

        produto.setAtivo(true);
        return produtoRepository.save(produto);
    }

    /**
     * Buscar produto por ID
     */
    @Transactional(readOnly = true)
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    /**
     * Listar todos os produtos ativos
     */
    @Transactional(readOnly = true)
    public List<Produto> listarAtivos() {
        return produtoRepository.findByAtivoTrue();
    }

    /**
     * Atualizar produto
     */
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produto = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

        // Verificar se nome está sendo alterado para um já existente
        if (!produto.getNome().equalsIgnoreCase(produtoAtualizado.getNome()) &&
                produtoRepository.existsByNomeIgnoreCase(produtoAtualizado.getNome())) {
            throw new IllegalArgumentException("Já existe outro produto com esse nome: " + produtoAtualizado.getNome());
        }

        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());

        return produtoRepository.save(produto);
    }

    /**
     * Inativar produto (soft delete)
     */
    public void inativar(Long id) {
        Produto produto = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

        produto.inativar();
        produtoRepository.save(produto);
    }

    /**
     * Buscar produtos por nome
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Validações de negócio
     */
    private void validarDadosProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        if (produto.getQuantidadeEstoque() == null || produto.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa");
        }
    }
}