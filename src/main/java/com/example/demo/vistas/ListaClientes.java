package com.example.demo.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.modelos.ClientesDAO;

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
        btAgregar = new Button();
        ImageView imv = new ImageView(getClass().getResource("/Images/Icon.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btAgregar.setGraphic(imv);
        tblMenu = new ToolBar(btAgregar);
        tbvClientes = new TableView<>();
        CreateTable();
        vBox = new VBox(tblMenu,tbvClientes);
        escena = new Scene(vBox,800,600);
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
        tbvClientes.getColumns().addAll(tbcNomCte,tbcDireccion,tbcTel,tbcEmail);
        tbvClientes.setItems(objC.SELECT());
    }
}
