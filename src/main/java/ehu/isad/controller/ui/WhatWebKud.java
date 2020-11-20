package ehu.isad.controller.ui;

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
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class WhatWebKud implements Initializable {

    @FXML
    private Button btnBotoiaEgi;

    @FXML
    private TextField txtURL;

    @FXML
    private TextArea txtLog;

    public void onClick(ActionEvent event) throws IOException {

        Button btn = (Button) event.getSource();
        String newLine = System.getProperty("line.separator");
        txtLog.setWrapText(true);
        txtLog.setText("Kargatzen. Itxaron, mesedez....");

        Thread taskThread = new Thread(() -> {

            String newL = System.getProperty("line.separator");
            final StringBuilder emaitza = new StringBuilder();
            komandoaExekutatu(txtURL.getText()).forEach(line -> {
                emaitza.append(line + newL);
            });

            Platform.runLater(() -> {
                txtLog.setText(emaitza.toString());

            });

        });

        taskThread.start();
    }

    private boolean existitzenDa() {
        File tempFile = new File(Utils.lortuEzarpenak().getProperty("tmpDir"));
        return tempFile.exists();
    }

    private void deleteFile() {
        File tempFile = new File(Utils.lortuEzarpenak().getProperty("tmpDir"));
        tempFile.delete();
    }

    //Transforma los ignore en or ignore
    public void sqlLiteKargatu() throws IOException {

        FileInputStream fstream = new FileInputStream(Config.TMPFILE);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String linea="";

        while((linea=br.readLine())!=null){
            WhatWebDBKud.getInstance().urlDatuBaseanSartu(linea.replace("IGNORE", "OR IGNORE"));
        }
        br.close();
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
                if (existitzenDa()) {
                    p = Runtime.getRuntime().exec("whatweb --color=never " +
                            "--log-sql=" + Config.TMPFILE + " " + url);
                } else {
                    p = Runtime.getRuntime().exec("whatweb --color=never " +
                            "--log-sql-create=" + Config.TMPFILE + " " + url);
                }
                sqlLiteKargatu();
                deleteFile();
            }
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

    public boolean urlEzNull(){
        if(txtURL.equals(null)){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

