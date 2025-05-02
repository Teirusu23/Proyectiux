package com.example.demo.vistas;

import com.example.demo.modelos.Supplier;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SupplierDialog {
    private final Stage dialog;
    private final Scene scene;
    private final TextField nameField;
    private final TextField contactField;
    private final TextField phoneField;

    public SupplierDialog(Stage owner) {
        dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Proveedor");

        nameField    = new TextField();
        contactField = new TextField();
        phoneField   = new TextField();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Contacto:"), 0, 1);
        grid.add(contactField, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2);
        grid.add(phoneField, 1, 2);

        Button save = new Button("Guardar");
        save.setOnAction(e -> dialog.close());
        grid.add(save, 1, 3);

        scene = new Scene(grid);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        dialog.setScene(scene);
    }

    public Supplier showDialog(Supplier existing) {
        if (existing != null) {
            nameField.setText(existing.getName());
            contactField.setText(existing.getContact());
            phoneField.setText(existing.getPhone());
        }
        dialog.showAndWait();
        if (nameField.getText().isEmpty()) return null;
        Supplier s = existing != null ? existing : new Supplier();
        s.setName(nameField.getText());
        s.setContact(contactField.getText());
        s.setPhone(phoneField.getText());
        return s;
    }
}
