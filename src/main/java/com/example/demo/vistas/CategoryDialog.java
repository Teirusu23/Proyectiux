package com.example.demo.vistas;

import com.example.demo.modelos.Category;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.*;

public class CategoryDialog {
    private Stage dialog;
    private Scene scene;
    private TextField nameField;
    private TextField descField;

    public CategoryDialog(Stage owner) {
        dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Categoría");

        nameField = new TextField();
        descField = new TextField();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10)); grid.setVgap(8); grid.setHgap(10);
        grid.add(new Label("Nombre:"),0,0); grid.add(nameField,1,0);
        grid.add(new Label("Descripción:"),0,1); grid.add(descField,1,1);

        Button save = new Button("Guardar");
        save.setOnAction(e -> dialog.close());
        grid.add(save,1,2);

        scene = new Scene(grid);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        dialog.setScene(scene);
    }

    public Category showDialog(Category existing) {
        if (existing != null) {
            nameField.setText(existing.getName());
            descField.setText(existing.getDescription());
        }
        dialog.showAndWait();
        if (nameField.getText().isEmpty()) return null;
        Category c = existing!=null? existing : new Category();
        c.setName(nameField.getText());
        c.setDescription(descField.getText());
        return c;
    }
}
