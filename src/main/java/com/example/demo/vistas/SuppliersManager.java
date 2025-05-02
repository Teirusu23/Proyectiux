package com.example.demo.vistas;

import com.example.demo.modelos.Supplier;
import com.example.demo.modelos.SupplierDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class SuppliersManager {
    private Stage stage;
    private Scene scene;
    private TableView<Supplier> table;

    public void show() {
        stage = new Stage();
        stage.setTitle("Gestión de Proveedores");

        table = new TableView<>();
        TableColumn<Supplier, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(d ->
                new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getId()));

        TableColumn<Supplier, String> colName = new TableColumn<>("Nombre");
        colName.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getName()));

        TableColumn<Supplier, String> colContact = new TableColumn<>("Contacto");
        colContact.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getContact()));

        TableColumn<Supplier, String> colPhone = new TableColumn<>("Teléfono");
        colPhone.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getPhone()));

        table.getColumns().addAll(colId, colName, colContact, colPhone);

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
            List<Supplier> list = SupplierDAO.getAll();
            table.getItems().setAll(list);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void onAdd() {
        SupplierDialog dlg = new SupplierDialog(stage);
        Supplier s = dlg.showDialog(null);
        if (s != null) {
            try {
                SupplierDAO.saveOrUpdate(s);
                loadData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void onEdit() {
        Supplier selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        SupplierDialog dlg = new SupplierDialog(stage);
        Supplier s = dlg.showDialog(selected);
        if (s != null) {
            try {
                SupplierDAO.saveOrUpdate(s);
                loadData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void onDelete() {
        Supplier selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        try {
            SupplierDAO.delete(selected.getId());
            loadData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
