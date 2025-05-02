package com.example.demo.vistas;

import com.example.demo.modelos.ClientesDAO;
import com.example.demo.modelos.Reservation;
import com.example.demo.modelos.ReservationDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReservationsManager {

    private Stage stage;
    private Scene scene;
    private TableView<Reservation> table;

    public void show() {
        stage = new Stage();
        stage.setTitle("Gestión de Reservaciones");

        table = new TableView<>();
        TableColumn<Reservation,Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(d ->
                new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getId()));

        TableColumn<Reservation,String> colClient = new TableColumn<>("Cliente");
        colClient.setCellValueFactory(d -> {
            int cid = d.getValue().getClienteId();
            for (ClientesDAO c : new ClientesDAO().SELECT()) {
                if (c.getIdCte() == cid) return new javafx.beans.property.SimpleStringProperty(c.getNomCte());
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });

        TableColumn<Reservation,Integer> colTable = new TableColumn<>("Mesa");
        colTable.setCellValueFactory(d ->
                new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getTableId()));

        TableColumn<Reservation,String> colDate = new TableColumn<>("Fecha y Hora");
        colDate.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(
                        d.getValue().getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
        );

        TableColumn<Reservation,Integer> colPersons = new TableColumn<>("Personas");
        colPersons.setCellValueFactory(d ->
                new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getPersons()));

        TableColumn<Reservation,String> colNotes = new TableColumn<>("Notas");
        colNotes.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getNotes()));

        table.getColumns().addAll(colId, colClient, colTable, colDate, colPersons, colNotes);

        Button add = new Button("Agregar");
        add.setOnAction(e -> onAdd());
        Button edit = new Button("Editar");
        edit.setOnAction(e -> onEdit());
        Button del = new Button("Eliminar");
        del.setOnAction(e -> onDelete());

        HBox controls = new HBox(10, add, edit, del);
        controls.setPadding(new Insets(10));

        VBox root = new VBox(10, controls, table);
        root.setPadding(new Insets(10));
        scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        stage.setScene(scene);

        loadData();
        stage.show();
    }

    private void loadData() {
        try {
            List<Reservation> list = ReservationDAO.getAll();
            table.getItems().setAll(list);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void onAdd() {
        ReservationDialog dlg = new ReservationDialog(stage);
        Reservation r = dlg.showDialog(null);
        if (r != null) {
            try {
                ReservationDAO.saveOrUpdate(r);
                loadData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void onEdit() {
        Reservation sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        ReservationDialog dlg = new ReservationDialog(stage);
        Reservation r = dlg.showDialog(sel);
        if (r != null) {
            try {
                ReservationDAO.saveOrUpdate(r);
                loadData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void onDelete() {
        Reservation sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        try {
            ReservationDAO.delete(sel.getId());
            loadData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
