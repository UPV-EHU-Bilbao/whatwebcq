package ehu.isad.controller.ui;

import ehu.isad.Main;
import ehu.isad.controller.db.WhatWebDBKud;
import ehu.isad.utils.Config;
import javafx.application.Platform;
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


    public void beharDirenFileSortu() throws IOException {
        Path path = Paths.get(System.getProperty("user.home")+File.separator+".whatwebfx");
        Files.createDirectory(path);

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            Runtime.getRuntime().exec("wsl wget https://raw.githubusercontent.com/Jasielprogramador/base_de_datos/master/whatweb.sqlite -O "+System.getProperty("user.home")+".whatwebfx/whatweb.sqlite");
        } else {
            Runtime.getRuntime().exec("wget https://raw.githubusercontent.com/Jasielprogramador/base_de_datos/master/whatweb.sqlite -O "+System.getProperty("user.home")+".whatwebfx/whatweb.sqlite");
        }
    }

    public boolean instalatutaDago(){
        String path = System.getProperty("user.home")+File.separator+".whatwebfx";
        File karpeta = new File(path);
        return karpeta.exists();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.apPane.setStyle("-fx-background-color: transparent;");
        if (!instalatutaDago()) {
            try {
                beharDirenFileSortu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
