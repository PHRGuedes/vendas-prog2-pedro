package br.com.pedro.vendas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.ArrayList;

import br.com.pedro.vendas.model.Fornecedor;
import br.com.pedro.vendas.util.ConnectionFactory;

public class FornecedorRepository {

    // 🔹 SALVAR
    public void salvar(Fornecedor fornecedor) {

        String sql = "INSERT INTO fornecedor (nomeFantasia, razaoSocial, cnpj) VALUES (?, ?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, fornecedor.getNomeFantasia());
            stmt.setString(2, fornecedor.getRazaoSocial());
            stmt.setString(3, fornecedor.getCnpj());

            stmt.executeUpdate();

            System.out.println("Fornecedor salvo!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 BUSCAR TODOS
    public List<Fornecedor> buscarTodos() {

        List<Fornecedor> lista = new ArrayList<>();

        String sql = "SELECT * FROM fornecedor";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fornecedor f = new Fornecedor();
                f.setId(rs.getInt("id"));
                f.setNomeFantasia(rs.getString("nomeFantasia"));
                f.setRazaoSocial(rs.getString("razaoSocial"));
                f.setCnpj(rs.getString("cnpj"));

                lista.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔹 ATUALIZAR
    public void atualizar(Fornecedor fornecedor) {

        String sql = "UPDATE fornecedor SET nomeFantasia = ?, razaoSocial = ?, cnpj = ? WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, fornecedor.getNomeFantasia());
            stmt.setString(2, fornecedor.getRazaoSocial());
            stmt.setString(3, fornecedor.getCnpj());
            stmt.setInt(4, fornecedor.getId());

            stmt.executeUpdate();

            System.out.println("Fornecedor atualizado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 DELETAR
    public void deletar(int id) {

        String sql = "DELETE FROM fornecedor WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate(); // 🔥 valida aqui

            if (linhas > 0) {
                System.out.println("Fornecedor deletado com sucesso!");
            } else {
                System.out.println("Fornecedor não encontrado!");
            }

        } catch (SQLIntegrityConstraintViolationException e) {

            System.out.println("Não é possível deletar este fornecedor pois ele está vinculado a compras cadastradas.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 BUSCAR POR ID
    public Fornecedor buscarPorId(int id) {

        String sql = "SELECT * FROM fornecedor WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Fornecedor f = new Fornecedor();
                    f.setId(rs.getInt("id"));
                    f.setNomeFantasia(rs.getString("nomeFantasia"));
                    f.setRazaoSocial(rs.getString("razaoSocial"));
                    f.setCnpj(rs.getString("cnpj"));

                    return f;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}