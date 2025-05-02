module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    exports com.example.demo;
    requires mysql.connector.j;
    requires java.sql;
    requires kernel;
    requires layout;
    opens com.example.demo.modelos;

}