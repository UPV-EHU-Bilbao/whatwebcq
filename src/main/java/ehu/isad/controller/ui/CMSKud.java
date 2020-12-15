package ehu.isad.controller.ui;

import ehu.isad.controller.db.CMSDBKud;
import ehu.isad.model.Webgunea;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.css.CssMetaData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class CMSKud implements Initializable {

    @FXML
    private Button btnBotoiaHelbu;

    @FXML
    private TableView<Webgunea> tbvTaula;

    @FXML
    private TableColumn<Webgunea, String> tcURL;

    @FXML
    private TableColumn<Webgunea, String> tcCMS;

    @FXML
    private TableColumn<Webgunea, String> tcVersion;

    @FXML
    private TableColumn<Webgunea, String> tcLastUpdated;

    @FXML
    private TextField txtUrl;

    @FXML
    private ComboBox<String> cmbCombo;

    @FXML
    private Button btnAdd;

    private ObservableList<Webgunea> modeloa;

    @FXML
    void onClick(ActionEvent event) throws IOException {
    }

    public void urlSartu() throws SQLException {
        List<Webgunea> kargatzekoa = CMSDBKud.getInstance().cmsListaLortu();
        modeloa = FXCollections.observableArrayList(kargatzekoa);
        tbvTaula.setItems(modeloa);
        tcURL.setCellValueFactory(new PropertyValueFactory<>("url"));
        tcCMS.setCellValueFactory(new PropertyValueFactory<>("cms"));
        tcVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        tcLastUpdated.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));

        this.addTextFilter();

    }

    public void addTextFilter() {

        ObjectProperty<Predicate<Webgunea>> urlFiltroa = new SimpleObjectProperty<>();

        ObjectProperty<Predicate<Webgunea>> comboFiltroa = new SimpleObjectProperty<>();

        // filtroa zehaztu
        urlFiltroa.bind(Bindings.createObjectBinding(()->
                webgune -> webgune.getUrl().toLowerCase().contains(txtUrl.getText().toLowerCase())
                        || webgune.getCms().toLowerCase().contains(txtUrl.getText().toLowerCase()),
                txtUrl.textProperty()
        ));
        comboFiltroa.bind(Bindings.createObjectBinding(()->
                webgune ->  cmbCombo.getValue().equals("") ||
                        webgune.getCms().toLowerCase().contains(cmbCombo.getValue().toLowerCase()),
                        cmbCombo.valueProperty()

        ));


        FilteredList<Webgunea> filteredData = new FilteredList<>(modeloa,b->true);

        filteredData.predicateProperty().bind(Bindings.createObjectBinding(
                ()->urlFiltroa.get().and(comboFiltroa.get()),urlFiltroa,comboFiltroa));


        SortedList<Webgunea> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbvTaula.comparatorProperty());
        tbvTaula.setItems(sortedData);


    }

    public CMSKud(){
        System.out.println("cms");
    }

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        cmbCombo.getItems().addAll("","WordPress","Drupal","Joomla");
        cmbCombo.getSelectionModel().selectFirst();
    }

}