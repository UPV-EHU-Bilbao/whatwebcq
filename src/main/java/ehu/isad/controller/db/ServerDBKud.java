package ehu.isad.controller.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerDBKud {

    private static final ServerDBKud instance = new ServerDBKud();

    public static ServerDBKud getInstance() {
        return instance;
    }

    public List<String> urlLortu(){
        String query = "select target from targets";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);
        List<String> emaitza=new ArrayList<>();

        try {
            while (rs.next()) {
                String url=rs.getString("target");
                emaitza.add(url);
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return emaitza;
    }

}
