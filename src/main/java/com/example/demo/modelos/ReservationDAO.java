package com.example.demo.modelos;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class ReservationDAO {

    public static List<Reservation> getAll() throws SQLException {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT id, cliente_id, table_id, date_time, persons, notes FROM reservations";
        try (Statement st = Conexion.connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setClienteId(rs.getInt("cliente_id"));
                r.setTableId(rs.getInt("table_id"));
                r.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                r.setPersons(rs.getInt("persons"));
                r.setNotes(rs.getString("notes"));
                list.add(r);
            }
        }
        return list;
    }

    public static void saveOrUpdate(Reservation r) throws SQLException {
        if (r.getId() <= 0) {
            String ins = "INSERT INTO reservations(cliente_id,table_id,date_time,persons,notes) VALUES(?,?,?,?,?)";
            try (PreparedStatement ps = Conexion.connection.prepareStatement(ins)) {
                ps.setInt(1, r.getClienteId());
                ps.setInt(2, r.getTableId());
                ps.setTimestamp(3, Timestamp.valueOf(r.getDateTime()));
                ps.setInt(4, r.getPersons());
                ps.setString(5, r.getNotes());
                ps.executeUpdate();
            }
        } else {
            String upd = "UPDATE reservations SET cliente_id=?,table_id=?,date_time=?,persons=?,notes=? WHERE id=?";
            try (PreparedStatement ps = Conexion.connection.prepareStatement(upd)) {
                ps.setInt(1, r.getClienteId());
                ps.setInt(2, r.getTableId());
                ps.setTimestamp(3, Timestamp.valueOf(r.getDateTime()));
                ps.setInt(4, r.getPersons());
                ps.setString(5, r.getNotes());
                ps.setInt(6, r.getId());
                ps.executeUpdate();
            }
        }
    }

    public static void delete(int id) throws SQLException {
        try (PreparedStatement ps = Conexion.connection
                .prepareStatement("DELETE FROM reservations WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
