package br.com.pedro.vendas.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.pedro.vendas.model.Cliente;
import br.com.pedro.vendas.model.Venda;
import br.com.pedro.vendas.util.ConnectionFactory;

public class VendaRepository {

    // ================= CONTAR VENDAS POR CPF =================
    public int contarVendasPorCpfNoMes(String cpf) {

        int total = 0;

        String sql = "SELECT COUNT(*) AS total " +
                     "FROM venda v " +
                     "JOIN cliente c ON v.clienteId = c.id " +
                     "WHERE c.cpf = ? " +
                     "AND MONTH(v.dataVenda) = MONTH(CURRENT_DATE()) " +
                     "AND YEAR(v.dataVenda) = YEAR(CURRENT_DATE())";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    // ================= SALVAR =================
    public void salvar(Venda venda) {

        String sql = "INSERT INTO venda (dataVenda, valorTotal, clienteId) VALUES (?, ?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(venda.getDataVenda()));
            stmt.setDouble(2, venda.getValorTotal());
            stmt.setInt(3, venda.getCliente().getId());

            stmt.executeUpdate();

            // 🔥 pegar ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                venda.setId(rs.getInt(1));
            }

            System.out.println("Venda salva!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= LISTAR =================
    public List<Venda> buscarTodos() {

        List<Venda> lista = new ArrayList<>();

        String sql = "SELECT * FROM venda";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Venda v = new Venda();

                v.setId(rs.getInt("id"));
                v.setDataVenda(rs.getDate("dataVenda").toLocalDate());
                v.setValorTotal(rs.getDouble("valorTotal"));

                Cliente c = new Cliente();
                c.setId(rs.getInt("clienteId"));
                v.setCliente(c);

                lista.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ================= BUSCAR POR ID =================
    public Venda buscarPorId(int id) {

        String sql = "SELECT * FROM venda WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Venda v = new Venda();

                v.setId(rs.getInt("id"));
                v.setDataVenda(rs.getDate("dataVenda").toLocalDate());
                v.setValorTotal(rs.getDouble("valorTotal"));

                Cliente c = new Cliente();
                c.setId(rs.getInt("clienteId"));
                v.setCliente(c);

                return v;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ================= ATUALIZAR =================
    public void atualizar(Venda venda) {

        String sql = "UPDATE venda SET dataVenda = ?, valorTotal = ?, clienteId = ? WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(venda.getDataVenda()));
            stmt.setDouble(2, venda.getValorTotal());
            stmt.setInt(3, venda.getCliente().getId());
            stmt.setInt(4, venda.getId());

            stmt.executeUpdate();

            System.out.println("Venda atualizada!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= DELETAR =================
    public void deletar(int id) {

        String sql = "DELETE FROM venda WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Venda deletada!");
            } else {
                System.out.println("Venda não encontrada!");
            }

        } catch (SQLIntegrityConstraintViolationException e) {

            System.out.println("Não pode deletar esta venda pois possui itens vinculados.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}