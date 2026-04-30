package br.com.pedro.vendas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.pedro.vendas.util.ConnectionFactory;

public class VendaRepository {
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
}
