package com.example.demo.vistas;

import com.example.demo.modelos.Employee;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.*;

public class EmployeeDialog {
    private Stage dialog;
    private Scene scene;
    private TextField nameField, rfcField, phoneField, posField;

    public EmployeeDialog(Stage owner) {
        dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Empleado");

        nameField = new TextField();
        rfcField  = new TextField();
        phoneField= new TextField();
        posField  = new TextField();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10)); grid.setVgap(8); grid.setHgap(10);
        grid.add(new Label("Nombre:"),0,0); grid.add(nameField,1,0);
        grid.add(new Label("RFC:"),0,1);    grid.add(rfcField,1,1);
        grid.add(new Label("Celular:"),0,2);grid.add(phoneField,1,2);
        grid.add(new Label("Puesto:"),0,3); grid.add(posField,1,3);

        Button save = new Button("Guardar");
        save.setOnAction(e -> dialog.close());
        grid.add(save,1,4);

        scene = new Scene(grid);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        dialog.setScene(scene);
    }

    public Employee showDialog(Employee existing) {
        if (existing!=null) {
            nameField.setText(existing.getName());
            rfcField.setText(existing.getRfc());
            phoneField.setText(existing.getPhone());
            posField.setText(existing.getPosition());
        }
        dialog.showAndWait();
        if (nameField.getText().isEmpty() || rfcField.getText().isEmpty()) return null;
        Employee e = existing!=null? existing : new Employee();
        e.setName(nameField.getText());
        e.setRfc(rfcField.getText());
        e.setPhone(phoneField.getText());
        e.setPosition(posField.getText());
        return e;
    }
}
