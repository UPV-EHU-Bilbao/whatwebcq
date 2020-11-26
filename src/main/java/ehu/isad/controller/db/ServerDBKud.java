package ehu.isad.controller.db;

import ehu.isad.model.Server;
import ehu.isad.model.URL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServerDBKud {

    private static final ServerDBKud instance = new ServerDBKud();

    public static ServerDBKud getInstance() {
        return instance;
    }

    public List<String> targetakLortu(){
        List<String> emaitza = new ArrayList<>();
        String query = "SELECT DISTINCT target FROM targets, scans ORDER BY scan_id";
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

    public List<Server> serverLortu(List<String> targetak) {
        String eskaneatu="";
        List<Server> emaitza = new ArrayList<>();
        Iterator<String> itr = targetak.iterator();
        while(itr.hasNext()) {
            eskaneatu = itr.next();
            String query = "Select DISTINCT target, string FROM targets t, scans s\n" +
                    "WHERE t.target_id=s.target_id AND s.plugin_id=268 AND t.target LIKE \"%" + eskaneatu + "%\"";
            DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
            ResultSet rs = dbKudeatzaile.execSQL(query);
            try {
                if (rs.next() != false) {
                    String target = rs.getString("target");
                    String server = rs.getString("string");
                    Server serverEma = new Server(target, server);
                    emaitza.add(serverEma);
                } else {
                    Server serverEma = new Server(eskaneatu, "unknown");
                    emaitza.add(serverEma);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return emaitza;
    }
}
