package com.example.demo.vistas;

import com.example.demo.modelos.Employee;
import com.example.demo.modelos.EmployeeDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;

public class EmployeesManager {
    private Stage stage;
    private Scene scene;
    private TableView<Employee> table;

    public void show() {
        stage = new Stage();
        stage.setTitle("Gestión de Empleados");

        table = new TableView<>();
        TableColumn<Employee,Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(d ->
                new javafx.beans.property.SimpleIntegerProperty(d.getValue().getId()));
        TableColumn<Employee,String> colName = new TableColumn<>("Nombre");
        colName.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getName()));
        TableColumn<Employee,String> colRFC = new TableColumn<>("RFC");
        colRFC.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getRfc()));
        TableColumn<Employee,String> colPhone = new TableColumn<>("Celular");
        colPhone.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getPhone()));
        TableColumn<Employee,String> colPos = new TableColumn<>("Puesto");
        colPos.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getPosition()));

        table.getColumns().addAll(colId,colName,colRFC,colPhone,colPos);

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
        scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/Styles/GenericTablaCrud.css").toExternalForm());
        Font.loadFont(getClass().getResource("/Fonts/MarGal.ttf").toExternalForm(),16);
        stage.setScene(scene);

        loadData();
        stage.show();
    }

    private void loadData() {
        try {
            List<Employee> list = EmployeeDAO.getAll();
            table.getItems().setAll(list);
        } catch (Exception ex) {
            showAlert(ex.getMessage());}
    }
    private void onAdd() {
        EmployeeDialog dlg = new EmployeeDialog(stage);
        Employee e = dlg.showDialog(null);
        if (e!=null) {
            try { EmployeeDAO.saveOrUpdate(e); loadData(); }
            catch (Exception ex) {
                showAlert("No se pudo guardar porque: " + ex.getMessage());}
        }
    }
    private void onEdit() {
        Employee sel = table.getSelectionModel().getSelectedItem();
        if (sel==null) return;
        EmployeeDialog dlg = new EmployeeDialog(stage);
        Employee e = dlg.showDialog(sel);
        if (e!=null) {
            try { EmployeeDAO.saveOrUpdate(e); loadData(); }
            catch (Exception ex) {
                showAlert("No se pudo guardar porque: " + ex.getMessage());}

        }
    }
    private void onDelete() {
        Employee sel = table.getSelectionModel().getSelectedItem();
        if (sel==null) return;
        try { EmployeeDAO.delete(sel.getId()); loadData(); }
        catch (Exception ex) {
            showAlert(ex.getMessage());}
    }


    private void showAlert(String msg) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
    }
}
