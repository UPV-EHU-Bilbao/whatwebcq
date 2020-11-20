package ehu.isad.controller.db;

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

}
