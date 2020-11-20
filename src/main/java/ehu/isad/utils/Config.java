package ehu.isad.utils;

import java.util.Properties;

public class Config {

    public static final String INSERT = "insert.sql";
    public static String TMPFILE;

    private static Config config=new Config();

    private Config(){
        Properties properties=Utils.lortuEzarpenak();
        TMPFILE = properties.get("tmpDir")+INSERT;
    }
}
