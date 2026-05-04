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

import br.com.pedro.vendas.model.Cliente;
import br.com.pedro.vendas.model.ItemVenda;
import br.com.pedro.vendas.model.Produto;
import br.com.pedro.vendas.model.Venda;
import br.com.pedro.vendas.repository.ItemVendaRepository;
import br.com.pedro.vendas.repository.ProdutoRepository;
import br.com.pedro.vendas.repository.VendaRepository;

public class VendaService {

    public boolean realizarVenda(Cliente cliente, Map<Produto, Integer> itens) {

        ProdutoRepository produtoRepository = new ProdutoRepository();
        VendaRepository vendaRepository = new VendaRepository();
        ItemVendaRepository itemVendaRepository = new ItemVendaRepository();

        // 🔹 validar limite por CPF
        int total = vendaRepository.contarVendasPorCpfNoMes(cliente.getCpf());

        if (total > 3) {
            System.out.println("Limite de vendas mensal atingido para este CPF.");
            return false;
        }

        // 🔹 validar estoque
        for (Produto produto : itens.keySet()) {

            int quantidade = itens.get(produto);

            if (produto.getQtdeEstoque() < quantidade) {
                System.out.println("Estoque insuficiente para: " + produto.getNome());
                return false;
            }
        }

        // 🔹 calcular total
        double valorTotal = 0;

        for (Produto produto : itens.keySet()) {
            int quantidade = itens.get(produto);
            valorTotal += produto.getPreco() * quantidade;
        }

        // 🔹 criar venda
        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setDataVenda(LocalDate.now());
        venda.setValorTotal(valorTotal);

        vendaRepository.salvar(venda); // precisa retornar ID!

        // 🔹 salvar itens + atualizar estoque
        for (Produto produto : itens.keySet()) {

            int quantidade = itens.get(produto);

            // atualizar estoque
            int novoEstoque = produto.getQtdeEstoque() - quantidade;
            produto.setQtdeEstoque(novoEstoque);
            produto.setValorUltimaVenda(produto.getPreco());

            produtoRepository.atualizar(produto);

            // salvar item_venda
            ItemVenda item = new ItemVenda();
            item.setVenda(venda);
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(produto.getPreco());

            itemVendaRepository.salvar(item);
        }

        System.out.println("Venda realizada com sucesso!");

        return true;
    }
}