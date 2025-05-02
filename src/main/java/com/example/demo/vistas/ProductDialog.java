package com.example.demo.vistas;

import com.example.demo.modelos.Database;
import com.example.demo.modelos.Product;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProductDialog {

    private final Stage dialog;
    private final Scene scene;
    private final TextField nameField;
    private final ComboBox<String> categoryCombo;
    private final Label imageLabel;
    private String imagePath;

    public ProductDialog(Stage owner) {
        dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Producto");

        nameField = new TextField();
        categoryCombo = new ComboBox<>();
        categoryCombo.getItems().addAll(loadCategories());

        Button chooseBtn = new Button("Seleccionar Imagen");
        imageLabel = new Label("Ninguna");
        chooseBtn.setOnAction(e -> selectImage());

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Categoría:"), 0, 1);
        grid.add(categoryCombo, 1, 1);
        grid.add(chooseBtn, 0, 2);
        grid.add(imageLabel, 1, 2);

        Button saveBtn = new Button("Guardar");
        saveBtn.setOnAction(e -> onSave());
        grid.add(saveBtn, 1, 3);

        scene = new Scene(grid);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        dialog.setScene(scene);
    }

    public Product showDialog(Product existing) {
        if (existing != null) {
            nameField.setText(existing.getName());
            categoryCombo.getSelectionModel().select(existing.getCategory());
            imagePath = existing.getImagePath();
            imageLabel.setText(imagePath != null ? imagePath : "Ninguna");
        }
        dialog.showAndWait();
        if (nameField.getText().isEmpty() || categoryCombo.getValue() == null) {
            return null;
        }
        Product p = existing != null ? existing : new Product();
        p.setName(nameField.getText());
        p.setCategory(categoryCombo.getValue());
        p.setImagePath(imagePath);
        return p;
    }

    private void selectImage() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png","*.jpg","*.jpeg")
        );
        File img = fc.showOpenDialog(dialog);
        if (img != null) {
            try {
                Path dest = Paths.get("images", img.getName());
                Files.createDirectories(dest.getParent());
                Files.copy(img.toPath(), dest, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                imagePath = dest.toString();
                imageLabel.setText(img.getName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void onSave() {
        dialog.close();
    }

    private java.util.List<String> loadCategories() {
        try {
            return com.example.demo.modelos.Database.getCategories();
        } catch (Exception e) {
            return java.util.Collections.emptyList();
        }
    }
}
