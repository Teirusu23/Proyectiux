package com.example.demo.vistas;

import com.example.demo.componentes.TicketPDFGenerator;
import com.example.demo.modelos.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class VentasRestaurante extends Stage {
    private TilePane productPane;
    private ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();
    private ListView<String> orderListView;
    private ComboBox<Integer> tableSelector;
    private ComboBox<Employee> employeeSelector;
    private ComboBox<ClientesDAO> clientSelector;

    private Scene scene;

    public void start() {
        // Cargar categorías desde BD
        VBox categoryBox = new VBox(10);
        categoryBox.setPadding(new Insets(10));
        categoryBox.setPrefWidth(150);
        try {
            List<String> categories = Database.getCategories();
            for (String category : categories) {
                Button btn = new Button(category);
                btn.setMaxWidth(Double.MAX_VALUE);
                btn.getStyleClass().add("category-button");
                btn.setOnAction(e -> loadProducts(category));
                categoryBox.getChildren().add(btn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // Panel de productos
        productPane = new TilePane();
        productPane.setPadding(new Insets(10));
        productPane.setHgap(10);
        productPane.setVgap(10);
        productPane.setPrefColumns(3);

        // Selector de mesa
        HBox tableBox = new HBox(5);
        Label tableLabel = new Label("Mesa:");
        tableSelector = new ComboBox<>();
        try {
            tableSelector.getItems().addAll(Database.getTables());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableSelector.getSelectionModel().selectFirst();

        tableBox.getChildren().addAll(tableLabel, tableSelector);
        tableBox.getStyleClass().add("table-box");

        // Selector Empleado
        HBox employeeBox = new HBox(5);
        Label empLabel = new Label("Empleado:");
        employeeSelector = new ComboBox<>();
        try {
            List<Employee> empleados = EmployeeDAO.getAll();
            employeeSelector.getItems().addAll(empleados);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        employeeSelector.getSelectionModel().selectFirst();

        employeeSelector.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Employee e, boolean empty) {
                super.updateItem(e, empty);
                setText(empty || e == null ? null : e.getName());
            }
        });
        employeeSelector.setButtonCell(employeeSelector.getCellFactory().call(null));

        employeeBox.getChildren().addAll(empLabel,employeeSelector);
        employeeBox.getStyleClass().add("employee-box");



        //Selector Cliente
        HBox clientBox = new HBox(5);
        Label clientLabel = new Label("Cliente:");
        clientSelector = new ComboBox<>();
            ObservableList<ClientesDAO> clientes = new ClientesDAO().SELECT();
            clientSelector.setItems(clientes);
            if (!clientes.isEmpty()) {
                clientSelector.getSelectionModel().selectFirst();
            }

            clientSelector.getSelectionModel().selectFirst();

// Mostrar nombre
        clientSelector.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(ClientesDAO c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null : c.getNomCte());
            }
        });
        clientSelector.setButtonCell(clientSelector.getCellFactory().call(null));


        // Botón para agregar nuevo cliente
        Button addClientBtn = new Button("Agregar Cliente");
        addClientBtn.setOnAction(e -> { new ListaClientes();
        });

        clientBox.getChildren().addAll(clientLabel, clientSelector, addClientBtn);
        addClientBtn.getStyleClass().add("add-client-btn");
        clientBox.getStyleClass().add("client-box");

        // Resumen de orden
        VBox orderBox = new VBox(10);
        orderBox.setPadding(new Insets(10));
        orderBox.setPrefWidth(300);
        orderBox.getStyleClass().add("order-box");
        Label orderLabel = new Label("Orden:");
        orderListView = new ListView<>();
        Button saveOrderBtn = new Button("Guardar Orden");
        saveOrderBtn.getStyleClass().add("save-order-button");
        saveOrderBtn.setMaxWidth(Double.MAX_VALUE);
        saveOrderBtn.setOnAction(e -> saveOrder());

        Button adminBtn = new Button("Admin");
        adminBtn.setMaxWidth(Double.MAX_VALUE);
        adminBtn.getStyleClass().add("admin-button");
        adminBtn.setOnAction(e -> {
            LoginApp login = new LoginApp();
            try {
                login.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        orderBox.getChildren().addAll(orderLabel, orderListView, tableBox, employeeBox, clientBox, saveOrderBtn, adminBtn);

        // Layout principal
        BorderPane root = new BorderPane();
        root.setLeft(categoryBox);
        root.setCenter(productPane);
        root.setRight(orderBox);
        scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/Styles/VentasRestau.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);


    }

    private void loadProducts(String category) {
        productPane.getChildren().clear();
        try {
            List<Product> products = Database.getProductsByCategory(category);
            for (Product p : products) {
                Image image = new Image("file:"+ p.getImagePath(),60,60,true,true);
                ImageView imageView = new ImageView(image);
                VBox vbox = new VBox(7);
                vbox.getChildren().addAll(new Label(p.getName()),imageView);
                Button btn = new Button();
                btn.setPrefSize(120, 120);
                btn.getStyleClass().add("product-button");
                btn.setGraphic(vbox);
                btn.setOnAction(e -> addToOrder(p));
                productPane.getChildren().add(btn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void addToOrder(Product product) {
        for (OrderItem item : orderItems) {
            if (item.getProduct().getId() == product.getId()) {
                item.incrementQuantity();
                updateOrderView();
                return;
            }
        }
        orderItems.add(new OrderItem(product, 1));
        updateOrderView();
    }

    private void updateOrderView() {
        ObservableList<String> display = FXCollections.observableArrayList();
        for (OrderItem item : orderItems) {
            display.add(item.getProduct().getName() + " x " + item.getQuantity());
        }
        orderListView.setItems(display);
    }

    private void saveOrder() {
        int tableId = tableSelector.getValue();
        int employeeId = employeeSelector.getValue().getId();
        int clienteId = clientSelector.getValue().getIdCte();
        try {
            int orderId = Database.saveOrder(orderItems, tableId,employeeId,clienteId);
            orderItems.clear();
            updateOrderView();
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            File file = fc.showSaveDialog(this);
            if (file != null) {
                TicketPDFGenerator.generateTicket(orderId, file.getAbsolutePath());
            }

        } catch (Exception e) {
            showAlert(
                    "No se pudo guardar la orden porque: " + e.getMessage());
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }



    public VentasRestaurante()
    {
        start();
        this.setTitle("Sistema Restaurante");
        this.setScene(scene);
        this.show();

    }

    // Clases internas POJO
    public static class Product {
        private int id;
        private String name;
        private String imagePath;
        public Product(int id, String name, String imagePath) {
            this.id = id;
            this.name = name;
            this.imagePath = imagePath;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getImagePath() { return imagePath; }

    }

    public static class OrderItem {
        private final Product product;
        private int quantity;
        public OrderItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
        public void incrementQuantity() { quantity++; }
    }
}


