package com.example.demo.vistas;

import com.example.demo.modelos.Table;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.*;

public class TableDialog {
    private final Stage dialog;
    private final Scene scene;
    private final Spinner<Integer> idSpinner;

    public TableDialog(Stage owner) {
        dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Mesa");

        idSpinner = new Spinner<>(1, 100, 1);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10)); grid.setVgap(8); grid.setHgap(10);
        grid.add(new Label("ID Mesa:"), 0, 0);
        grid.add(idSpinner, 1, 0);

        Button save = new Button("Guardar");
        save.setOnAction(e -> dialog.close());
        grid.add(save, 1, 1);

        scene = new Scene(grid);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        dialog.setScene(scene);
    }

    public Table showDialog(Table existing) {
        if (existing != null) {
            idSpinner.getValueFactory().setValue(existing.getId());
        }
        dialog.showAndWait();
        int id = idSpinner.getValue();
        return new Table(id);
    }
}
