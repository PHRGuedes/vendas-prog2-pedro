package br.com.pedro.vendas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.pedro.vendas.model.Categoria;
import br.com.pedro.vendas.model.Produto;
import br.com.pedro.vendas.util.ConnectionFactory;

public class ProdutoRepository {


    public void salvar(Produto produto) {

    if (produto.getCategoria() == null || produto.getCategoria().getId() == 0) {
        System.out.println("Produto precisa ter categoria!");
        return;
    }

    String sql = "INSERT INTO produto (nome, preco, qtdeEstoque, categoriaId) VALUES (?, ?, ?, ?)";

    try (Connection con = ConnectionFactory.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, produto.getNome());
        stmt.setDouble(2, produto.getPreco());
        stmt.setInt(3, produto.getQtdeEstoque());
        stmt.setInt(4, produto.getCategoria().getId());

        stmt.executeUpdate();

        System.out.println("Produto salvo!");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    public List<Produto> buscarTodos() {

        List<Produto> lista = new ArrayList<>();

        String sql = "SELECT * FROM produto";

        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                p.setQtdeEstoque(rs.getInt("qtdeEstoque"));
                p.setPrecoMedio(rs.getDouble("precoMedio"));
                p.setValorUltimaCompra(rs.getDouble("valorUltimaCompra"));
                p.setValorUltimaVenda(rs.getDouble("valorUltimaVenda"));
                p.setQuantidadeCompras(rs.getInt("quantidadeCompras"));

                // categoria
                Categoria c = new Categoria();
                c.setId(rs.getInt("categoriaId"));
                p.setCategoria(c);

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void atualizar(Produto produto) {

        String sql = "UPDATE produto SET nome = ?, preco = ?, qtdeEstoque = ?, precoMedio = ?, valorUltimaCompra = ?, valorUltimaVenda = ?, quantidadeCompras = ?, categoriaId = ? WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQtdeEstoque());

            stmt.setDouble(4, produto.getPrecoMedio());
            stmt.setDouble(5, produto.getValorUltimaCompra());
            stmt.setDouble(6, produto.getValorUltimaVenda());
            stmt.setInt(7, produto.getQuantidadeCompras());

            stmt.setInt(8, produto.getCategoria().getId());
            stmt.setInt(9, produto.getId());

            stmt.executeUpdate();

            System.out.println("Produto atualizado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {

        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

            System.out.println("Produto deletado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}













