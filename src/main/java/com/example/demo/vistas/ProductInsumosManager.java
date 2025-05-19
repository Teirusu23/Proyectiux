package com.example.demo.vistas;

import com.example.demo.modelos.Insumo;
import com.example.demo.modelos.InsumoDAO;
import com.example.demo.modelos.ProductInsumo;
import com.example.demo.modelos.ProductInsumoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class ProductInsumosManager extends Stage {

    private TableView<ProductInsumo> table;
    private ObservableList<ProductInsumo> insumosList;
    private int productId;
    private Scene scene;

    public ProductInsumosManager(int productId) {
        this.productId = productId;
        setTitle("Insumos del Producto #" + productId);
        initModality(Modality.APPLICATION_MODAL);
        setMinWidth(500);
        setMinHeight(400);

        table = new TableView<>();
        insumosList = FXCollections.observableArrayList();

        // Configurar columnas
        TableColumn<ProductInsumo, String> colInsumo = new TableColumn<>("Insumo");
        colInsumo.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(param.getValue().getInsumoName()));
        colInsumo.setPrefWidth(250);

        TableColumn<ProductInsumo, Double> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(param -> new javafx.beans.property.SimpleObjectProperty<>(param.getValue().getQuantity()));
        colCantidad.setPrefWidth(100);

        table.getColumns().addAll(colInsumo, colCantidad);
        table.setItems(insumosList);

        // Botones
        Button addBtn = new Button("Agregar");
        Button editBtn = new Button("Editar");
        Button deleteBtn = new Button("Eliminar");

        addBtn.setOnAction(e -> openDialog(null));
        editBtn.setOnAction(e -> {
            ProductInsumo selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openDialog(selected);
            } else {
                showAlert("Seleccione un insumo para editar.");
            }
        });
        deleteBtn.setOnAction(e -> {
            ProductInsumo selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    ProductInsumoDAO.delete(productId, selected.getInsumoId());
                    loadInsumos();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    showAlert("Error al eliminar insumo.");
                }
            } else {
                showAlert("Seleccione un insumo para eliminar.");
            }
        });

        HBox buttons = new HBox(10, addBtn, editBtn, deleteBtn);
        buttons.setPadding(new Insets(10));
        buttons.setStyle("-fx-alignment: center;");

        VBox root = new VBox(10, table, buttons);
        root.setPadding(new Insets(10));

        loadInsumos();

        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        this.setScene(scene);
    }

    private void loadInsumos() {
        try {
            List<ProductInsumo> lista = ProductInsumoDAO.getByProduct(productId);
            insumosList.setAll(lista);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error al cargar insumos.");
        }
    }

    private void openDialog(ProductInsumo productInsumo) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(productInsumo == null ? "Agregar Insumo" : "Editar Insumo");

        ComboBox<Insumo> insumoBox = new ComboBox<>();
        Spinner<Double> cantidadSpinner = new Spinner<>(0.0, 10000.0, 1.0, 0.1);
        cantidadSpinner.setEditable(true);

        try {
            List<Insumo> insumosDisponibles = InsumoDAO.getAll();
            insumoBox.setItems(FXCollections.observableArrayList(insumosDisponibles));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (productInsumo != null) {
            // Buscar insumo por nombre
            for (Insumo i : insumoBox.getItems()) {
                if (i.getName().equals(productInsumo.getInsumoName())) {
                    insumoBox.getSelectionModel().select(i);
                    break;
                }
            }
            cantidadSpinner.getValueFactory().setValue(productInsumo.getQuantity());
        }

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.setPadding(new Insets(10));
        form.add(new Label("Insumo:"), 0, 0);
        form.add(insumoBox, 1, 0);
        form.add(new Label("Cantidad:"), 0, 1);
        form.add(cantidadSpinner, 1, 1);

        Button saveBtn = new Button("Guardar");
        saveBtn.setOnAction(e -> {
            if (insumoBox.getValue() == null) {
                showAlert("Seleccione un insumo.");
                return;
            }
            double cantidad = cantidadSpinner.getValue();
            if (cantidad <= 0) {
                showAlert("Cantidad debe ser mayor a 0.");
                return;
            }
            try {
                ProductInsumo pi = new ProductInsumo();
                pi.setProductId(productId);
                pi.setInsumoId(insumoBox.getValue().getId());
                pi.setInsumoName(insumoBox.getValue().getName());
                pi.setQuantity(cantidad);

                ProductInsumoDAO.saveOrUpdate(pi);
                loadInsumos();
                dialog.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error al guardar insumo.");
            }
        });

        VBox dialogVBox = new VBox(10, form, saveBtn);
        dialogVBox.setPadding(new Insets(10));
        scene = new Scene(dialogVBox);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }
}
