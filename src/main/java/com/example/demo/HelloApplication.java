package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import vistas.Calculadora;

import java.io.IOException;

public class HelloApplication extends Application {
    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menComp1, menComp2;
    private MenuItem mitCalculadora;
    private Scene escena;

    void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(event -> new Calculadora());
        menComp1= new Menu("Competencia 1");
        menComp1.getItems().addAll(mitCalculadora);
        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(menComp1);
        vBox = new VBox(mnbPrincipal);
        escena = new Scene(vBox);
        escena.getStylesheets().add(getClass().getResource("/Styles/main.css").toString());

    }

    @Override
    public void start(Stage stage) throws IOException{
        CrearUI();
        stage.setTitle("Te amo Gumshoe");
        stage.setScene(escena);
        stage.show();
        stage.setMaximized(true);
    }
    public static void main(String[] args) {
        launch();
    }


}