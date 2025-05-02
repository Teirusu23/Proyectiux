package com.example.demo.modelos;

import java.sql.*;
import java.util.*;

public class TableDAO {
    public static List<Table> getAll() throws SQLException {
        List<Table> list = new ArrayList<>();
        String sql = "SELECT id FROM tables";
        try (Statement st = Conexion.connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Table(rs.getInt("id")));
            }
        }
        return list;
    }

    public static void saveOrUpdate(Table t) throws SQLException {
        if (t.getId() <= 0) {
            throw new IllegalArgumentException("El ID de mesa debe ser mayor que 0");
        } else {
            // En este caso solo permitimos INSERT; UPDATE no aplica al PK
            String ins = "INSERT INTO tables(id) VALUES(?)";
            try (PreparedStatement ps = Conexion.connection.prepareStatement(ins)) {
                ps.setInt(1, t.getId());
                ps.executeUpdate();
            }
        }
    }

    public static void delete(int id) throws SQLException {
        try (PreparedStatement ps = Conexion.connection
                .prepareStatement("DELETE FROM tables WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
