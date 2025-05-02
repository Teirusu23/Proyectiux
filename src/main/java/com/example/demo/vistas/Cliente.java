package com.example.demo.vistas;

import com.example.demo.modelos.ClientesDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Cliente extends Stage {
    private Button btnGuardar;
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
        txtNombre = new TextField();
        txtDireccion = new TextField();
        txtTelCte = new TextField();
        txtEmail = new TextField();
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
        vBox = new VBox(txtNombre,txtDireccion,txtTelCte,txtEmail,btnGuardar);
        escena = new Scene(vBox,120,150);
        escena.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
    }
}
