package com.example.demo.vistas;

import com.example.demo.modelos.Category;
import com.example.demo.modelos.CategoryDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;

public class CategoriesManager {
    private Stage stage;
    private Scene scene;
    private TableView<Category> table;

    public void show() {
        stage = new Stage();
        stage.setTitle("Gestión de Categorías");

        table = new TableView<>();
        TableColumn<Category,Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(d ->
                new SimpleObjectProperty<>(d.getValue().getId()));
        TableColumn<Category,String> colName = new TableColumn<>("Nombre");
        colName.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getName()));
        TableColumn<Category,String> colDesc = new TableColumn<>("Descripción");
        colDesc.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getDescription()));

        table.getColumns().addAll(colId,colName,colDesc);

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
        scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        stage.setScene(scene);

        loadData();
        stage.show();
    }

    private void loadData() {
        try {
            List<Category> list = CategoryDAO.getAll();
            table.getItems().setAll(list);
        } catch (SQLException e) { e.printStackTrace(); }
    }
    private void onAdd() {
        CategoryDialog dlg = new CategoryDialog(stage);
        Category c = dlg.showDialog(null);
        if (c!=null) {
            try { CategoryDAO.saveOrUpdate(c); loadData(); }
            catch(SQLException ex){ ex.printStackTrace(); }
        }
    }
    private void onEdit() {
        Category sel = table.getSelectionModel().getSelectedItem();
        if (sel==null) return;
        CategoryDialog dlg = new CategoryDialog(stage);
        Category c = dlg.showDialog(sel);
        if (c!=null) {
            try { CategoryDAO.saveOrUpdate(c); loadData(); }
            catch(SQLException ex){ ex.printStackTrace(); }
        }
    }
    private void onDelete() {
        Category sel = table.getSelectionModel().getSelectedItem();
        if (sel==null) return;
        try { CategoryDAO.delete(sel.getId()); loadData(); }
        catch(SQLException ex){ ex.printStackTrace(); }
    }
}
