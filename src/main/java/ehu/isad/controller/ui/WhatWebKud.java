package ehu.isad.controller.ui;

import ehu.isad.controller.db.CMSDBKud;
import ehu.isad.controller.db.WhatWebDBKud;
import ehu.isad.utils.Config;
import ehu.isad.utils.Utils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.WeakHashMap;

public class WhatWebKud implements Initializable {

    @FXML
    private Button btnBotoiaEgi;

    @FXML
    private TextField txtURL;

    @FXML
    private TextArea txtLog;

    public void onClick(ActionEvent event) throws IOException, SQLException {
        Button btn = (Button) event.getSource();
        String newLine = System.getProperty("line.separator");
        txtLog.setWrapText(true);
        txtLog.setText("Kargatzen. Itxaron, mesedez....");

        Thread taskThread = new Thread(() -> {

            String newL = System.getProperty("line.separator");
            final StringBuilder emaitza = new StringBuilder();
            komandoaExekutatu(txtURL.getText()).forEach(line -> {
                emaitza.append(line + newL);
            }
            );

            Platform.runLater(() -> {
                txtLog.setText(emaitza.toString());

            });

        });

        taskThread.start();


    }


    private void deleteFile() throws IOException {
        File tempFile = new File(Config.TMPFILE);
        tempFile.delete();
    }

    //Transforma los ignore en or ignore
    public void sqlLiteKargatu() throws IOException, SQLException {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FileInputStream fstream = new FileInputStream(Config.TMPFILE);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String linea="";

        while((linea=br.readLine())!=null){
            WhatWebDBKud.getInstance().urlDatuBaseanSartu(linea.replace("IGNORE", "OR IGNORE"));
        }
        br.close();
    }

    public WhatWebKud(){
        System.out.println("whatweb");
    }

    public List<String> komandoaExekutatu(String url) {
        List<String> processes = new LinkedList<String>();
        try {
            String line;
            Process p = null;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                p = Runtime.getRuntime().exec
                        (System.getenv("windir") + "\\system32\\" + "tasklist.exe");

            } else {
                if (datuBaseaSortutaDago()) {
                    p = Runtime.getRuntime().exec("whatweb --color=never " +
                            "--log-sql=" + Config.TMPFILE + " " + url);
                }else {
                    p = Runtime.getRuntime().exec("whatweb --color=never " +
                            "--log-sql-create=" + Config.TMPFILE + " " + url);
                }
            }
            sqlLiteKargatu();
            datuBaseaEguneratu();
            deleteFile();
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                processes.add(line);
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        return processes;
    }


    public void datuBaseaEguneratu() throws SQLException {
        String target = CMSDBKud.getInstance().azkenengoTargetLortu();
        List<String> jadaTaulanDaude = CMSDBKud.getInstance().cmsTaulaTargetsLortu();
        if(jadaTaulanDaude.contains(target)){
            CMSDBKud.getInstance().eguneratuData(target);
        }
        else{
            CMSDBKud.getInstance().gehituCMSBerria(target);
        }
    }
    private boolean datuBaseaSortutaDago() throws SQLException {
        return WhatWebDBKud.getInstance().datuBaseaSortutaDago();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}