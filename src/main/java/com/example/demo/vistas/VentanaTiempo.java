package com.example.demo.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaTiempo extends Stage {

    private static long tiempo;
     static VBox vBox = new VBox();

    static void crearUI() {
        Label MostrarTiempo = new Label("Tiempo: " + tiempo + " segundos");
        vBox.getChildren().addAll(MostrarTiempo);
    }

    public void VentanaTiempo(long tiempo, Scene escena) {
            this.tiempo = tiempo;
            crearUI();
            escena = new Scene(vBox, 100, 100);
            this.setScene(escena);
            this.setTitle("Tiempo intento");
            this.show();

        }

}
