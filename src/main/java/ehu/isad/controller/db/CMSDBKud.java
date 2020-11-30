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

    public List<String> targetakLortu(){
        List<String> emaitza = new ArrayList<>();
        String query = "SELECT DISTINCT target FROM targets, scans ORDER BY scan_id";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);

        try{
            while(rs.next()){
                String target = rs.getString("target");
                emaitza.add(target);
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return emaitza;
    }

    public List<Webgunea> filtroa(String url){
        String query ="select target,version,cms,lastUpdated from cms_taula where target like \"%"+url+"%\"";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);
        List<Webgunea> emaitza = new ArrayList<>();
        try{
            while(rs.next()){
                String target = rs.getString("target");
                String cms = rs.getString("cms");
                String version = rs.getString("version");
                String last = rs.getString("lastUpdated");
                Webgunea a = new Webgunea(target,cms,version,last);
                emaitza.add(a);
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return emaitza;
    }

    public List<Webgunea> cmsLortu(List<String> targetak) throws SQLException {
        String eskaneatu="";
        List<Webgunea> emaitza = new ArrayList<>();
        Iterator<String> itr = targetak.iterator();
        while(itr.hasNext()) {
            eskaneatu=itr.next();
            String query = "SELECT DISTINCT target, s.version, c.string\n" +
                    "FROM scans s \n" +
                    "INNER JOIN targets t ON s.target_id=t.target_id\n" +
                    "INNER JOIN scans c ON t.target_id=c.target_id\n" +
                    "WHERE (c.plugin_id=192) AND (s.plugin_id=1152 OR s.plugin_id=132 OR s.plugin_id=337) AND t.target = \""+eskaneatu+"\"";
            DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
            ResultSet rs = dbKudeatzaile.execSQL(query);
            try {
                if (rs.next()!=false) {
                    String target = rs.getString("target");
                    String cms = rs.getString("string");
                    String version = rs.getString("version");
                    Webgunea webgunea = new Webgunea(target, cms, version, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    emaitza.add(webgunea);
                } else {
                    Webgunea webgunea = new Webgunea(eskaneatu, "unknown", "0", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    emaitza.add(webgunea);
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return emaitza;
    }

    public String azkenengoTargetLortu() throws SQLException {
        String query = "SELECT target FROM targets t, scans s WHERE scan_id=(SELECT MAX(scan_id) FROM scans) AND s.target_id=t.target_id";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);
        if(rs!=null) {
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

    public void eguneratuData(String target) throws SQLException {
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        String lortuCMSAtributuak = "SELECT DISTINCT target, s.version, c.string\n" +
                "FROM scans s \n" +
                "INNER JOIN targets t ON s.target_id=t.target_id\n" +
                "INNER JOIN scans c ON t.target_id=c.target_id\n" +
                "WHERE (c.plugin_id=192) AND (s.plugin_id=1152 OR s.plugin_id=132 OR s.plugin_id=337) AND t.target = \""+target+"\"";
        ResultSet rs = dbKudeatzaile.execSQL(lortuCMSAtributuak);
        String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        rs.next();
        if(rs.getRow()!=0){
            String cms = rs.getString("string");
            String version = rs.getString("version");
            String query = "UPDATE cms_taula SET lastUpdated = \"" + data + "\", cms = \"" + cms + "\", version = \"" + version + "\" WHERE target LIKE \"%" + target + "%\"";
            dbKudeatzaile.execSQL(query);
        }
        else{
            String query = "UPDATE cms_taula SET lastUpdated = \""+data+"\" WHERE target LIKE \"%"+target+"%\"";
            dbKudeatzaile.execSQL(query);
        }
    }

    public void gehituCMSBerria(String target) throws SQLException {
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        String lortuCMSAtributuak = "SELECT DISTINCT target, s.version, c.string\n" +
                "FROM scans s \n" +
                "INNER JOIN targets t ON s.target_id=t.target_id\n" +
                "INNER JOIN scans c ON t.target_id=c.target_id\n" +
                "WHERE (c.plugin_id=192) AND (s.plugin_id=1152 OR s.plugin_id=132 OR s.plugin_id=337) AND t.target = \""+target+"\"";
        ResultSet rs = dbKudeatzaile.execSQL(lortuCMSAtributuak);
        String version="";
        String cms ="";
        String data ="";
        if (rs.next()) {
            version = rs.getString("version");
            cms = rs.getString("string");
            data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        if(cms.equals("")){
            String query = "INSERT INTO cms_taula (target, version, cms, lastUpdated) VALUES (\"" + target + "\",\"0\",\"unknown\",\"" + data + "\")";
            dbKudeatzaile.execSQL(query);
        }else{
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


    /*public List<URL> cmsListaLortu(String target) throws SQLException {
        List<URL> emaitza = new ArrayList<>();
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        if(!target.equals("eskaneatuZeozer")){
            String queryNagusia = "SELECT target, s.version, c.string\n" +
                    "FROM scans s \n" +
                    "INNER JOIN targets t ON s.target_id=t.target_id\n" +
                    "INNER JOIN scans c ON t.target_id=c.target_id\n" +
                    "WHERE (c.plugin_id=192) AND (s.plugin_id=1152 OR s.plugin_id=132 OR s.plugin_id=337) AND t.target = \""+target+"\"";
            ResultSet rsNagusia = dbKudeatzaile.execSQL(queryNagusia);
            String nagusia = rsNagusia.toString();
            String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            List<String> helburuak = cmsTaulaTargetsLortu();

            if(helburuak.contains(target)){
                String query = "UPDATE cms_taula SET lastUpdated = "+data+" WHERE target = \""+target+"\"";
                dbKudeatzaile.execSQL(query);
            }
            else{
                if(rsNagusia.getRow()!=0) {
                    String targetNagusia = rsNagusia.getString("target");
                    String versionNagusia = rsNagusia.getString("version");
                    String stringNagusia = rsNagusia.getString("string");
                    String query = "INSERT INTO cms_taula (target, version, cms, lastUpdated) VALUES (\"" + targetNagusia + "\",\"" + versionNagusia + "\",\"" + stringNagusia + "\",\"" + data + "\")";
                    dbKudeatzaile.execSQL(query);
                }
                else{
                    String query = "INSERT INTO cms_taula (target, version, cms, lastUpdated) VALUES (\"" + target + "\",\"0\",\"unknown\",\"" + data + "\")";
                    dbKudeatzaile.execSQL(query);
                }
            }

            String queryCMSLortu = "SELECT target, version, cms, lastUpdated FROM cms_taula";
            ResultSet rsCMSLortu = dbKudeatzaile.execSQL(queryCMSLortu);
            try {
                while(rsCMSLortu.next()) {
                    String targetEmaitza = rsCMSLortu.getString("target");
                    String versionEmaitza = rsCMSLortu.getString("version");
                    String cmsEmaitza = rsCMSLortu.getString("cms");
                    String lastUpdatedEmaitza = rsCMSLortu.getString("lastUpdated");
                    URL url = new URL(targetEmaitza, versionEmaitza, cmsEmaitza, lastUpdatedEmaitza);
                    emaitza.add(url);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return emaitza;
        }
        else{
            return new ArrayList<URL>();
        }*/


    }
