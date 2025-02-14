module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    exports com.example.demo;
}