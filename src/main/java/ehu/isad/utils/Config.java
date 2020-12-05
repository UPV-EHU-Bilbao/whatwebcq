package ehu.isad.utils;

import java.io.IOException;
import java.util.Properties;

public class Config {

    public static final String INSERT = "insert.sql";
    public static String TMPFILE;

    private static Config config;

    static {
        try {
            config = new Config();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Config() throws IOException {
        Properties properties=Utils.lortuEzarpenak();
        TMPFILE = properties.get("tmpDir")+INSERT;
    }
}
