package ehu.isad.controller.ui;

import ehu.isad.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashKud implements Initializable {

    @FXML
    private AnchorPane apPane;

    public SplashKud(){
        System.out.println("splash");
    }

    private Main mainApp;

    public SplashKud(Main mainApp) {
        this.mainApp = mainApp;
    }

    public AnchorPane getApPane(){return this.apPane;}

    private boolean begiratuDatuBaserikDagoen(String path){
        File tempFile = new File(path);
        if(tempFile.exists()){
            return true;
        }
        else{
            return false;
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.apPane.setStyle("-fx-background-color: transparent;");
    }

}
