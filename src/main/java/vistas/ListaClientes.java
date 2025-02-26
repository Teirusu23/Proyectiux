package vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelos.ClientesDAO;

public class ListaClientes extends Stage {

    private ToolBar tblMenu;
    private TableView<ClientesDAO> tbvClientes;
    private VBox vBox;
    private Scene escena;
    private Button btAgregar;

    public ListaClientes(){
        CrearUI();
        this.setTitle("Listado de Clientes ewe");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){
        tblMenu = new ToolBar(btAgregar);
        btAgregar = new Button();
        ImageView imv = new ImageView(getClass().getResource("/Images/Icon.PNG").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btAgregar.setGraphic(imv);
        tbvClientes = new TableView<>();
        vBox = new VBox(tblMenu,tbvClientes);
        escena = new Scene(vBox,800,600);
    }
}
