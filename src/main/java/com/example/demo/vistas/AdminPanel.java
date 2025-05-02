package com.example.demo.vistas;

import com.example.demo.modelos.Product;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class AdminPanel extends Stage {

    private Scene scene;

    public void CrearIU() {
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);

        // Título
        Label title = new Label("Panel de Administrador");
        title.getStyleClass().add("alert-info");
        title.getStyleClass().add("Titulo");

        // Botones de acción
        Button productsBtn = new Button("Productos");
        productsBtn.setOnAction(e -> new ProductsManager().show());
        productsBtn.getStyleClass().add("btn-success");

        Button reportsBtn = new Button("Reportes");
        reportsBtn.setOnAction(e -> {
            ReportsManager rm = new ReportsManager();
            rm.show();
        });
        reportsBtn.getStyleClass().add("btn-warning");

        Button categoriesBtn = new Button("Categorías");
        categoriesBtn.setOnAction(e -> new CategoriesManager().show());
        categoriesBtn.getStyleClass().add("btn-success");

        Button employeesBtn = new Button("Empleados");
        employeesBtn.setOnAction(e -> new EmployeesManager().show());
        employeesBtn.getStyleClass().add("btn-warning");

        Button tablesBtn = new Button("Mesas");
        tablesBtn.setOnAction(e -> new TablesManager().show());
        tablesBtn.getStyleClass().add("btn-primary");

        Button reservationsBtn = new Button("Reservaciones");
        reservationsBtn.setOnAction(e -> new ReservationsManager().show());
        reservationsBtn.getStyleClass().add("btn-info");

        Button SuppliersBtn = new Button("Proveedores");
        SuppliersBtn.setOnAction(e -> new SuppliersManager().show());
        SuppliersBtn.getStyleClass().add("btn-primary");

        Button InsumosBtn = new Button("Insumos");
        InsumosBtn.setOnAction(e -> new InsumosManager().show());
        InsumosBtn.getStyleClass().add("btn-default");


        // Separador y logout
        Separator sep = new Separator();
        Button logoutBtn = new Button("Cerrar Sesión");
        logoutBtn.setOnAction(e -> this.close());
        logoutBtn.getStyleClass().add("btn-danger");

        // Layout vertical
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                title,
                productsBtn,
                reportsBtn,
                categoriesBtn,
                employeesBtn,
                tablesBtn,
                reservationsBtn,
                SuppliersBtn,
                InsumosBtn,
                sep,
                logoutBtn
        );

        // Ajuste de crecimiento (opcional)
        VBox.setVgrow(sep, Priority.ALWAYS);

        scene = new Scene(root, 350, 465);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("/Styles/Admin.css").toExternalForm());

    }


    public AdminPanel() {
        CrearIU();
        this.setTitle("Panel de Administrador");
        this.setScene(scene);
        this.show();
    }
}
