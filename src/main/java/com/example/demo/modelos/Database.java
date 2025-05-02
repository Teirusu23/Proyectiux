package com.example.demo.modelos;
import com.example.demo.vistas.VentasRestaurante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Connection conn = Conexion.connection;

    public static List<String> getCategories() throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT name FROM categories";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(rs.getString("name"));
        }
        return list;
    }

    public static List<Integer> getTables() throws SQLException {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT id FROM tables";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(rs.getInt("id"));
        }
        return list;
    }

    public static List<VentasRestaurante.Product> getProductsByCategory(String categoryName) throws SQLException {
        List<VentasRestaurante.Product> list = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.image_path FROM products p JOIN categories c ON p.category_id=c.id WHERE c.name=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoryName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new VentasRestaurante.Product(rs.getInt("id"), rs.getString("name"), rs.getString("image_path")));
                }
            }
        }
        return list;
    }
    public static int saveOrder(List<VentasRestaurante.OrderItem> items,
                                int tableId,
                                int employeeId,
                                int clientId) throws SQLException {
        String insertOrder =
                "INSERT INTO orders(table_id, employee_id, client_id) VALUES(?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(
                insertOrder, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, tableId);
            ps.setInt(2, employeeId);
            ps.setInt(3, clientId);
            ps.executeUpdate();

            // Obtener el ID generado
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int orderId = keys.getInt(1);

                    // Insertar los items
                    String insertItem =
                            "INSERT INTO order_items(order_id, product_id, quantity) VALUES(?,?,?)";
                    try (PreparedStatement psi = conn.prepareStatement(insertItem)) {
                        for (VentasRestaurante.OrderItem item : items) {
                            psi.setInt(1, orderId);
                            psi.setInt(2, item.getProduct().getId());
                            psi.setInt(3, item.getQuantity());
                            psi.addBatch();
                        }
                        psi.executeBatch();
                    }

                    return orderId;  // <— retornamos aquí
                } else {
                    throw new SQLException("No se pudo obtener el ID de la orden.");
                }
            }
        }
    }


    public static boolean validateAdmin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM admins WHERE username=? AND password=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static Connection getConnection()  {
        return conn;
    }
}
