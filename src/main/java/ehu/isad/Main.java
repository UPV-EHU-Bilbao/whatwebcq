package ehu.isad;

import ehu.isad.controller.ui.*;
import ehu.isad.utils.Config;
import ehu.isad.utils.Utils;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;


public class Main extends Application {

    private Stage stage;
    private Scene sceneHasiera;
    private Scene sceneDatuBaseaSartu;
    private Parent hasieraUI;
    private Parent datuBaseaSartuUI;
    private HasieraKud hasieraKud;
    private WhatWebKud whatWebKud;
    private ServerKud serverKud;
    private CMSKud CMSKud;
    private DatuBaseaSartuKud datuBaseaSartuKud;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //sortuFitxategia();
        stage = primaryStage;
        pantailakKargatu();

        leihoaMugitu();
        stage.setScene(sceneDatuBaseaSartu);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    private void leihoaMugitu() throws IOException {
        //you can use underdecorated or transparent.
        stage.initStyle(StageStyle.TRANSPARENT);

        //grab your root here
        hasieraUI.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        //move around here
        hasieraUI.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    private void pantailakKargatu() throws IOException {

        FXMLLoader loaderDatuBase = new FXMLLoader(getClass().getResource("/datuBaseaSartu.fxml"));
        datuBaseaSartuKud = new DatuBaseaSartuKud(this); //  setMain() metodoa ekidituz

        FXMLLoader loaderHasiera = new FXMLLoader(getClass().getResource("/hasiera.fxml"));
        hasieraKud = new HasieraKud(this); //  setMain() metodoa ekidituz
        whatWebKud = new WhatWebKud();
        serverKud = new ServerKud();
        CMSKud = new CMSKud();


        Callback<Class<?>, Object> controllerFactory = type -> {
            if (type == HasieraKud.class) {
                return hasieraKud;
            } else if (type == WhatWebKud.class) {
                return whatWebKud;
            } else if (type == CMSKud.class) {
                return CMSKud;
            }
            else if(type == DatuBaseaSartuKud.class){
                return datuBaseaSartuKud;
            }
            else if(type == ServerKud.class){
                return serverKud;
            }
            else {
                // default behavior for controllerFactory:
                try {
                    return type.newInstance();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    throw new RuntimeException(exc); // fatal, just bail...
                }
            }
        };

        loaderDatuBase.setControllerFactory(controllerFactory);
        datuBaseaSartuUI = (Parent) loaderDatuBase.load();
        sceneDatuBaseaSartu=new Scene(datuBaseaSartuUI);


        loaderHasiera.setControllerFactory(controllerFactory);
        hasieraUI = (Parent) loaderHasiera.load();
        sceneHasiera=new Scene(hasieraUI);
    }

    public void hasieraSceneJarri(){
        stage.setScene(sceneHasiera);
        stage.show();
    }

}
