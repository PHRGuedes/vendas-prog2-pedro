package br.com.pedro.vendas.main;

import java.sql.Connection;
import java.util.List;

import br.com.pedro.vendas.model.Categoria;
import br.com.pedro.vendas.model.Cliente;
import br.com.pedro.vendas.model.Fornecedor;
import br.com.pedro.vendas.model.Produto;
import br.com.pedro.vendas.repository.ClienteRepository;
import br.com.pedro.vendas.repository.FornecedorRepository;
import br.com.pedro.vendas.repository.ProdutoRepository;
import br.com.pedro.vendas.util.ConnectionFactory;

public class Main {

    public static void main(String[] args) {

        Connection con = ConnectionFactory.getConnection();
        System.out.println("Conectou!");

       ProdutoRepository repo = new ProdutoRepository();


List<Produto> lista = repo.buscarTodos();

for (Produto p : lista) {
    System.out.println(p.getNome());
}

    }
}