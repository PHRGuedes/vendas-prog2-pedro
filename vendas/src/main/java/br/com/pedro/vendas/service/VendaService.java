// validar estoque
// diminuir estoque
// atualizar produto
// guardar valorUltimaVenda
//O sistema não pode realizar uma venda de um produto cujo seu estoque seja inferior a 1.
//Ao fazer uma venda, o sistema deve verificar quantas vendas já foram realizadas por CPF. Se, dentro do mesmo mês, já foram realizadas mais de 3 vendas para o CPF, abortar a operação.
//Ao fazer uma compra, atualizar o campo preco_medio do produto com a média de preços de compra do produto na compra

package br.com.pedro.vendas.service;

import java.util.List;

import br.com.pedro.vendas.model.Cliente;
import br.com.pedro.vendas.model.Produto;
import br.com.pedro.vendas.repository.ProdutoRepository;
import br.com.pedro.vendas.repository.VendaRepository;

public class VendaService {

    public boolean realizarVenda(Cliente cliente, List<Produto> produtos) {

        ProdutoRepository produtoRepository = new ProdutoRepository();
        VendaRepository vendaRepository = new VendaRepository();

        // validar limite por CPF
        int total = vendaRepository.contarVendasPorCpfNoMes(cliente.getCpf());

        if (total > 3) {
            System.out.println("Limite de vendas mensal atingido para este CPF.");
            return false;
        }

        // validar estoque
        for (Produto produto : produtos) {
            if (produto.getQtdeEstoque() < 1) {
                System.out.println("Estoque insuficiente para o produto: " + produto.getNome());
                return false;
            }
        }

        // aplicar venda
        for (Produto produto : produtos) {

            int novoEstoque = produto.getQtdeEstoque() - 1;
            produto.setQtdeEstoque(novoEstoque);

            produto.setValorUltimaVenda(produto.getPreco());

            produtoRepository.atualizar(produto);
        }

        System.out.println("Venda realizada com sucesso!");

        return true;
    }
}