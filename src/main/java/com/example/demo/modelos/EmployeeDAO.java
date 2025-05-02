package com.example.demo.modelos;

import java.sql.*;
import java.util.*;

public class EmployeeDAO {
    public static List<Employee> getAll() throws SQLException {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT id,name,rfc,phone,position FROM employees";
        try (Statement st = Conexion.connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("rfc"),
                        rs.getString("phone"),
                        rs.getString("position")
                ));
            }
        }
        return list;
    }
    public static void saveOrUpdate(Employee e) throws SQLException {
        if (e.getId()<=0) {
            String ins="INSERT INTO employees(name,rfc,phone,position) VALUES(?,?,?,?)";
            try (PreparedStatement ps=Conexion.connection.prepareStatement(ins)) {
                ps.setString(1,e.getName());
                ps.setString(2,e.getRfc());
                ps.setString(3,e.getPhone());
                ps.setString(4,e.getPosition());
                ps.executeUpdate();
            }
        } else {
            String upd="UPDATE employees SET name=?,rfc=?,phone=?,position=? WHERE id=?";
            try (PreparedStatement ps=Conexion.connection.prepareStatement(upd)) {
                ps.setString(1,e.getName());
                ps.setString(2,e.getRfc());
                ps.setString(3,e.getPhone());
                ps.setString(4,e.getPosition());
                ps.setInt(5,e.getId());
                ps.executeUpdate();
            }
        }
    }
    public static void delete(int id) throws SQLException {
        try (PreparedStatement ps=Conexion.connection
                .prepareStatement("DELETE FROM employees WHERE id=?")) {
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }
}
