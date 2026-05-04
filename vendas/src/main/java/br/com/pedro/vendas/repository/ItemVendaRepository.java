package br.com.pedro.vendas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import br.com.pedro.vendas.model.ItemVenda;
import br.com.pedro.vendas.util.ConnectionFactory;

public class ItemVendaRepository {

    public void salvar(ItemVenda itemVenda) {
        String sql = "INSERT INTO item_venda (vendaId, produtoId, quantidade, precoUnitario) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, itemVenda.getVenda().getId());
            stmt.setInt(2, itemVenda.getProduto().getId());
            stmt.setInt(3, itemVenda.getQuantidade());
            stmt.setDouble(4, itemVenda.getPrecoUnitario());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    itemVenda.setId(rs.getInt(1));
                }
            }

            System.out.println("Item de venda salvo!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
