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
        tbvTaula.getItems().remove(0,tbvTaula.getItems().size());
        List<String> targetak = CMSDBKud.getInstance().targetakLortu();
        List<URL> kargatzekoa = CMSDBKud.getInstance().cmsLortu(targetak);
        ObservableList<URL> Urlak = FXCollections.observableArrayList(kargatzekoa);
        tbvTaula.setItems(Urlak);
        tcURL.setCellValueFactory(new PropertyValueFactory<>("url"));
        tcCMS.setCellValueFactory(new PropertyValueFactory<>("cms"));
        tcVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
    }

    public boolean urlEzNull(){
        if(txtUrl.equals(null)){
            return false;
        }
        else{
            return true;
        }
    }

    public void filtroa() {

        /*ObservableList<List<Object>> allData,
        TextField filterField, TableView<List<Object>> table*/
        List<String> targetak = CMSDBKud.getInstance().targetakLortu();
        ObservableList<URL> urlLista = FXCollections.observableArrayList(CMSDBKud.getInstance().cmsLortu(targetak));
        FilteredList<URL> filteredData  = new FilteredList<>(urlLista, p -> true);
        txtUrl.setOnKeyReleased(e ->
        {
            filteredData.setPredicate(p  ->
            {
                if (txtUrl.getText() == null || txtUrl.getText().isEmpty()){
                    return true;
                }else {
                    String pToString = p.toString().toLowerCase().replace(", "," ");
                    String textIwantB = txtUrl.getText();
                    String[] parts = textIwantB.toLowerCase().split(" ");

                    if(p.contains(textIwantB)){
                        System.out.println("p.: " + p);

                    }

                    int counter = 0;
                    for (int i = 0; i < parts.length; i ++) {
                        if (parts[i] != null)
                            if(!(pToString.contains(parts[i]))){
                                System.out.println("this one is eliminated: " + pToString);
                                return false;
                            }
                        counter++;
                    }

                    System.out.println("counter: " + counter);




                    return pToString.contains(parts[0]);
                }
            });


        });

        SortedList<URL> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbvTaula.comparatorProperty());
        tbvTaula.setItems(sortedData);
    }



    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        cmbCombo.getItems().addAll("Url", "Cms", "Version", "LastUpdated");
        cmbCombo.getSelectionModel().selectFirst();
        filtroa();
    }
}