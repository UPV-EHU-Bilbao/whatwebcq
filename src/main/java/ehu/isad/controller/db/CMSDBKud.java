package ehu.isad.controller.db;

import ehu.isad.model.Webgunea;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CMSDBKud {

    private static final CMSDBKud instance = new CMSDBKud();

    public static CMSDBKud getInstance() {
        return instance;
    }


    public String azkenengoTargetLortu() throws SQLException {
        String query = "SELECT target FROM targets t, scans s WHERE scan_id=(SELECT MAX(scan_id) FROM scans) AND s.target_id=t.target_id";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);
        rs.next();
        if(rs.getRow()!=0) {
            return rs.getString("target");
        }else{
            System.out.println("Oraindik ez da ezer eskaneatu");
            return "eskaneatuZeozer";
        }
    }

    public List<String> cmsTaulaTargetsLortu(){
        List<String> targets = new ArrayList<>();
        String query = "SELECT DISTINCT target FROM cms_taula";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);
        try{
            while(rs.next()){
                String target = rs.getString("target");
                targets.add(target);
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return targets;
    }

    public void eguneratuDatuak(String target) throws SQLException {
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        String lortuCMSAtributuak = "SELECT DISTINCT target, s.version, c.string, MAX(s.scan_id) AS id\n" +
                "FROM scans s \n" +
                "INNER JOIN targets t ON s.target_id=t.target_id\n" +
                "INNER JOIN scans c ON t.target_id=c.target_id\n" +
                "WHERE (c.plugin_id=192) AND (s.plugin_id=1152 OR s.plugin_id=132 OR s.plugin_id=337 OR s.plugin_id=72 OR s.plugin_id=241 OR s.plugin_id=283 OR s.plugin_id=315 OR s.plugin_id=1129 OR s.plugin_id=1419 OR s.plugin_id=131 OR s.plugin_id=88 OR s.plugin_id=824 OR s.plugin_id=822) AND t.target = \""+target+"\" ORDER BY s.scan_id";
        ResultSet rs = dbKudeatzaile.execSQL(lortuCMSAtributuak);
        String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        rs.next();
        String badu = rs.getString("target");
        if(!(badu==null)){
            String cms = rs.getString("string");
            String version = rs.getString("version");
            String query = "UPDATE cms_taula SET lastUpdated = \"" + data + "\", cms = \"" + cms + "\", version = \"" + version + "\" WHERE target LIKE \"%" + target + "%\"";
            dbKudeatzaile.execSQL(query);
        }
        else{
            String query = "UPDATE cms_taula SET lastUpdated = \""+data+"\", cms = 'unknown', version = '0' WHERE target LIKE \"%"+target+"%\"";
            dbKudeatzaile.execSQL(query);
        }
    }

    public void gehituCMSBerria(String target) throws SQLException {
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        String lortuCMSAtributuak = "SELECT DISTINCT target, s.version, c.string, MAX(s.scan_id) AS id\n" +
                "FROM scans s \n" +
                "INNER JOIN targets t ON s.target_id=t.target_id\n" +
                "INNER JOIN scans c ON t.target_id=c.target_id\n" +
                "WHERE (c.plugin_id=192 or c.plugin_id=1299) AND (s.plugin_id=1152 OR s.plugin_id=132 OR s.plugin_id=337 OR s.plugin_id=72 OR s.plugin_id=241 OR s.plugin_id=283 OR s.plugin_id=315 OR s.plugin_id=1129 OR s.plugin_id=1419 OR s.plugin_id=131 OR s.plugin_id=88 OR s.plugin_id=824 OR s.plugin_id=822) AND t.target = \""+target+"\"";
        ResultSet rs = dbKudeatzaile.execSQL(lortuCMSAtributuak);
        String version="";
        String cms ="";
        String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String badu = rs.getString("target");
        if (badu==null){
            String query = "INSERT INTO cms_taula (target, version, cms, lastUpdated) VALUES (\"" + target + "\",\"0\",\"unknown\",\"" + data + "\")";
            dbKudeatzaile.execSQL(query);
        }else{
            version = rs.getString("version");
            cms = rs.getString("string");
            String query = "INSERT INTO cms_taula (target, version, cms, lastUpdated) VALUES (\"" + target + "\",\""+version+"\",\""+cms+"\",\"" + data + "\")";
            dbKudeatzaile.execSQL(query);
        }
    }

    public List<Webgunea> cmsListaLortu() {
        List<Webgunea> emaitza = new ArrayList<>();
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        String query = "SELECT target, version, cms, lastUpdated FROM cms_taula ORDER BY lastUpdated DESC";
        ResultSet rs = dbKudeatzaile.execSQL(query);
        try{
            while(rs.next()) {
                String target = rs.getString("target");
                String version = rs.getString("version");
                String cms = rs.getString("cms");
                String lastUpdated = rs.getString("lastUpdated");
                Webgunea webgunea = new Webgunea(target,version,cms,lastUpdated);
                emaitza.add(webgunea);
            }
        }catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return emaitza;
    }
}
