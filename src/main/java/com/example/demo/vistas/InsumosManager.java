package com.example.demo.vistas;

import com.example.demo.modelos.Insumo;
import com.example.demo.modelos.InsumoDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class InsumosManager {

    private Stage stage;
    private Scene scene;
    private TableView<Insumo> table;

    public void show() {
        stage = new Stage();
        stage.setTitle("Gestión de Insumos");

        // Definir columnas
        table = new TableView<>();
        TableColumn<Insumo,Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(d ->
                new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getId()));

        TableColumn<Insumo,String> colName = new TableColumn<>("Nombre");
        colName.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getName()));

        TableColumn<Insumo,String> colUnit = new TableColumn<>("Unidad");
        colUnit.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getUnit()));

        TableColumn<Insumo,Integer> colSupplier = new TableColumn<>("Proveedor ID");
        colSupplier.setCellValueFactory(d ->
                new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getSupplierId()));

        table.getColumns().addAll(colId, colName, colUnit, colSupplier);

        // Botones
        Button add = new Button("Agregar");
        add.setOnAction(e -> onAdd());
        Button edit = new Button("Editar");
        edit.setOnAction(e -> onEdit());
        Button del = new Button("Eliminar");
        del.setOnAction(e -> onDelete());

        HBox controls = new HBox(10, add, edit, del);
        controls.setPadding(new Insets(10));

        VBox root = new VBox(10, controls, table);
        root.setPadding(new Insets(10));
        scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        stage.setScene(scene);

        loadData();
        stage.show();
    }

    private void loadData() {
        try {
            List<Insumo> list = InsumoDAO.getAll();
            table.getItems().setAll(list);
        } catch (Exception ex) {
            showAlert(ex.getMessage());}
    }

    private void onAdd() {
        InsumoDialog dlg = new InsumoDialog(stage);
        Insumo i = dlg.showDialog(null);
        if (i != null) {
            try {
                InsumoDAO.saveOrUpdate(i);
                loadData();
            } catch (Exception ex) {
                showAlert("No se pudo guardar porque: " + ex.getMessage());}
        }
    }

    private void onEdit() {
        Insumo selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        InsumoDialog dlg = new InsumoDialog(stage);
        Insumo updated = dlg.showDialog(selected);
        if (updated != null) {
            try {
                InsumoDAO.saveOrUpdate(updated);
                loadData();
            } catch (Exception ex) {
                showAlert("No se pudo guardar porque: " + ex.getMessage());}
        }
    }

    private void onDelete() {
        Insumo selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        try {
            InsumoDAO.delete(selected.getId());
            loadData();
        } catch (Exception ex) {
            showAlert(ex.getMessage());}
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
