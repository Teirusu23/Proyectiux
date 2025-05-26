package com.example.demo.vistas;

import com.example.demo.modelos.Product;
import com.example.demo.modelos.ProductDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ProductsManager {

    private Stage stage;
    private Scene scene;
    private TableView<Product> table;

    public void show() {
        stage = new Stage();
        stage.setTitle("Gestión de Productos");

        table = new TableView<>();
        TableColumn<Product, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()));

        TableColumn<Product, String> colName = new TableColumn<>("Nombre");
        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Product, String> colCat = new TableColumn<>("Categoría");
        colCat.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCategory()));

        TableColumn<Product, String> colPath = new TableColumn<>("Ruta Imagen");
        colPath.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getImagePath()));

        TableColumn<Product, ImageView> colImg = new TableColumn<>("Imagen");
        colImg.setCellValueFactory(data -> {
            String path = data.getValue().getImagePath();
            if (path != null && !path.isEmpty()) {
                try {
                    ImageView iv = new ImageView(new Image(new FileInputStream(path)));
                    iv.setFitWidth(50);
                    iv.setFitHeight(50);
                    return new javafx.beans.property.SimpleObjectProperty<>(iv);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return new javafx.beans.property.SimpleObjectProperty<>(null);
        });

        TableColumn<Product, BigDecimal> colPrice = new TableColumn<>("Precio");
        colPrice.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty(data.getValue().getPrice()));

        table.getColumns().addAll(colId, colName, colCat, colPath, colImg, colPrice);

        Button addBtn = new Button("Agregar");
        addBtn.setOnAction(e -> onAdd());

        Button editBtn = new Button("Editar");
        editBtn.setOnAction(e -> onEdit());

        Button delBtn = new Button("Eliminar");
        delBtn.setOnAction(e -> onDelete());

        Button insumosBtn = new Button("Insumos");
        insumosBtn.setOnAction(e -> {
            Product selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                new ProductInsumosManager(selected.getId()).show();
            }
        });

        HBox buttons = new HBox(10, addBtn, editBtn, delBtn,insumosBtn);
        buttons.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(buttons);
        root.setCenter(table);
        root.setPadding(new Insets(10));

        scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        stage.setScene(scene);
        loadData();
        stage.show();
    }

    private void loadData() {
        try {
            List<Product> list = ProductDAO.getAll();
            table.getItems().setAll(list);
        } catch (Exception ex) {
            showAlert(ex.getMessage());}
    }

    private void onAdd() {
        ProductDialog dlg = new ProductDialog(stage);
        Product p = dlg.showDialog(null);
        if (p != null) {
            try {
                ProductDAO.saveOrUpdate(p);
                loadData();
            } catch (Exception ex) {
                showAlert("No se pudo guardar porque: " + ex.getMessage());}
        }
    }

    private void onEdit() {
        Product selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        ProductDialog dlg = new ProductDialog(stage);
        Product p = dlg.showDialog(selected);
        if (p != null) {
            try {
                ProductDAO.saveOrUpdate(p);
                loadData();
            } catch (Exception ex) {
                showAlert("No se pudo guardar porque: " + ex.getMessage());}
        }
    }
    private void onDelete() {
        Product selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        try {
            ProductDAO.delete(selected.getId());
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
