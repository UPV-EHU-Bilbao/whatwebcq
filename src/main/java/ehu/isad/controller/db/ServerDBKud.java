package ehu.isad.controller.db;

import ehu.isad.model.Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerDBKud {

    private static final ServerDBKud instance = new ServerDBKud();

    public static ServerDBKud getInstance() {
        return instance;
    }

    public List<Server> urlLortu() {
        String query = "Select DISTINCT target, string from targets t, scans s \n" +
                "where t.target_id=s.target_id and s.plugin_id=268";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);
        List<Server> emaitza = new ArrayList<>();

        try {
            while (rs.next()) {
                String target = rs.getString("target");
                String server = rs.getString("string");
                Server serverEma = new Server(target,server);
                emaitza.add(serverEma);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return emaitza;
    }
}
