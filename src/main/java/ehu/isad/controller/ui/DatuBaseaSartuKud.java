package ehu.isad.controller.ui;

import ehu.isad.Main;
import ehu.isad.utils.Config;
import ehu.isad.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class DatuBaseaSartuKud implements Initializable {

    @FXML
    private TextField tfPath;

    @FXML
    private Label lblWarning;

    @FXML
    private Button btnBotoia;

    @FXML
    void onClick(ActionEvent event) throws IOException {
        if(begiratuDatuBaserikDagoen(tfPath.getText())) {
            whatwebfxEditatu(tfPath.getText());
            mainApp.hasieraSceneJarri();
        }
        else{
            lblWarning.setText("Sartu duzun path-ean ez da existitzen datu baserik mesedez saia zaitez berriro");
        }
    }

    public DatuBaseaSartuKud(){
        System.out.println("datuBase");
    }

    private boolean begiratuDatuBaserikDagoen(String path){
        File tempFile = new File(path);
        if(tempFile.exists()){
            return true;
        }
        else{
            return false;
        }
    }

    private Main mainApp;

    public DatuBaseaSartuKud(Main mainApp) {
        this.mainApp = mainApp;
    }

    private void whatwebfxSortu() throws IOException {
        String db = System.getProperty("user.home")+"/.whatwebfx.properties";
        File tempFile = new File(db);
        if(!tempFile.exists()){
            tempFile.createNewFile();
        }
    }

    private void whatwebfxEditatu(String path) throws IOException {
        String db = System.getProperty("user.home")+"/.whatwebfx.properties";
        FileWriter myWriter = new FileWriter(db);
        myWriter.write("dbpath="+path+"\n" +
                "tmpDir=/tmp/");
        myWriter.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            whatwebfxSortu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
