package br.com.pedro.vendas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.ArrayList;

import br.com.pedro.vendas.model.Cliente;
import br.com.pedro.vendas.util.ConnectionFactory;

public class ClienteRepository {

    // 🔹 SALVAR
    public void salvar(Cliente cliente) {

        String sql = "INSERT INTO cliente (nome, cpf, rg, endereco, telefone) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getRg());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getTelefone());

            stmt.executeUpdate();

            System.out.println("Cliente salvo!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 BUSCAR TODOS
    public List<Cliente> buscarTodos() {

        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM cliente";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setRg(rs.getString("rg"));
                c.setEndereco(rs.getString("endereco"));
                c.setTelefone(rs.getString("telefone"));

                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔹 ATUALIZAR
    public void atualizar(Cliente cliente) {

        String sql = "UPDATE cliente SET nome = ?, cpf = ?, rg = ?, endereco = ?, telefone = ? WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getRg());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getTelefone());
            stmt.setInt(6, cliente.getId());

            stmt.executeUpdate();

            System.out.println("Cliente atualizado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 DELETAR
    public void deletar(int id) {

        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Cliente deletado!");

        } 
        
        catch (SQLIntegrityConstraintViolationException e) {
        System.out.println("Não pode deletar pois está vinculado a produtos.");
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 BUSCAR POR ID
    public Cliente buscarPorId(int id) {

        String sql = "SELECT * FROM cliente WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCpf(rs.getString("cpf"));
                    c.setRg(rs.getString("rg"));
                    c.setEndereco(rs.getString("endereco"));
                    c.setTelefone(rs.getString("telefone"));

                    return c;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // BUSCAR POR CPF
    public Cliente buscarPorCpf(String cpf) {

        String sql = "SELECT * FROM cliente WHERE cpf = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCpf(rs.getString("cpf"));
                    c.setRg(rs.getString("rg"));
                    c.setEndereco(rs.getString("endereco"));
                    c.setTelefone(rs.getString("telefone"));

                    return c;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}