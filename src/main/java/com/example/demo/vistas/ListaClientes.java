package com.example.demo.vistas;

import com.example.demo.componentes.ButtonCell;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.example.demo.modelos.ClientesDAO;
import javafx.util.Callback;

public class ListaClientes extends Stage {

    private ToolBar tblMenu;
    private TableView<ClientesDAO> tbvClientes;
    private VBox vBox;
    private Scene escena;
    private Button btAgregar;

    public ListaClientes(){
        CrearUI();
        this.setTitle("Listado de Clientes ewe");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){
        tbvClientes = new TableView<>();
        btAgregar = new Button();
        btAgregar.setOnAction(actionEvent -> new Cliente(tbvClientes,null));
        ImageView imv = new ImageView(getClass().getResource("/Images/Icon.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btAgregar.setGraphic(imv);
        tblMenu = new ToolBar(btAgregar);
        CreateTable();
        vBox = new VBox(tblMenu,tbvClientes);
        escena = new Scene(vBox,800,600);
        escena.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
    }

    private void CreateTable(){
        ClientesDAO objC = new ClientesDAO();
        TableColumn<ClientesDAO,String> tbcNomCte = new TableColumn<>("Nombre");
        tbcNomCte.setCellValueFactory(new PropertyValueFactory<>("nomCte")); //callback, accion que se maneja automaticamente, despues de haber realizado otra accion
        TableColumn<ClientesDAO,String> tbcDireccion = new TableColumn<>("Direccion");
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        TableColumn<ClientesDAO,String> tbcTel = new TableColumn<>("Telefono");
        tbcTel.setCellValueFactory(new PropertyValueFactory<>("telCte"));
        TableColumn<ClientesDAO,String> tbcEmail = new TableColumn<>("Email");
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("emailCte"));

        TableColumn<ClientesDAO,String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<ClientesDAO, String>, TableCell<ClientesDAO, String>>() {
            @Override
            public TableCell<ClientesDAO, String> call(TableColumn<ClientesDAO, String> clientesDAOStringTableColumn) {
                return new ButtonCell("Editar");
            }
        });
        TableColumn<ClientesDAO,String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ClientesDAO, String>, TableCell<ClientesDAO, String>>() {
            @Override
            public TableCell<ClientesDAO, String> call(TableColumn<ClientesDAO, String> clientesDAOStringTableColumn) {
                return new ButtonCell("Eliminar");
            }
        });

        tbvClientes.getColumns().addAll(tbcNomCte,tbcDireccion,tbcTel,tbcEmail,tbcEditar,tbcEliminar);
        tbvClientes.setItems(objC.SELECT());
    }
}
