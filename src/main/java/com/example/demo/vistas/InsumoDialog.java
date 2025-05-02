package com.example.demo.vistas;

import com.example.demo.modelos.Insumo;
import com.example.demo.modelos.Supplier;
import com.example.demo.modelos.SupplierDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class InsumoDialog {

    private final Stage dialog;
    private final Scene scene;
    private final TextField nameField;
    private final ComboBox<Supplier> supplierCombo;
    private final TextField unitField;
    private Insumo insumo;  // referncia para editar

    public InsumoDialog(Stage owner) {
        dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Insumo");

        nameField     = new TextField();
        unitField     = new TextField();
        supplierCombo = new ComboBox<>();

        // Cargar proveedores
        try {
            List<Supplier> suppliers = SupplierDAO.getAll();
            supplierCombo.getItems().addAll(suppliers);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        supplierCombo.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Supplier s, boolean empty) {
                super.updateItem(s, empty);
                setText(empty || s == null ? null : s.getName());
            }
        });
        supplierCombo.setButtonCell(supplierCombo.getCellFactory().call(null));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        grid.add(new Label("Nombre:"),   0, 0);
        grid.add(nameField,               1, 0);
        grid.add(new Label("Unidad:"),   0, 1);
        grid.add(unitField,               1, 1);
        grid.add(new Label("Proveedor:"),0, 2);
        grid.add(supplierCombo,           1, 2);

        Button save = new Button("Guardar");
        save.setOnAction(e -> {
            // Validaciones mínimas
            if (nameField.getText().isEmpty() || unitField.getText().isEmpty() ||  supplierCombo.getValue() == null) {
                return;
            }
            if (insumo == null) insumo = new Insumo();
            insumo.setName(nameField.getText());
            insumo.setUnit(unitField.getText());
            insumo.setSupplierId(supplierCombo.getValue().getId());
            dialog.close();
        });
        grid.add(save, 1, 3);

        scene = new Scene(grid);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        dialog.setScene(scene);
    }

    /** Muestra el diálogo; si existe, precarga datos. */
    public Insumo showDialog(Insumo existing) {
        insumo = existing;
        if (existing != null) {
            nameField.setText(existing.getName());
            unitField.setText(existing.getUnit());
            // seleccionar proveedor correspondiente
            for (Supplier s : supplierCombo.getItems()) {
                if (s.getId() == existing.getSupplierId()) {
                    supplierCombo.getSelectionModel().select(s);
                    break;
                }
            }
        } else {
            supplierCombo.getSelectionModel().selectFirst();
        }
        dialog.showAndWait();
        return insumo;
    }
}
