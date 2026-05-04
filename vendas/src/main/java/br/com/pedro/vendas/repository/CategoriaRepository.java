package br.com.pedro.vendas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.ArrayList;

import br.com.pedro.vendas.model.Categoria;
import br.com.pedro.vendas.util.ConnectionFactory;

public class CategoriaRepository {

    // 🔹 SALVAR
    public void salvar(Categoria categoria) {

        String sql = "INSERT INTO categoria (nome) VALUES (?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.executeUpdate();

            System.out.println("Categoria salva!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 BUSCAR TODOS
    public List<Categoria> buscarTodos() {

        List<Categoria> lista = new ArrayList<>();

        String sql = "SELECT * FROM categoria";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));

                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔹 ATUALIZAR
    public void atualizar(Categoria categoria) {

        String sql = "UPDATE categoria SET nome = ? WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.setInt(2, categoria.getId());

            stmt.executeUpdate();

            System.out.println("Categoria atualizada!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 DELETAR
    public void deletar(int id) {

        String sql = "DELETE FROM categoria WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate(); // 🔥 valida aqui

            if (linhas > 0) {
                System.out.println("Categoria deletada!");
            } else {
                System.out.println("Categoria não encontrada!");
            }

        } 
        catch (SQLIntegrityConstraintViolationException e) {

            System.out.println("Não pode deletar pois está vinculado a produtos.");
 } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 🔹 BUSCAR POR ID
    public Categoria buscarPorId(int id) {

        String sql = "SELECT * FROM categoria WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Categoria c = new Categoria();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));

                    return c;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}