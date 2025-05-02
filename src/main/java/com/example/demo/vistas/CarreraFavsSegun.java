package com.example.demo.vistas;

import com.example.demo.componentes.Hilo;
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
    private String[] strPer = {"Gumshoe","Tails","Adagio","Georgia","Bayonetta","Rouge","Franziska"};
    private Hilo[] thrPerso;

    public CarreraFavsSegun(){
        CrearUI();
        this.setTitle("Carreriux");
        this.setScene(escena);
        this.show();

    }

    private void CrearUI()
    {
        btnInicio = new Button("Iniciar");
        pgbPers = new ProgressBar[7];
        arLblPer = new Label[7];
        thrPerso = new Hilo[7];
        gdpPersonajes = new GridPane();
        for (int i = 0; i < pgbPers.length; i++) {
            arLblPer[i]=new Label(strPer[i]);
            pgbPers[i] = new ProgressBar(0);
            thrPerso[i] = new Hilo(strPer[i],pgbPers[i]);
            gdpPersonajes.add(arLblPer[i],0,i);
            gdpPersonajes.add(pgbPers[i],1,i);

        }
        vBox = new VBox(gdpPersonajes,btnInicio);
        escena = new Scene(vBox,300,200);
    }
}
