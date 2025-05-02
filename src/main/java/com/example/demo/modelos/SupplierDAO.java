package com.example.demo.modelos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public static List<Supplier> getAll() throws SQLException {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT id, name, contact, phone FROM suppliers";
        try (Statement st = Conexion.connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("contact"),
                        rs.getString("phone")
                ));
            }
        }
        return list;
    }

    public static void saveOrUpdate(Supplier s) throws SQLException {
        if (s.getId() <= 0) {
            String ins = "INSERT INTO suppliers(name, contact, phone) VALUES(?,?,?)";
            try (PreparedStatement ps = Conexion.connection.prepareStatement(ins, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, s.getName());
                ps.setString(2, s.getContact());
                ps.setString(3, s.getPhone());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        s.setId(keys.getInt(1));
                    }
                }
            }
        } else {
            String upd = "UPDATE suppliers SET name = ?, contact = ?, phone = ? WHERE id = ?";
            try (PreparedStatement ps = Conexion.connection.prepareStatement(upd)) {
                ps.setString(1, s.getName());
                ps.setString(2, s.getContact());
                ps.setString(3, s.getPhone());
                ps.setInt(4, s.getId());
                ps.executeUpdate();
            }
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM suppliers WHERE id = ?";
        try (PreparedStatement ps = Conexion.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
