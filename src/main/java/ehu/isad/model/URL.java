package ehu.isad.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class URL {
    private String url;
    private String cms;
    private String version;
    private String lastUpdated;

    public URL(String url, String cms, String version, String lastUpdated) {
        this.url = url;
        this.cms = cms;
        this.version = version;
        this.lastUpdated = lastUpdated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCms() {
        return cms;
    }

    public void setCms(String cms) {
        this.cms = cms;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean contains(String textIwantB) {
        if(this.url.contains(textIwantB)){
            return true;
        }
        return false;
    }
}
