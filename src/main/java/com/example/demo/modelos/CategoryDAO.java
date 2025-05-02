package com.example.demo.modelos;

import java.sql.*;
import java.util.*;

public class CategoryDAO {
    public static List<Category> getAll() throws SQLException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT id, name, description FROM categories";
        try (Statement st = Conexion.connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
        }
        return list;
    }
    public static void saveOrUpdate(Category c) throws SQLException {
        if (c.getId() <= 0) {
            String ins = "INSERT INTO categories(name,description) VALUES(?,?)";
            try (PreparedStatement ps = Conexion.connection.prepareStatement(ins)) {
                ps.setString(1, c.getName());
                ps.setString(2, c.getDescription());
                ps.executeUpdate();
            }
        } else {
            String upd = "UPDATE categories SET name=?,description=? WHERE id=?";
            try (PreparedStatement ps = Conexion.connection.prepareStatement(upd)) {
                ps.setString(1, c.getName());
                ps.setString(2, c.getDescription());
                ps.setInt(3, c.getId());
                ps.executeUpdate();
            }
        }
    }
    public static void delete(int id) throws SQLException {
        try (PreparedStatement ps = Conexion.connection
                .prepareStatement("DELETE FROM categories WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
