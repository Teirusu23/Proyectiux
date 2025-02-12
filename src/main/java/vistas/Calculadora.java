package vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Calculadora extends Stage
{
    private Scene escena;
    private TextField txtDisplay;
    private VBox vBox;
    private GridPane gpTeclado;
    private Button[][] arBtTec;
    String strTeclas[] = {"7","8","9","*","4","5","6","/","1","2","3","+","=","0",".","-"};

    public void CrearUI()
    {
        CrearTeclas();
        txtDisplay = new TextField("0");
        txtDisplay.setEditable(false);
        txtDisplay.setAlignment(Pos.BASELINE_RIGHT);
        vBox = new VBox(txtDisplay,gpTeclado);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        escena = new Scene(vBox,200,200);
        //Font.loadFont(getClass().getResourceAsStream("MGx.ttf"), 10)
        escena.getStylesheets().add(getClass().getResource("/Styles/Calcu.css").toString());
    }

    public void CrearTeclas()
    {
        arBtTec = new Button[4][4];
        gpTeclado = new GridPane();
        gpTeclado.setHgap(5);
        gpTeclado.setVgap(5);
        int pos = 0;
        for (int i = 0; i < 4; i++)
        {
            for (int k = 0; k < 4; k++)
            {
                arBtTec[i][k] = new Button(strTeclas[pos]);
                if(strTeclas[pos].equals("*"))
                    arBtTec[i][k].setId("fontButton");
                int finalPos = pos;
                arBtTec[i][k].setOnAction(event -> EventoTeclado(strTeclas[finalPos]));
                arBtTec[i][k].setPrefSize(50,50);
                gpTeclado.add(arBtTec[i][k],k,i);
                pos++;
            }
        }
    }

    private void EventoTeclado(String strTecla)
    {
        txtDisplay.appendText(strTecla);
    }


    public Calculadora()
    {
        CrearUI();
        this.setScene(escena);
        this.setTitle("Calculadora");
        this.show();

    }
}
