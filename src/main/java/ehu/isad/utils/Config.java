package ehu.isad.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public static final String INSERT = "insert.sql";
    public static String TMPFILE;
    public static String DBPATH;

    private static Config config;

    static {
        try {
            config = new Config();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Config() throws IOException {
        //eraikitzailea
        TMPFILE = File.separator+"tmp"+File.separator;
        DBPATH = System.getProperty("user.home")+File.separator+".whatwebfx"+File.separator+"whatweb.sqlite";
    }
}
