package com.example.demo.vistas;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;

public class Rompecabeza extends Stage {
    private static VBox vBox = new VBox();
    private static Image imagen = new Image("/Images/Rompe.jpeg");
    static Piezas[][] piezas;

    private Scene escena;

    static int numPiezas = 9;

    private static void refresh(){
        piezas = new Piezas[(int) Math.sqrt(numPiezas)][(int) Math.sqrt(numPiezas)];
        vBox.getChildren().clear();
        vBox.getChildren().addAll(crearUI(),createGrid());

        // Mix it up
        Random rand = new Random();
        for(int i = 0; i < 10000; i++){
            int x = rand.nextInt((int) Math.sqrt(numPiezas));
            int y = rand.nextInt((int) Math.sqrt(numPiezas));
            piezas[x][y].clicked(true);
        }
    }

    private static Parent createGrid() {
        for(int i = 0; i < (int) Math.sqrt(numPiezas); i++){
            for(int j = 0; j < (int) Math.sqrt(numPiezas); j++){
                PixelReader lector = imagen.getPixelReader();
                int tamaño = 480/(int) Math.sqrt(numPiezas);
                WritableImage newImage = new WritableImage(lector, i*tamaño,j*tamaño,tamaño,tamaño);

                Piezas pieza;

                if(i == (int) Math.sqrt(numPiezas)-1 && j == (int) Math.sqrt(numPiezas)-1) {
                    pieza = new Piezas(newImage, true,i,j);
                }
                else{
                    pieza = new Piezas(newImage, false,i,j);
                }
                piezas[i][j] = pieza;
            }
        }

        GridPane gpPiezas = new GridPane();
        gpPiezas.setHgap(1);
        gpPiezas.setVgap(1);

        for(int i = 0; i < (int) Math.sqrt(numPiezas); i++){
            for(int j = 0; j < (int) Math.sqrt(numPiezas); j++) {
                gpPiezas.add(piezas[i][j],i,j);
            }
        }
        return gpPiezas;
    }

    private static Node crearUI(){

        MenuBar menuBar = new MenuBar();

        Menu difficultyMenu = new Menu("Dificultad");

        MenuItem facilIte = new MenuItem("Facil (3x3)");
        facilIte.setOnAction(actionEvent -> { numPiezas = 9; refresh(); });
        MenuItem MediaIte = new MenuItem("Media (4x4)");
        MediaIte.setOnAction(actionEvent -> { numPiezas = 16; refresh(); });
        MenuItem DifiIte = new MenuItem("Dificil (5x5)");
        DifiIte.setOnAction(actionEvent -> { numPiezas = 25; refresh(); });

        difficultyMenu.getItems().addAll(facilIte, MediaIte,DifiIte);

        Menu hintMenu = new Menu("Pista");

        ImageView hintImage = new ImageView(imagen);
        hintImage.setPreserveRatio(true);
        hintImage.setFitWidth(175);

        MenuItem hintImageMenuItem = new MenuItem();

        hintImageMenuItem.setGraphic(hintImage);

        hintMenu.getItems().add(hintImageMenuItem);

        menuBar.getMenus().addAll(difficultyMenu, hintMenu);

        return menuBar;

    }

    static void checkGanar() {
        int counter = 0;
        for(int i = 0; i < Rompecabeza.piezas.length; i++){
            for(int j = 0; j < Rompecabeza.piezas[i].length; j++){
                if(piezas[i][j].isOriginalBackground()){
                    counter++;
                }
            }
        }
        if(counter == Rompecabeza.numPiezas){
            Gano();
        }
    }

    private static void Gano(){
        Alert ganeee = new Alert(Alert.AlertType.INFORMATION);
        ganeee.setTitle("Haz Ganado");
        ganeee.setHeaderText("Felicidades!");
        ganeee.setContentText("Haz resueslto este rompecabeza!");
        ganeee.showAndWait();
        for(Node node : vBox.getChildren()){
            if(node instanceof GridPane) {
                node.setDisable(true);
            }
        }
    }

    public Rompecabeza(){
        escena = new Scene(vBox, 500, 500);
        this.setScene(escena);
        this.setTitle("Rompecabeza");
        this.show();
    }
}


