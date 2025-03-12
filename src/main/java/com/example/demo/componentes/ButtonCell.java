package com.example.demo.componentes;

import com.example.demo.modelos.ClientesDAO;
import com.example.demo.vistas.Cliente;
import javafx.scene.control.*;

import java.util.Optional;

public class ButtonCell extends TableCell<ClientesDAO,String> {
    Button btnCelda;
    private String strLabelBtn;

    public ButtonCell(String label){
        strLabelBtn = label;
        btnCelda = new Button(strLabelBtn);
        btnCelda.setOnAction(actionEvent -> {
            ClientesDAO objC = this.getTableView().getItems().get(this.getIndex());
            if (strLabelBtn.equals("Editar")){
                new Cliente(this.getTableView(),objC);
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje Sistema :3");
                alert.setContentText("¿Desea eliminar el registro seleccionado?");
                Optional<ButtonType> opcion = alert.showAndWait();
                if(opcion.get() == ButtonType.OK) {
                    objC.DELETE();
                }
            }
            this.getTableView().setItems(objC.SELECT());
            this.getTableView().refresh();
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if( !empty)
            this.setGraphic(btnCelda);
    }
}
