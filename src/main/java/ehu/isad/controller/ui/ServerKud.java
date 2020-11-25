package ehu.isad.controller.ui;

import ehu.isad.controller.db.ServerDBKud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ServerKud implements Initializable {

    @FXML
    private Button btnBotoiaHelbu;

    @FXML
    private ListView<String> lvLista;

    @FXML
    void onClick(ActionEvent event) throws IOException {
    }

    public void aktualizatuLista(){
        List<String> lista= ServerDBKud.getInstance().urlLortu();
        for(int i=0;i<lista.size();i++){
            lvLista.getItems().add(lista.get(i));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
