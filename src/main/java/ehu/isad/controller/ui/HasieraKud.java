package ehu.isad.controller.ui;

import ehu.isad.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HasieraKud implements Initializable {

    private Main mainApp;

    //AnchorPane-ak
    @FXML
    private AnchorPane paneCMS;

    @FXML
    private AnchorPane paneWhatWeb;

    //AnchorPanez aldatzeko botoiak
    @FXML
    private Button btnWhatWeb;

    @FXML
    private Button btnCMS;

    @FXML
    private CMSKud cmsController ;

    @FXML
    private WhatWebKud whatwebController ;


    //Irten
    @FXML
    private Button btnIrten;

    public HasieraKud(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    void onClick(ActionEvent event) throws IOException {

        Button btn = (Button) event.getSource();
        if(btn.equals(this.btnWhatWeb)){
            paneWhatWeb.toFront();
        }
        else if(btn.equals(this.btnCMS)){
            cmsController.urlSartu();
            paneCMS.toFront();
        }
        else if(btn.equals(this.btnIrten)){
            this.irten();
        }
    }

    //Irten
    public void irten() {
        Stage stage = (Stage) btnIrten.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneWhatWeb.toFront();
    }

}