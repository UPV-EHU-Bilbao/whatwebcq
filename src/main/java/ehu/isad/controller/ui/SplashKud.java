package ehu.isad.controller.ui;

import ehu.isad.Main;
import ehu.isad.utils.Config;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private void beharDirenFileSortu() throws IOException {
        Path path = Paths.get(System.getProperty("user.home")+File.separator+".whatwebfx");
        Files.createDirectory(path);

        //Datu basea sortzeko
        if (System.getProperty("os.name").toLowerCase().contains("win"))
            Runtime.getRuntime().exec("wsl whatweb --log-sql-create=/home/user/.whatwebfx/whatweb.sqlite");
        else{
            Runtime.getRuntime().exec("whatweb --log-sql-create=/home/user/.whatwebfx/whatweb.sqlite");
        }

        //Unistaller
        String db = System.getProperty("user.home")+File.separator+".whatwebfx"+File.separator+"unistaller.sh";
        FileWriter myWriter = new FileWriter(db);
        myWriter.write("#!/bin/bash"+"\n" +
                "sudo apt remove whatwebfx"+"\n"+
                "sudo rm -r "+System.getProperty("user.home")+File.separator+".whatwebfx");
        myWriter.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.apPane.setStyle("-fx-background-color: transparent;");
        if(!begiratuDatuBaserikDagoen(System.getProperty("user.home")+File.separator+".whatwebfx"+File.separator+"whatweb.sqlite")){
            try {
                beharDirenFileSortu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
