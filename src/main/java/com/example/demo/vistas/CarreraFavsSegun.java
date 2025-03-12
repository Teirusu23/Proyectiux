package com.example.demo.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CarreraFavsSegun extends Stage {

    private VBox vBox;
    private GridPane gdpPersonajes;
    private Button btnInicio;
    private Scene escena;
    private Label[] arLblPer;
    private ProgressBar[] pgbPers;

    public CarreraFavsSegun(){
        this.setTitle("Carreriux");
        this.setScene(escena);
        this.show();

    }
}
