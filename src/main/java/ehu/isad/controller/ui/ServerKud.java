package ehu.isad.controller.ui;

import ehu.isad.controller.db.CMSDBKud;
import ehu.isad.controller.db.ServerDBKud;
import ehu.isad.model.Server;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableColumn<Server, String> tcTarget;

    @FXML
    private TableColumn<Server, String> tcServer;

    @FXML
    void onClick(ActionEvent event) throws IOException {
    }

    public ServerKud(){
        System.out.println("server");
    }

    public void aktualizatuLista() {
        //tvTaula.getItems().remove(0, tvTaula.getItems().size());
        List<String> targetak = ServerDBKud.getInstance().targetakLortu();
        List<ehu.isad.model.Server> kargatzekoa = ServerDBKud.getInstance().serverLortu(targetak);
        ObservableList<ehu.isad.model.Server> servers = FXCollections.observableArrayList(kargatzekoa);
        tvTaula.setItems(servers);
        tcTarget.setCellValueFactory(new PropertyValueFactory<>("target"));
        tcServer.setCellValueFactory(new PropertyValueFactory<>("server"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
