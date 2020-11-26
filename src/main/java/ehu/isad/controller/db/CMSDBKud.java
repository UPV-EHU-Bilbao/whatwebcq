package ehu.isad.controller.db;

import ehu.isad.controller.ui.ServerKud;
import ehu.isad.model.Server;
import ehu.isad.model.URL;
import javafx.scene.control.TableColumn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CMSDBKud {

    private static final CMSDBKud instance = new CMSDBKud();

    public static CMSDBKud getInstance() {
        return instance;
    }

    public List<String> targetakLortu(){
        List<String> emaitza = new ArrayList<>();
        String query = "SELECT target FROM targets";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);

        try{
            while(rs.next()){
                String target = rs.getString("target");
                emaitza.add(target);
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return emaitza;
    }

    public List<URL> cmsLortu(List<String> targetak) {
        String eskaneatu="";
        List<URL> emaitza = new ArrayList<>();
        Iterator<String> itr = targetak.iterator();
        while(itr.hasNext()) {
            eskaneatu=itr.next();
            String query = "SELECT DISTINCT target, s.version, c.string\n" +
                    "FROM scans s \n" +
                    "INNER JOIN targets t ON s.target_id=t.target_id\n" +
                    "INNER JOIN scans c ON t.target_id=c.target_id\n" +
                    "WHERE (c.plugin_id=192 or c.plugin_id != 192) and t.target like \"%"+eskaneatu+"%\"";
            DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
            ResultSet rs = dbKudeatzaile.execSQL(query);
            try {
                while (rs.next()) {
                    if (rs != null) {
                        String target = rs.getString("target");
                        String cms = rs.getString("c.string");
                        String version = rs.getString("s.version");
                        URL url = new URL(target, cms, version, null);
                        emaitza.add(url);
                    } else {
                        URL url = new URL("Parametro de textField de lo que queremos escanear", "unknown", "0", null);
                        emaitza.add(url);
                    }
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return emaitza;
    }
}
