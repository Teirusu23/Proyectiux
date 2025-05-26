package com.example.demo.componentes;

import com.example.demo.modelos.Database;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.ListNumberingType;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class TicketPDFGenerator {

    public static void generateTicket(int orderId, String dest) throws Exception {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        // Cabecera: número de ticket
        doc.add(new Paragraph("Ticket de Compra")
                .setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));

        // Datos de la orden
        String sql = "SELECT o.id, o.date, t.id AS table_no, e.name AS emp, c.nomCte AS client " +
                "FROM orders o " +
                " JOIN tables t ON o.table_id = t.id " +
                " JOIN employees e ON o.employee_id = e.id " +
                " JOIN clientes c ON o.client_id = c.idCte " +
                "WHERE o.id = ?";
        try (PreparedStatement ps = Database.getConnection()
                .prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    doc.add(new Paragraph(
                            "Ticket No. " + rs.getInt("id") +
                                    "    Fecha: " + rs.getTimestamp("date").toLocalDateTime()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
                    doc.add(new Paragraph(
                            "Mesa: " + rs.getInt("table_no") +
                                    "    Empleado: " + rs.getString("emp")));
                    doc.add(new Paragraph("Cliente: " + rs.getString("client"))
                            .setMarginBottom(10));
                }
            }
        }

        // Detalle de items
        BigDecimal total = BigDecimal.ZERO;

        doc.add(new Paragraph("Productos:").setBold().setMarginTop(10));

        List itemList = new List(ListNumberingType.DECIMAL);
        String itemsSql =
                "SELECT p.name, oi.quantity, p.price FROM order_items oi " +
                        " JOIN products p ON oi.product_id = p.id " +
                        " WHERE oi.order_id = ?";
        try (PreparedStatement psi = Database.getConnection()
                .prepareStatement(itemsSql)) {
            psi.setInt(1, orderId);
            try (ResultSet rs2 = psi.executeQuery()) {
                while (rs2.next()) {
                            String name = rs2.getString("name");
                            int qty = rs2.getInt("quantity");
                            BigDecimal price = rs2.getBigDecimal("price");
                            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(qty));
                            total = total.add(subtotal);

                            String line = String.format("%s $%.2f x %d = $%.2f",
                                    name,
                                    price,
                                    qty,
                                    subtotal);
                    itemList.add(new ListItem(line));
                }
            }
        }
        doc.add(itemList);

        doc.add(new Paragraph(String.format("Total: $%.2f", total)))
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT)
                .setTopMargin(10);

        doc.close();
    }
}