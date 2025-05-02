package com.example.demo.modelos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductInsumoDAO {

    public static List<ProductInsumo> getByProduct(int productId) throws SQLException {
        List<ProductInsumo> list = new ArrayList<>();
        String sql =
                "SELECT pi.insumo_id, i.name AS insumo, pi.quantity " +
                        "FROM product_insumos pi " +
                        "JOIN insumos i ON pi.insumo_id = i.id " +
                        "WHERE pi.product_id = ?";
        try (PreparedStatement ps = Conexion.connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ProductInsumo(
                            productId,
                            rs.getInt("insumo_id"),
                            rs.getString("insumo"),
                            rs.getDouble("quantity")
                    ));
                }
            }
        }
        return list;
    }

    public static void saveOrUpdate(ProductInsumo pi) throws SQLException {
        String upsert =
                "REPLACE INTO product_insumos(product_id, insumo_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = Conexion.connection.prepareStatement(upsert)) {
            ps.setInt(1, pi.getProductId());
            ps.setInt(2, pi.getInsumoId());
            ps.setDouble(3, pi.getQuantity());
            ps.executeUpdate();
        }
    }

    public static void delete(int productId, int insumoId) throws SQLException {
        String sql = "DELETE FROM product_insumos WHERE product_id = ? AND insumo_id = ?";
        try (PreparedStatement ps = Conexion.connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setInt(2, insumoId);
            ps.executeUpdate();
        }
    }
}
