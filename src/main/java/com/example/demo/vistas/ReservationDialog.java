package com.example.demo.vistas;

import com.example.demo.modelos.ClientesDAO;
import com.example.demo.modelos.Database;
import com.example.demo.modelos.Reservation;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class ReservationDialog {

    private Stage dialog;
    private Scene scene;
    private ComboBox<ClientesDAO> clientCombo;
    private ComboBox<Integer> tableCombo;
    private DatePicker datePicker;
    private Spinner<Integer> hourSpinner, minuteSpinner;
    private Spinner<Integer> personsSpinner;
    private TextField notesField;

    public ReservationDialog(Stage owner) {
        dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Reservación");

        // Clientes
        clientCombo = new ComboBox<>();
        ObservableList<ClientesDAO> clientes;
        try {
            clientes = new ClientesDAO().SELECT();
            clientCombo.setItems(clientes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        clientCombo.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(ClientesDAO c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null : c.getNomCte());
            }
        });
        clientCombo.setButtonCell(clientCombo.getCellFactory().call(null));

        // Mesas
        tableCombo = new ComboBox<>();
        try {
            tableCombo.getItems().addAll(Database.getTables());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        datePicker = new DatePicker(LocalDateTime.now().toLocalDate());
        hourSpinner   = new Spinner<>(0, 23, LocalDateTime.now().getHour());
        minuteSpinner = new Spinner<>(0, 59, LocalDateTime.now().getMinute());
        personsSpinner= new Spinner<>(1, 20, 2);
        notesField    = new TextField();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10)); grid.setVgap(8); grid.setHgap(10);
        grid.add(new Label("Cliente:"), 0, 0); grid.add(clientCombo, 1, 0);
        grid.add(new Label("Mesa:"),    0, 1); grid.add(tableCombo,  1, 1);
        grid.add(new Label("Fecha:"),   0, 2); grid.add(datePicker,   1, 2);
        grid.add(new Label("Hora:"),    0, 3);
        HBox timeBox = new HBox(5, hourSpinner, new Label(":"), minuteSpinner);
        grid.add(timeBox,                1, 3);
        grid.add(new Label("Personas:"), 0, 4); grid.add(personsSpinner,1, 4);
        grid.add(new Label("Notas:"),    0, 5); grid.add(notesField,    1, 5);

        Button save = new Button("Guardar");
        save.setOnAction(e -> dialog.close());
        grid.add(save, 1, 6);

        scene = new Scene(grid);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        dialog.setScene(scene);
    }

    public Reservation showDialog(Reservation existing) {
        if (existing != null) {
            // Seleccionar cliente en el ComboBox
            for (ClientesDAO c : clientCombo.getItems()) {
                if (c.getIdCte() == existing.getClienteId()) {
                    clientCombo.getSelectionModel().select(c);
                    break;
                }
            }
            tableCombo.getSelectionModel().select((Integer) existing.getTableId());
            LocalDateTime dt = existing.getDateTime();
            datePicker.setValue(dt.toLocalDate());
            hourSpinner.getValueFactory().setValue(dt.getHour());
            minuteSpinner.getValueFactory().setValue(dt.getMinute());
            personsSpinner.getValueFactory().setValue(existing.getPersons());
            notesField.setText(existing.getNotes());
        } else {
            // selecciona primero por defecto
            Optional.ofNullable(clientCombo.getItems().stream().findFirst())
                    .ifPresent(o -> clientCombo.getSelectionModel().select(o.get()));
            tableCombo.getSelectionModel().selectFirst();
        }

        dialog.showAndWait();

        ClientesDAO c = clientCombo.getValue();
        Integer t   = tableCombo.getValue();
        if (c == null || t == null || datePicker.getValue() == null) {
            return null;
        }
        LocalDateTime dt = LocalDateTime.of(
                datePicker.getValue(),
                java.time.LocalTime.of(
                        hourSpinner.getValue(),
                        minuteSpinner.getValue()
                )
        );
        Reservation r = existing != null ? existing : new Reservation();
        r.setClienteId(c.getIdCte());
        r.setTableId(t);
        r.setDateTime(dt);
        r.setPersons(personsSpinner.getValue());
        r.setNotes(notesField.getText());
        return r;
    }
}
