package br.com.pedro.vendas.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.pedro.vendas.model.Categoria;
import br.com.pedro.vendas.model.Cliente;
import br.com.pedro.vendas.model.Produto;
import br.com.pedro.vendas.repository.ProdutoRepository;
import br.com.pedro.vendas.service.CompraService;
import br.com.pedro.vendas.service.VendaService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ProdutoRepository produtoRepository = new ProdutoRepository();
        VendaService vendaService = new VendaService();
        CompraService compraService = new CompraService();

        while (true) {

            System.out.println("\n===== SISTEMA DE VENDAS =====");
            System.out.println("1 - Listar produtos");
            System.out.println("2 - Realizar venda");
            System.out.println("3 - Realizar compra");
            System.out.println("0 - Sair");

            int opcao = sc.nextInt();

            switch (opcao) {

                case 1:
                    List<Produto> produtos = produtoRepository.buscarTodos();

                    System.out.println("\n--- PRODUTOS ---");
                    for (Produto p : produtos) {
                        System.out.println("ID: " + p.getId() +
                                " | Nome: " + p.getNome() +
                                " | Preço: " + p.getPreco() +
                                " | Estoque: " + p.getQtdeEstoque());
                    }
                    break;

                case 2:
                    sc.nextLine(); // limpar buffer

                    Cliente cliente = new Cliente();
                    System.out.print("CPF do cliente: ");
                    cliente.setCpf(sc.nextLine());

                    List<Produto> produtosVenda = new ArrayList<>();

                    System.out.print("ID do produto para vender: ");
                    int idVenda = sc.nextInt();

                    Produto produtoVenda = buscarPorId(produtoRepository.buscarTodos(), idVenda);

                    if (produtoVenda != null) {
                        produtosVenda.add(produtoVenda);

                        boolean sucesso = vendaService.realizarVenda(cliente, produtosVenda);

                        if (sucesso) {
                            System.out.println("Venda concluída!");
                        } else {
                            System.out.println("Venda NÃO realizada.");
                        }
                    } else {
                        System.out.println("Produto não encontrado.");
                    }

                    break;

                case 3:
                    List<Produto> produtosCompra = new ArrayList<>();

                    System.out.print("ID do produto para comprar: ");
                    int idCompra = sc.nextInt();

                    Produto produtoCompra = buscarPorId(produtoRepository.buscarTodos(), idCompra);

                    if (produtoCompra != null) {
                        produtosCompra.add(produtoCompra);

                        compraService.realizarCompra(produtosCompra);
                    } else {
                        System.out.println("Produto não encontrado.");
                    }

                    break;

                case 0:
                    System.out.println("Saindo...");
                    sc.close();
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // 🔹 método auxiliar
    public static Produto buscarPorId(List<Produto> lista, int id) {

        for (Produto p : lista) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }
}