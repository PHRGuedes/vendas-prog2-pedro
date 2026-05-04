// validar estoque
// diminuir estoque
// atualizar produto
// guardar valorUltimaVenda
//O sistema não pode realizar uma venda de um produto cujo seu estoque seja inferior a 1.
//Ao fazer uma venda, o sistema deve verificar quantas vendas já foram realizadas por CPF. Se, dentro do mesmo mês, já foram realizadas mais de 3 vendas para o CPF, abortar a operação.
//Ao fazer uma compra, atualizar o campo preco_medio do produto com a média de preços de compra do produto na compra

package br.com.pedro.vendas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import br.com.pedro.vendas.model.Fornecedor;
import br.com.pedro.vendas.model.Produto;
import br.com.pedro.vendas.repository.ProdutoRepository;

public class CompraService {

    public void realizarCompra(List<Produto> produtos, Map<Integer, Integer> quantidades, LocalDate data, Fornecedor fornecedor) {

        ProdutoRepository produtoRepository = new ProdutoRepository();

        for (Produto produto : produtos) {

            int quantidade = quantidades.get(produto.getId());

            int novoEstoque = produto.getQtdeEstoque() + quantidade;
            produto.setQtdeEstoque(novoEstoque);

            int qtd = produto.getQuantidadeCompras();
            double precoCompra = produto.getPreco();

            double novoPrecoMedio = ((produto.getPrecoMedio() * qtd) + precoCompra) / (qtd + 1);

            produto.setPrecoMedio(novoPrecoMedio);
            produto.setQuantidadeCompras(qtd + 1);
            produto.setValorUltimaCompra(precoCompra);

            produtoRepository.atualizar(produto);
        }

        System.out.println("Compra realizada com sucesso!");
    }
}