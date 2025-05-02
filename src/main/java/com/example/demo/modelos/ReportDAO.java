package com.example.demo.modelos;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class ReportDAO {

    /** Lista de pares (nombreProducto, totalVendido) ordenados desc. */
    public static List<Map.Entry<String,Integer>> topProducts(int limit) throws SQLException {
        String sql =
                "SELECT p.name, SUM(oi.quantity) AS total " +
                        "FROM order_items oi " +
                        " JOIN products p ON oi.product_id = p.id " +
                        " GROUP BY p.name " +
                        " ORDER BY total DESC " +
                        " LIMIT ?";
        List<Map.Entry<String,Integer>> result = new ArrayList<>();
        try (PreparedStatement ps = Conexion.connection.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new AbstractMap.SimpleEntry<>(
                            rs.getString("name"),
                            rs.getInt("total")
                    ));
                }
            }
        }
        return result;
    }

    /** Mapa fecha → total de ventas (count ordenes). */
    public static Map<LocalDate, Integer> salesByDay(LocalDate from, LocalDate to) throws SQLException {
        String sql =
                "SELECT DATE(o.date) AS day, COUNT(*) AS count " +
                        "FROM orders o " +
                        " WHERE o.date BETWEEN ? AND ? " +
                        " GROUP BY day " +
                        " ORDER BY day";
        Map<LocalDate,Integer> result = new LinkedHashMap<>();
        try (PreparedStatement ps = Conexion.connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.put(rs.getDate("day").toLocalDate(), rs.getInt("count"));
                }
            }
        }
        return result;
    }

    /** Lista (nombreEmpleado, totalVentas) desc. */
    public static List<Map.Entry<String,Integer>> topEmployees(int limit) throws SQLException {
        String sql =
                "SELECT e.name, COUNT(*) AS total " +
                        " FROM orders o " +
                        " JOIN employees e ON o.employee_id = e.id " +
                        " GROUP BY e.name " +
                        " ORDER BY total DESC " +
                        " LIMIT ?";
        List<Map.Entry<String,Integer>> result = new ArrayList<>();
        try (PreparedStatement ps = Conexion.connection.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new AbstractMap.SimpleEntry<>(
                            rs.getString("name"),
                            rs.getInt("total")
                    ));
                }
            }
        }
        return result;
    }
}
