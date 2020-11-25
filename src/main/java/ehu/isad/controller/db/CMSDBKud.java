package ehu.isad.controller.db;

import ehu.isad.model.URL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CMSDBKud {

    private static final CMSDBKud instance = new CMSDBKud();

    public static CMSDBKud getInstance() {
        return instance;
    }

    public List<URL> urlLortu(){
        String query = "select target from targets";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);
        List<URL> emaitza=new ArrayList<>();

        try {
            while (rs.next()) {
                String url=rs.getString("target");
                URL url1 = new URL(url,null,null,null);
                emaitza.add(url1);
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return emaitza;
    }
}