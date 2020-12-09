package ehu.isad.controller.ui;

import ehu.isad.Main;
import ehu.isad.utils.Config;
import ehu.isad.utils.Utils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.Text;

import java.io.*;
import java.net.URL;
import java.util.Properties;
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

    private void whatwebfxSortu() throws IOException {
        String db = System.getProperty("user.home")+"/"+".whatwebfx";
        File tempFile = new File(db);
        if(!tempFile.exists()){
            tempFile.createNewFile();
        }
    }

    private void whatwebfxEditatu(String path) throws IOException {
        String db = System.getProperty("user.home")+"/"+".whatwebfx";
        FileWriter myWriter = new FileWriter(db);
        myWriter.write("dbpath="+path+"\n" +
                "tmpDir=/tmp/");
        myWriter.close();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.apPane.setStyle("-fx-background-color: transparent;");
    }

}
