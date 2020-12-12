package ehu.isad.controller.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WhatWebDBKud {

    private static final WhatWebDBKud instance = new WhatWebDBKud();

    public static WhatWebDBKud getInstance() {
        return instance;
    }

    public void urlDatuBaseanSartu(String linea){
        System.out.println(linea);
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        dbKudeatzaile.execSQL(linea);
    }

    /*public boolean datuBaseaSortutaDago() throws SQLException {
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        String query = "SELECT MAX(plugin_id) AS zenbat FROM plugins";
        ResultSet rs = dbKudeatzaile.execSQL(query);
        return (rs.getInt("zenbat") > 1);
    }*/

}
