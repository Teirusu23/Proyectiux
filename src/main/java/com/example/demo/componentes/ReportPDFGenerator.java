package com.example.demo.componentes;

import com.example.demo.modelos.ReportDAO;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.borders.*;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ReportPDFGenerator {

    public static void generateReport(String dest, LocalDate from, LocalDate to) throws Exception {
        PdfWriter writer = new PdfWriter(new FileOutputStream(dest));
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        // Título
        doc.add(new Paragraph("Reporte de Ventas")
                .setFont(PdfFontFactory.createFont())
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20)
        );

        // 1) Productos más vendidos
        doc.add(new Paragraph("1. Productos más vendidos")
                .setFontSize(14).setBold().setMarginBottom(10));
        Table prodTable = new Table(UnitValue.createPercentArray(new float[]{70,30}))
                .useAllAvailableWidth();
        prodTable.addHeaderCell(createHeaderCell("Producto"));
        prodTable.addHeaderCell(createHeaderCell("Total vendido"));
        List<Entry<String,Integer>> topProducts = ReportDAO.topProducts(50);
        for (Entry<String,Integer> e : topProducts) {
            prodTable.addCell(new Cell().add(new Paragraph(e.getKey())));
            prodTable.addCell(new Cell().add(new Paragraph(e.getValue().toString())));
        }
        doc.add(prodTable.setMarginBottom(20));

        // 2) Ventas por día
        doc.add(new Paragraph("2. Ventas por día (" + from + " – " + to + ")")
                .setFontSize(14).setBold().setMarginBottom(10));
        Table dayTable = new Table(UnitValue.createPercentArray(new float[]{50,50}))
                .useAllAvailableWidth();
        dayTable.addHeaderCell(createHeaderCell("Fecha"));
        dayTable.addHeaderCell(createHeaderCell("Órdenes"));
        Map<LocalDate,Integer> salesByDay = ReportDAO.salesByDay(from, to);
        for (Entry<LocalDate,Integer> e : salesByDay.entrySet()) {
            dayTable.addCell(new Cell().add(new Paragraph(e.getKey().toString())));
            dayTable.addCell(new Cell().add(new Paragraph(e.getValue().toString())));
        }
        doc.add(dayTable.setMarginBottom(20));

        // 3) Empleado con más ventas
        doc.add(new Paragraph("3. Empleado con más ventas")
                .setFontSize(14).setBold().setMarginBottom(10));
        Table empTable = new Table(UnitValue.createPercentArray(new float[]{70,30}))
                .useAllAvailableWidth();
        empTable.addHeaderCell(createHeaderCell("Empleado"));
        empTable.addHeaderCell(createHeaderCell("Órdenes atendidas"));
        List<Entry<String,Integer>> topEmps = ReportDAO.topEmployees(50);
        for (Entry<String,Integer> e : topEmps) {
            empTable.addCell(new Cell().add(new Paragraph(e.getKey())));
            empTable.addCell(new Cell().add(new Paragraph(e.getValue().toString())));
        }
        doc.add(empTable);

        doc.close();
    }

    private static Cell createHeaderCell(String text) {
        return new Cell().add(new Paragraph(text))
                .setBackgroundColor(new DeviceGray(0.75f))
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(5)
                .setBorder(new SolidBorder(1));
    }
}
