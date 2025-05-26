package com.example.demo.vistas;

import com.example.demo.modelos.ClientesDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Cliente extends Stage {
    private Button btnGuardar;
    private Label lblNombre, lblDireccion, lblTelefono, lblEmail;
    private TextField txtNombre, txtDireccion, txtTelCte, txtEmail;
    private VBox vBox;
    private Scene escena;
    private ClientesDAO objC;
    private TableView<ClientesDAO> tbvClientes;

    public Cliente(TableView<ClientesDAO> tbvCte,ClientesDAO obj){
        this.tbvClientes = tbvCte;
        CrearUI();
        if(obj == null){
            objC = new ClientesDAO();
        }else{
            objC = obj;
            txtNombre.setText(objC.getNomCte());
            txtDireccion.setText(objC.getDireccion());
            txtEmail.setText(objC.getEmailCte());
            txtTelCte.setText(objC.getTelCte());

        }
        this.setTitle("Registrar Cliente");
        this.setScene(escena);
        this.show();

    }

    private void CrearUI(){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);
        lblNombre = new Label("Nombre");
        txtNombre = new TextField();
        lblDireccion = new Label("Direccion");
        txtDireccion = new TextField();
        lblTelefono = new Label("Telefono");
        txtTelCte = new TextField();
        lblEmail = new Label("Email");
        txtEmail = new TextField();
        grid.add(lblNombre, 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(lblDireccion, 0, 1);
        grid.add(txtDireccion, 1, 1);
        grid.add(lblTelefono, 0, 2);
        grid.add(txtTelCte, 1, 2);
        grid.add(lblEmail, 0, 3);
        grid.add(txtEmail, 1, 3);
        btnGuardar =new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> {
            objC.setNomCte(txtNombre.getText());
            objC.setDireccion(txtDireccion.getText());
            objC.setTelCte(txtTelCte.getText());
            objC.setEmailCte(txtEmail.getText());
            if(objC.getIdCte()>0)
                objC.UPDATE();
            else
                objC.INSERT();
            tbvClientes.setItems(objC.SELECT());
            tbvClientes.refresh();
            this.close();
        });
        vBox = new VBox(grid,btnGuardar);
        escena = new Scene(vBox,250,160);
        escena.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
    }
}
