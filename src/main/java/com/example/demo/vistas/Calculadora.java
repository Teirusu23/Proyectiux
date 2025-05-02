package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage
{
    private Scene escena;
    private String tipodeCalc = "";
    private TextField txtDisplay;
    private VBox vBox;
    private GridPane gpTeclado;
    private Button[][] arBtTec;
    String strTeclas[] = {"7","8","9","*","4","5","6","/","1","2","3","+","=","0","C","-"};
    private StringBuilder txtVisual = new StringBuilder();
    private double resultado;


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
            for (int k = 0; k < 4; k++) {
                arBtTec[i][k] = new Button(strTeclas[pos]);
                int finalPos = pos;
                if (strTeclas[pos].equals("*") || strTeclas[pos].equals("+") || strTeclas[pos].equals("-") || strTeclas[pos].equals("/")) {
                    arBtTec[i][k].setOnAction(event -> Operador(strTeclas[finalPos]));
                }
                else
                {
                    if(strTeclas[pos].equals("=")) {
                        arBtTec[i][k].setOnAction(event -> Igual());
                    }
                    else{
                        if (strTeclas[pos].equals("C"))
                        {
                            arBtTec[i][k].setOnAction(actionEvent -> Borrar());
                        }
                        else
                        {
                            arBtTec[i][k].setOnAction(event -> Numeros(strTeclas[finalPos]));
                        }
                    }
                }

                arBtTec[i][k].setPrefSize(50,50);
                gpTeclado.add(arBtTec[i][k],k,i);
                pos++;
            }
        }
    }

    private void Borrar() {
        txtVisual.setLength(0);
        txtDisplay.setText("0");
        resultado=0.0;
    }

    private void Igual() {
        if (txtVisual.length() > 0) {
            double operando = Double.parseDouble(txtVisual.toString());
            switch (tipodeCalc) {
                case "+":
                    resultado += operando;
                    break;
                case "-":
                    resultado -= operando;
                    break;
                case "*":
                    resultado *= operando;
                    break;
                case "/":
                    if (operando != 0) {
                        resultado /= operando;
                    } else {
                        txtVisual.setLength(0);
                        txtVisual.append("Error");
                        actualiTxt();
                        return;
                    }
                    break;
            }
            txtVisual.setLength(0);
            txtVisual.append(resultado);
            tipodeCalc = "";
            actualiTxt();
        }
        else {
            txtDisplay.setText("Error");
        }

    }

    private void actualiTxt() {
        String TextMost = txtVisual.length() > 0 ? txtVisual.toString() : Double.toString(resultado);
        TextMost = TextMost.length() > 10 ? TextMost.substring(0, 10) : TextMost;
        txtDisplay.setText(TextMost);
    }

    private void Numeros(String strTecla)
    {
        txtVisual.append(Integer.parseInt(strTecla));
        actualiTxt();
    }

    private void EventoTeclado(String strTecla)
    {
        txtDisplay.appendText(strTecla);
    }

    private void Operador(String strTecla) {
        if (txtVisual.length() > 0) {
            if (!tipodeCalc.isEmpty()) {
                Igual();
            }
            tipodeCalc = strTecla;
            resultado = Double.parseDouble(txtVisual.toString());
            txtVisual.setLength(0);
            EventoTeclado(strTecla);
        }
        else {
            EventoTeclado(strTecla);
        }
    }



//        private void Punto (String strTecla)
//        {
//            txtDisplay.appendText("SMW");
//        }


    public Calculadora()
    {
        CrearUI();
        this.setScene(escena);
        this.setTitle("Calculadora");
        this.show();

    }
}
