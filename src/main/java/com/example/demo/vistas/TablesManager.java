package com.example.demo.vistas;

import com.example.demo.modelos.Table;
import com.example.demo.modelos.TableDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class TablesManager {
    private Stage stage;
    private Scene scene;
    private TableView<Table> tableView;

    public void show() {
        stage = new Stage();
        stage.setTitle("Gestión de Mesas");

        tableView = new TableView<>();
        TableColumn<Table, Integer> colId = new TableColumn<>("ID Mesa");
        colId.setCellValueFactory(d ->
                new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getId()));
        tableView.getColumns().add(colId);

        Button add = new Button("Agregar");
        add.setOnAction(e -> onAdd());
        Button del = new Button("Eliminar");
        del.setOnAction(e -> onDelete());

        HBox controls = new HBox(10, add, del);
        controls.setPadding(new Insets(10));

        VBox root = new VBox(10, controls, tableView);
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
            List<Table> list = TableDAO.getAll();
            tableView.getItems().setAll(list);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private void onAdd() {
        TableDialog dlg = new TableDialog(stage);
        Table t = dlg.showDialog(null);
        if (t != null) {
            try { TableDAO.saveOrUpdate(t); loadData(); }
            catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    private void onDelete() {
        Table sel = tableView.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        try { TableDAO.delete(sel.getId()); loadData(); }
        catch (SQLException ex) { ex.printStackTrace(); }
    }
}
