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


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Rompecabeza extends Stage {
    private static VBox vBox = new VBox();
    private static Image imagen;
    private static Scene escena;
    private static long TI,TF,TF1;
    private static long Tiempo;

    static int numPiezas = 9;
    static Pieza[][] piezas;

    private static void refresh(){
        TI=System.nanoTime();
        Tiempo = 0;
        piezas = new Pieza[(int) Math.sqrt(numPiezas)][(int) Math.sqrt(numPiezas)];
        vBox.getChildren().clear();
        vBox.getChildren().addAll(crearUI(), createGrid());

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
                int tamano = 480/(int) Math.sqrt(numPiezas);
                WritableImage newImage = new WritableImage(lector, i*tamano,j*tamano,tamano,tamano);

                Pieza pieza;

                if(i == (int) Math.sqrt(numPiezas)-1 && j == (int) Math.sqrt(numPiezas)-1) {
                    pieza = new Pieza(newImage, true,i,j);
                }
                else{
                    pieza = new Pieza(newImage, false,i,j);
                }
                piezas[i][j] = pieza;
            }
        }

        GridPane gridLayout = new GridPane();
        gridLayout.setHgap(1);
        gridLayout.setVgap(1);

        for(int i = 0; i < (int) Math.sqrt(numPiezas); i++){
            for(int j = 0; j < (int) Math.sqrt(numPiezas); j++) {
                gridLayout.add(piezas[i][j],i,j);
            }
        }
        return gridLayout;
    }

    private static Node crearUI(){

        MenuBar menuBar = new MenuBar();

        Menu dificulMenu = new Menu("Dificultad");

        MenuItem facilMit = new MenuItem("Fácil (3x3)");
        facilMit.setOnAction(actionEvent -> { numPiezas = 9; refresh(); });
        MenuItem NormMit = new MenuItem("Normal (4x4)");
        NormMit.setOnAction(actionEvent -> { numPiezas = 16; refresh(); });
        MenuItem DifMit = new MenuItem("Difícil (5x5)");
        DifMit.setOnAction(actionEvent -> { numPiezas = 25; refresh(); });

        dificulMenu.getItems().addAll(facilMit,NormMit,DifMit);

        Menu pistaMenu = new Menu("Pista");

        ImageView ImagenPist = new ImageView(imagen);
        ImagenPist.setPreserveRatio(true);
        ImagenPist.setFitWidth(175);

        MenuItem PistaMit = new MenuItem();

        PistaMit.setGraphic(ImagenPist);

        pistaMenu.getItems().add(PistaMit);

        menuBar.getMenus().addAll(dificulMenu, pistaMenu);

        return menuBar;
    }

    static void checarGanar() {
        int counter = 0;
        for(int i = 0; i < Rompecabeza.piezas.length; i++){
            for(int j = 0; j < Rompecabeza.piezas[i].length; j++){
                if(piezas[i][j].isOriginalBackground()){
                    counter++;
                }
            }
        }
        if(counter == Rompecabeza.numPiezas){
            TF=System.nanoTime();
            TF1=TF-TI;
            Tiempo = Tiempo + TF1;
            Tiempo = (long) (Tiempo*0.000000001);
            VentanaTiempo vt = new VentanaTiempo();
            guardarRecords();
            vt.VentanaTiempo(Tiempo, escena);
            Ganar();
        }
    }

    private static void Ganar(){
        Alert gan = new Alert(Alert.AlertType.INFORMATION);
        gan.setTitle("¡Has Ganado!");
        gan.setHeaderText("¡Felicidades!");
        gan.setContentText("¡Has resuelto este rompecabezas!");
        gan.showAndWait();
        for(Node node : vBox.getChildren()){
            if(node instanceof GridPane) {
                node.setDisable(true);
            }
        }
    }

    private static void guardarRecords()
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("records.txt",true))){
            writer.write("Se tardó "+Tiempo+" segundos\n");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


public Rompecabeza(){
        imagen = new Image(getClass().getResource("/Images/Otto.png").toString());
        refresh();
        escena = new Scene(vBox, 500, 500);
        this.setScene(escena);
        this.setTitle("Rompecabeza");
        this.show();
    }
}


