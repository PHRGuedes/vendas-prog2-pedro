package br.com.pedro.vendas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import br.com.pedro.vendas.model.ItemCompra;
import br.com.pedro.vendas.util.ConnectionFactory;

public class ItemCompraRepository {

    public void salvar(ItemCompra itemCompra) {
        String sql = "INSERT INTO item_compra (compraId, produtoId, quantidade, precoUnitario) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, itemCompra.getCompra().getId());
            stmt.setInt(2, itemCompra.getProduto().getId());
            stmt.setInt(3, itemCompra.getQuantidade());
            stmt.setDouble(4, itemCompra.getPrecoUnitario());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    itemCompra.setId(rs.getInt(1));
                }
            }

            System.out.println("Item de compra salvo!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
