package ehu.isad.controller.ui;

import ehu.isad.Main;
import ehu.isad.controller.db.CMSDBKud;
import ehu.isad.model.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private TextField txtUrl;

    @FXML
    private ComboBox<String> cmbCombo;

    @FXML
    private Button btnAdd;

    @FXML
    void onClick(ActionEvent event) throws IOException {
    }

    public void urlSartu(){
        List<URL> kargatzekoa = CMSDBKud.getInstance().urlLortu();
        ObservableList<URL> Urlak = FXCollections.observableArrayList(kargatzekoa);
        tbvTaula.setItems(Urlak);
        tcURL.setCellValueFactory(new PropertyValueFactory<>("url"));
    }

    public boolean urlEzNull(){
        if(txtUrl.equals(null)){
            return false;
        }
        else{
            return true;
        }
    }


    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        cmbCombo.getItems().addAll("Url","Cms","Version","LastUpdated");
    }
}