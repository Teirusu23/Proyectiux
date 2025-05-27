package com.example.demo;

//import com.example.demo.componentes.Hilo;
import com.example.demo.vistas.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.modelos.Conexion;

import java.io.IOException;

public class HelloApplication extends Application {
    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menComp1, menComp2;
    private MenuItem mitCalculadora,mitRestaurante,mitRompecabeza,mitHilo;
    private Scene escena;

    void CrearUI(){
        /*
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(event -> new Calculadora());
        mitRestaurante = new MenuItem("Restaurante");
        mitRestaurante.setOnAction(actionEvent -> new VentasRestaurante());
        mitRompecabeza = new MenuItem("Rompecabeza");
        mitRompecabeza.setOnAction(actionEvent -> new Rompecabeza());
        menComp1= new Menu("Competencia 1");
        menComp1.getItems().addAll(mitCalculadora,mitRestaurante,mitRompecabeza);
        mitHilo = new MenuItem("Carrera con Hilos");
        mitHilo.setOnAction(actionEvent -> new CarreraFavsSegun());
        menComp2= new Menu("Competencia 2");
        menComp2.getItems().addAll(mitHilo);
        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(menComp1,menComp2);
        vBox = new VBox(mnbPrincipal);
         */
        VentasRestaurante venta = new VentasRestaurante();
        //escena = new Scene();
        //escena.getStylesheets().add(getClass().getResource("/Styles/main.css").toString());

    }

    @Override
    public void start(Stage stage) throws IOException{
//        new Hilo("Gumshoe").start();
//        new Hilo("Tails").start();
//        new Hilo("Georgia").start();
//        new Hilo("Adagio").start();
//        new Hilo("Bayonetta").start();
//        new Hilo("Rouge").start();
//        new Hilo("Franziska").start();
        Conexion.createConnection();
        CrearUI();
        //stage.setTitle("Te amo Gumshoe");
        //stage.setScene(escena);
        //stage.show();
        //stage.setMaximized(true);
    }
    public static void main(String[] args) {
        launch();
    }


}