package ehu.isad.controller.ui;

import ehu.isad.Main;
import ehu.isad.controller.db.CMSDBKud;
import ehu.isad.model.URL;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;

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

    public void urlSartu() throws SQLException {
        List<URL> kargatzekoa = CMSDBKud.getInstance().cmsListaLortu();
        ObservableList<URL> urls = FXCollections.observableArrayList(kargatzekoa);
        tbvTaula.setItems(urls);
        tcURL.setCellValueFactory(new PropertyValueFactory<>("url"));
        tcCMS.setCellValueFactory(new PropertyValueFactory<>("cms"));
        tcVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        tcLastUpdated.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
    }

    public boolean urlEzNull(){
        if(txtUrl.equals(null)){
            return false;
        }
        else{
            return true;
        }
    }
    public void filtroa()     {
        txtUrl.textProperty().addListener((observable, oldValue, newValue) -> {

        });

    }


    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        cmbCombo.getItems().addAll("Url", "Cms", "Version", "LastUpdated");
        cmbCombo.getSelectionModel().selectFirst();
    }

}