package ehu.isad.controller.ui;

import ehu.isad.controller.db.ServerDBKud;
import ehu.isad.model.Server;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ServerKud implements Initializable {

    @FXML
    private Button btnBotoiaHelbu;


    @FXML
    private TableView<Server> tvTaula;

    @FXML
    private TableColumn<Server, String> tblTarget;

    @FXML
    private TableColumn<Server, String> tblServer;

    @FXML
    void onClick(ActionEvent event) throws IOException {
    }

    public void aktualizatuLista() {
        tvTaula.getItems().remove(0, tvTaula.getItems().size());
        List<Server> lista = ServerDBKud.getInstance().urlLortu();
        for (int i = 0; i < lista.size(); i++) {
            tvTaula.getItems().add(lista.get(i));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
