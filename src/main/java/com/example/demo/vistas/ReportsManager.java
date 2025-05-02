package com.example.demo.vistas;

import com.example.demo.componentes.ReportPDFGenerator;
import com.example.demo.modelos.ReportDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

public class ReportsManager {

    private Stage stage;
    private Scene scene;

    public void show() {
        stage = new Stage();
        stage.setTitle("Reportes");

        // Rango de fechas
        DatePicker fromPicker = new DatePicker(LocalDate.now().minusDays(7));
        DatePicker toPicker   = new DatePicker(LocalDate.now());

        // Gráfica 1: Productos más vendidos (barras)
        CategoryAxis xProd = new CategoryAxis();
        NumberAxis yProd = new NumberAxis();
        BarChart<String,Number> prodChart = new BarChart<>(xProd,yProd);
        prodChart.setTitle("Productos más vendidos");

        // Gráfica 2: Ventas por día (líneas)
        CategoryAxis xDay = new CategoryAxis();
        NumberAxis yDay = new NumberAxis();
        LineChart<String,Number> dayChart = new LineChart<>(xDay,yDay);
        dayChart.setTitle("Ventas por día");

        // Gráfica 3: Empleado top (barras)
        CategoryAxis xEmp = new CategoryAxis();
        NumberAxis yEmp = new NumberAxis();
        BarChart<String,Number> empChart = new BarChart<>(xEmp,yEmp);
        empChart.setTitle("Empleado con más ventas");

        // Cargar datos
        loadProductData(prodChart);
        loadDayData(dayChart, fromPicker.getValue(), toPicker.getValue());
        loadEmployeeData(empChart);

        Button exportBtn = new Button("Exportar PDF");
        exportBtn.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            File file = fc.showSaveDialog(stage);
            if (file != null) {
                try {
                    ReportPDFGenerator.generateReport(
                            file.getAbsolutePath(),
                            fromPicker.getValue(),
                            toPicker.getValue()
                    );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        VBox root = new VBox(10,
                new Label("Desde:"), fromPicker,
                new Label("Hasta:"), toPicker,
                prodChart, dayChart, empChart
        );
        root.setPadding(new Insets(10));

        // Al cambiar fechas, recargar ventas por día
        fromPicker.setOnAction(e -> loadDayData(dayChart, fromPicker.getValue(), toPicker.getValue()));
        toPicker.setOnAction(e -> loadDayData(dayChart, fromPicker.getValue(), toPicker.getValue()));

        HBox exportBox = new HBox(exportBtn);
        exportBox.setPadding(new Insets(10, 0, 10, 0));
        root.getChildren().add(2, exportBox);

        scene = new Scene(root, 800, 900);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        stage.setScene(scene);
        stage.show();
    }

    private void loadProductData(BarChart<String,Number> chart) {
        try {
            XYChart.Series<String,Number> series = new XYChart.Series<>();
            ReportDAO.topProducts(10).forEach(entry ->
                    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()))
            );
            chart.getData().setAll(series);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadDayData(LineChart<String,Number> chart, LocalDate from, LocalDate to) {
        try {
            XYChart.Series<String,Number> series = new XYChart.Series<>();
            for (Map.Entry<LocalDate,Integer> e : ReportDAO.salesByDay(from, to).entrySet()) {
                series.getData().add(new XYChart.Data<>(e.getKey().toString(), e.getValue()));
            }
            chart.getData().setAll(series);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadEmployeeData(BarChart<String,Number> chart) {
        try {
            XYChart.Series<String,Number> series = new XYChart.Series<>();
            ReportDAO.topEmployees(5).forEach(entry ->
                    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()))
            );
            chart.getData().setAll(series);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
