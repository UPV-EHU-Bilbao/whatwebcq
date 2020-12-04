package ehu.isad.controller.ui;

import ehu.isad.Main;
import ehu.isad.utils.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.io.*;

public class DatuBaseaSartuKud {

    @FXML
    private TextField tfPath;

    @FXML
    private Label lblWarning;

    @FXML
    private Button btnBotoia;

    @FXML
    void onClick(ActionEvent event) throws IOException {
        if(begiratuDatuBaserikDagoen(tfPath.getText())) {
            propertiesEguneratu(tfPath.getText());
            mainApp.hasieraSceneJarri();
        }
        else{
            lblWarning.setText("Sartu duzun path-ean ez da existitzen datu baserik mesedez saia zaitez berriro");
        }
    }

    public DatuBaseaSartuKud(){
        System.out.println("datuBase");
    }

    private void propertiesEguneratu(String path) throws IOException {
        String line ="";
        File f1 = new File("/setup.properties");
        if(f1.exists()) {
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                if (line.contains("dbpath"))
                    line = line.replace("dbpath=", "dbpath=" + path);
            }
        }
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


}
