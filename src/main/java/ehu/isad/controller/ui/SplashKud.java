package ehu.isad.controller.ui;

import ehu.isad.Main;
import ehu.isad.controller.db.WhatWebDBKud;
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
        if (System.getProperty("os.name").toLowerCase().contains("win"))
            Runtime.getRuntime().exec("wsl whatweb --log-sql-create="+System.getProperty("user.home")+"/.whatwebfx/whatweb.txt");
        else{
            System.out.println("whatweb --log-sql-create="+System.getProperty("user.home")+"/.whatwebfx/whatweb.txt");
            Runtime.getRuntime().exec("whatweb --log-sql-create="+System.getProperty("user.home")+"/.whatwebfx/whatweb.txt");
        }
        File datubasea = new File(System.getProperty("user.home")+File.separator+".whatwebfx"+File.separator+"whatweb.sqlite");
        datubasea.createNewFile();

    }

    public boolean instalatutaDago(){
        String path = System.getProperty("user.home")+File.separator+".whatwebfx";
        File karpeta = new File(path);
        return karpeta.exists();
    }

    public void datuBaseaSortu() throws IOException {
        try{
            Thread.sleep(3000);
        }
        catch(Throwable t){
            t.printStackTrace();
        }

        FileInputStream fstream = new FileInputStream(System.getProperty("user.home")+File.separator+".whatwebfx"+File.separator+"whatweb.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String linea="";
        int kont=0;
        while((linea=br.readLine())!=null){
            System.out.println(linea);
            if(kont<6){
            String lerroberria1 = linea.replace("IGNORE", "OR IGNORE");
            lerroberria1 = lerroberria1.replace("CREATE TABLE plugins (plugin_id int NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL,PRIMARY KEY (plugin_id), UNIQUE (name));" ,  "CREATE TABLE \"plugins\" (\"plugin_id\"\tINTEGER NOT NULL,\"name\"\tTEXT NOT NULL UNIQUE,PRIMARY KEY(\"plugin_id\" AUTOINCREMENT))");
            lerroberria1 = lerroberria1.replace("CREATE TABLE scans (scan_id int NOT NULL AUTO_INCREMENT, config_id INT NOT NULL, plugin_id INT NOT NULL, target_id INT NOT NULL, version varchar(255), os varchar(255), string varchar(1024), account varchar(1024), model varchar(1024), firmware varchar(1024), module varchar(1024), filepath varchar(1024), certainty varchar(10) ,PRIMARY KEY (scan_id));" , "CREATE TABLE \"scans\" (\"scan_id\"\tINTEGER NOT NULL,\"config_id\" INTEGER NOT NULL,\"plugin_id\" NUMERIC NOT NULL,\"target_id\"\tNUMERIC NOT NULL,\"version\" TEXT,\"os\" TEXT,\"string\" TEXT,\"account\" TEXT,\"model\" TEXT,\"firmware\" TEXT,\"module\"\tTEXT,\"filepath\"\tTEXT,\"certainty\" TEXT,PRIMARY KEY(\"scan_id\" AUTOINCREMENT))");
            lerroberria1 = lerroberria1.replace("CREATE TABLE targets (target_id int NOT NULL AUTO_INCREMENT, target varchar(2048) NOT NULL, status varchar(10),PRIMARY KEY (target_id), UNIQUE (target, status) );", "CREATE TABLE `targets` (`target_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,`target`\tTEXT NOT NULL,`status` TEXT,unique (target,status))");
            lerroberria1 = lerroberria1.replace("CREATE TABLE request_configs (config_id int NOT NULL AUTO_INCREMENT, value TEXT NOT NULL, PRIMARY KEY (config_id) );", "CREATE TABLE \"request_configs\" (\"config_id\" INTEGER NOT NULL,\"value\" TEXT NOT NULL, PRIMARY KEY(\"config_id\" AUTOINCREMENT))");
            WhatWebDBKud.getInstance().urlDatuBaseanSartu(lerroberria1);
            kont++;}
            else{
                WhatWebDBKud.getInstance().urlDatuBaseanSartu(linea);
            }
        }
        br.close();
        String taulaLaguntzailea = "CREATE TABLE \"cms_taula\" (\"target\" TEXT NOT NULL,\"version\" TEXT NOT NULL,\"cms\" TEXT NOT NULL,\"lastUpdated\" TEXT NOT NULL, PRIMARY KEY(\"target\"))";
        WhatWebDBKud.getInstance().urlDatuBaseanSartu(taulaLaguntzailea);
        File ezabatu = new File(System.getProperty("user.home")+File.separator+".whatwebfx"+File.separator+"whatweb.txt");
        ezabatu.delete();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.apPane.setStyle("-fx-background-color: transparent;");
    }

}
