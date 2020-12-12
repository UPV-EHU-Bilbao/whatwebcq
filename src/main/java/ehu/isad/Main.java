package ehu.isad;

import ehu.isad.controller.ui.*;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.time.Year;


public class Main extends Application {

    private Stage stageHasiera;
    private Stage stageSplash;
    private Scene sceneHasiera;
    private Scene sceneSplash;
    private Parent hasieraUI;
    private Parent splashUI;
    private HasieraKud hasieraKud;
    private WhatWebKud whatWebKud;
    private ServerKud serverKud;
    private CMSKud CMSKud;
    private SplashKud splashKud;
    private double xOffset = 0;
    private double yOffset = 0;




    @Override
    public void start(Stage primaryStage) throws Exception {

        pantailakKargatu();
        splashLeihoaJarri();

        Thread kargatu = new Thread(){
            public void run(){
                try {
                    if (!splashKud.instalatutaDago()){
                        splashKud.beharDirenFileSortu();
                        splashKud.datuBaseaSortu();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable task = () -> {
            if (splashKud.instalatutaDago()){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    Thread.sleep(198000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Platform.runLater(() -> {
                stageSplash.hide();
                stageHasiera = primaryStage;
                stageHasiera.setScene(sceneHasiera);
                stageHasiera.initStyle(StageStyle.UNDECORATED);
                stageHasiera.show();
            });
        };
        Thread thread = new Thread(task);
        thread.start();
        kargatu.start();

        leihoaMugitu();

    }

    public void splashLeihoaJarri() throws IOException {

        stageSplash = new Stage();
        stageSplash.setScene(sceneSplash);
        stageSplash.initStyle(StageStyle.TRANSPARENT);
        sceneSplash.setFill(Color.TRANSPARENT);
        stageSplash.show();

        FadeTransition fadeInSplash = new FadeTransition(Duration.seconds(3), splashKud.getApPane());
        fadeInSplash.setFromValue(0);
        fadeInSplash.setToValue(1);
        fadeInSplash.setCycleCount(1);

        fadeInSplash.play();
    }


    private void leihoaMugitu() throws IOException {

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
                stageHasiera.setX(event.getScreenX() - xOffset);
                stageHasiera.setY(event.getScreenY() - yOffset);
            }
        });
    }


    public void pantailakKargatu() throws IOException {

        FXMLLoader loaderSplash = new FXMLLoader(getClass().getResource("/splash.fxml"));
        splashKud = new SplashKud(this); //  setMain() metodoa ekidituz

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
            else if(type == SplashKud.class){
                return splashKud;
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

        loaderSplash.setControllerFactory(controllerFactory);
        splashUI = (Parent) loaderSplash.load();
        sceneSplash=new Scene(splashUI);

        loaderHasiera.setControllerFactory(controllerFactory);
        hasieraUI = (Parent) loaderHasiera.load();
        sceneHasiera=new Scene(hasieraUI);
    }

    }
