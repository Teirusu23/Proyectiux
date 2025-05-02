package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Pieza extends Pane {

    private Background originalBackground;
    private Boolean isBlank = false;
    private int x, y;

    Pieza(Image image, Boolean isBlank, int x, int y){

        this.x = x;
        this.y = y;
        this.setMinHeight(480/(int) Math.sqrt(Rompecabeza.numPiezas));
        this.setMinWidth(480/(int) Math.sqrt(Rompecabeza.numPiezas));
        this.isBlank = isBlank;

        if(!isBlank){
            BackgroundImage backImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            originalBackground = new Background(backImage);
            this.setBackground(new Background(backImage));
        }
        else{
            BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE,
                    CornerRadii.EMPTY, Insets.EMPTY);
            originalBackground = new Background(backgroundFill);
        }

        this.setOnMouseClicked(event -> clicked(false));
    }

    void clicked(Boolean ai){
        if(!isBlank) {
            int[] neighboursLocs = new int[] { -1, 0, 0, -1, 0, 1, 1, 0};

            ArrayList<Pieza> neighbours = new ArrayList<Pieza>();

            for (int i = 0; i < neighboursLocs.length; i++) {
                int dx = neighboursLocs[i];
                int dy = neighboursLocs[++i];

                int newX = x + dx;
                int newY = y + dy;

                if (newX >= 0 && newX < (int) Math.sqrt(Rompecabeza.numPiezas) && newY >= 0 && newY < (int) Math.sqrt(Rompecabeza.numPiezas)) {
                    neighbours.add(Rompecabeza.piezas[newX][newY]);
                }
            }

            for(Pieza tile : neighbours){
                if(tile.isBlank){
                    Background temp = this.getBackground();
                    tile.isBlank = false;
                    tile.setBackground(temp);
                    BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
                    Background background = new Background(backgroundFill);
                    this.setBackground(background);
                    this.isBlank = true;
                }
            }
        }
        if(!ai) {
            Rompecabeza.checarGanar();
        }
    }

    @Override
    public Node getStyleableNode() {
        return null;
    }

    public boolean isOriginalBackground() {
        return originalBackground.equals(this.getBackground());
    }
}
