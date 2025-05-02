package com.example.demo.modelos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public static List<Product> getAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.id, p.name, c.name AS category, p.image_path " +
                "FROM products p " +
                "JOIN categories c ON p.category_id = c.id";
        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("image_path")
                ));
            }
        }
        return list;
    }

    public static void saveOrUpdate(Product p) throws SQLException {
        if (p.getId() <= 0) {
            String insert = "INSERT INTO products(name, category_id, image_path) " +
                    "VALUES(?, (SELECT id FROM categories WHERE name = ?), ?)";
            try (PreparedStatement ps = Conexion.connection.prepareStatement(insert)) {
                ps.setString(1, p.getName());
                ps.setString(2, p.getCategory());
                ps.setString(3, p.getImagePath());
                ps.executeUpdate();
            }
        } else {
            String update = "UPDATE products SET name = ?, category_id = (SELECT id FROM categories WHERE name = ?), image_path = ? " +
                    "WHERE id = ?";
            try (PreparedStatement ps = Conexion.connection.prepareStatement(update)) {
                ps.setString(1, p.getName());
                ps.setString(2, p.getCategory());
                ps.setString(3, p.getImagePath());
                ps.setInt(4, p.getId());
                ps.executeUpdate();
            }
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement ps = Conexion.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
