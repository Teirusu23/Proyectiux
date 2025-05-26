package com.example.demo.vistas;

import com.example.demo.modelos.Conexion;
import com.example.demo.modelos.Database;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoginApp extends Stage {

    private Scene scene;

    public void start() {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new javafx.geometry.Insets(20));

        Label userLabel = new Label("Usuario:");
        TextField userField = new TextField();

        Label passLabel = new Label("Contraseña:");
        PasswordField passField = new PasswordField();

        Button loginBtn = new Button("Iniciar sesión");
        Label status = new Label();

        loginBtn.setOnAction(e -> {
            String user = userField.getText();
            String pass = passField.getText();
            try {
                if (Database.validateAdmin(user, pass)) {
                    new AdminPanel();
                    this.close();
                } else {
                    status.setText("Credenciales inválidas");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passField, 1, 1);
        grid.add(loginBtn, 1, 2);
        grid.add(status, 1, 3);

        scene = new Scene(grid, 600, 230);
        scene.getStylesheets().add(getClass().getResource("/Styles/Login.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        this.setScene(scene);
        this.setTitle("Login Administrador");
        this.show();
    }

}

