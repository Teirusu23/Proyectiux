package com.example.demo.modelos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsumoDAO {

    public static List<Insumo> getAll() throws SQLException {
        List<Insumo> list = new ArrayList<>();
        String sql = "SELECT id, name, unit, supplier_id FROM insumos";
        try (Statement st = Conexion.connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Insumo(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("unit"),
                        rs.getInt("supplier_id")
                ));
            }
        }
        return list;
    }

    public static void saveOrUpdate(Insumo i) throws SQLException {
        if (i.getId() <= 0) {
            String ins = "INSERT INTO insumos(name, unit, supplier_id) VALUES(?,?,?)";
            try (PreparedStatement ps = Conexion.connection.prepareStatement(ins)) {
                ps.setString(1, i.getName());
                ps.setString(2, i.getUnit());
                ps.setInt(3, i.getSupplierId());
                ps.executeUpdate();
            }
        } else {
            String upd = "UPDATE insumos SET name=?, unit=?, supplier_id=? WHERE id=?";
            try (PreparedStatement ps = Conexion.connection.prepareStatement(upd)) {
                ps.setString(1, i.getName());
                ps.setString(2, i.getUnit());
                ps.setInt(3, i.getSupplierId());
                ps.setInt(4, i.getId());
                ps.executeUpdate();
            }
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM insumos WHERE id = ?";
        try (PreparedStatement ps = Conexion.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
