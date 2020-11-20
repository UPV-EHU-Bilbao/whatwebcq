package ehu.isad.controller.ui;

import ehu.isad.controller.db.CMSDBKud;
import ehu.isad.controller.url.URL;
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
import java.util.List;
import java.util.ResourceBundle;

public class CMSKud implements Initializable {

    @FXML
    private Button btnBotoiaHelbu;

    @FXML
    private TableView<URL> tbvTaula;

    @FXML
    private TableColumn<URL, String> tcURL;

    @FXML
    private TableColumn<URL, String> tcCMS;

    @FXML
    private TableColumn<URL, String> tcVersion;

    @FXML
    private TableColumn<URL, String> tcLastUpdated;


    @FXML
    void onClick(ActionEvent event) throws IOException {
    }

    public void urlSartu(){
        List<URL> kargatzekoa = CMSDBKud.getInstance().urlLortu();
        ObservableList<URL> Urlak = FXCollections.observableArrayList(kargatzekoa);
        tbvTaula.setItems(Urlak);
        tcURL.setCellValueFactory(new PropertyValueFactory<>("url"));
    }


    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {

    }
}
