import java.sql.*;
import java.util.*;

import java.io.*;

class JDBC_VersionVerse {

    String url;
    String user;
    String pass;
    
    Connection conn = null;

    JDBC_VersionVerse(String url , String user , String pass ) {
        this.url = url;
        this.user = user;
        this.pass = pass;

        try {
            this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
        }catch(SQLException e) {
            System.out.println("Connection failed");
        }
    }

    Connection getConnection() {
        return conn;
    }

    String getFeature(String lang, String ver) {
        String fName = "";
        try {
            String sql = "SELECT lang_id FROM languages WHERE lang = ?";
            PreparedStatement s = conn.prepareStatement(sql);
            s.setString(1, lang);
            ResultSet r = s.executeQuery();
            int id = 0;
            if (r.next()) {
                id = r.getInt("lang_id");
            }

            if (id == 0) {
                System.out.println("No language found");
            } else {
                sql = "SELECT feature FROM version_info WHERE lang_id = ? AND version = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setString(2, ver);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    fName = rs.getString("feature");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred: " + e.getMessage());
            return "SQL Exception occurred: " + e.getMessage();
        }

        String path = "";
        try {
            path = new File(".").getCanonicalPath() + "/VersionInfo/" + lang + "/" + fName;
        } catch (IOException ie) {
            System.out.println("Malfunction");
            return "Malfunction";
        }

        File f = new File(path);
        String ret = "";
        if (f.exists()) {
            try (FileReader fr = new FileReader(f)) {
                int data;
                while ((data = fr.read()) != -1) {
                    ret += ((char) data);
                }
            } catch (FileNotFoundException fn) {
                System.out.println("File not found");
                return "File not found";
            } catch (IOException ie) {
                System.out.println("IOException occurred");
                return "IOException occurred";
            }
        }
        return ret;
    }   


    ArrayList<String> getLangList() {
        ArrayList<String> langs = new ArrayList<String>();

        try (Statement s = conn.createStatement()) {
            String sql = "SELECT lang FROM languages";
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                langs.add(r.getString("lang"));
            }

            if (langs.isEmpty()) {
                System.out.println("Empty Language Table");
            }
        } catch (SQLException se) {
            System.out.println("SQL Exception in getLangList occurred: " + se.getMessage());
        }

        return langs;
    }


    ArrayList<String> getVersionList(String lang) {
        ArrayList<String> ver = new ArrayList<String>();

        try (Statement s = conn.createStatement()) {
            String sqlLang = "SELECT lang_id FROM languages WHERE lang = ?";
            PreparedStatement ps = conn.prepareStatement(sqlLang);
            ps.setString(1, lang);
            ResultSet r1 = ps.executeQuery();

            if (r1.next()) {
                int langId = r1.getInt("lang_id");
                sqlLang = "SELECT version FROM version_info WHERE lang_id = ?";
                ps = conn.prepareStatement(sqlLang);
                ps.setInt(1, langId);
                ResultSet r2 = ps.executeQuery();

                while (r2.next()) {
                    ver.add(r2.getString("version"));
                }
            } else {
                System.out.println("No language found");
            }
        } catch (SQLException se) {
            System.out.println("SQL Exception in getVersionList occurred: " + se.getMessage());
        }

        return ver;
    }

    String getUrl(String lang , String ver) {

        String url = "";
        Statement s;
        ResultSet r1;
        ResultSet r2;
        try {
            s = conn.createStatement();
            String sql = "SELECT Id FROM Lang_Name WHERE LanguageName = "+lang;
            r1 = s.executeQuery(sql);
            r1.next();
            if(r1.getString(1).equals(null)) {
                System.out.println("No Language Found");
                return new String("error");
            }else {
                String st = "SELECT HtmlLinks FROM Versions WHERE lang_id = "+r1.getString(1) + " AND version = "+ver;
                r2 = s.executeQuery(st);
                r2.next();
                if(r2.getString(1).equals(null)) {
                    System.out.println("No feature Found");
                    return new String("No feature found");
                }else {
                    url = r2.getString(1);
                }
            }
        }catch(SQLException se) {
            System.out.println("SQL Exception");
            return new String("SQL Exception");
        }catch(Exception ie) {
            System.out.println("Exception");
            return new String("Exception");
        }
        return url;
    }

    /*boolean setFeature(String lang , String ver , File f) {
        Statement s;
        ResultSet r;
        try {
            s = conn.createStatement();
            String sql = "SELECT lang_id FROM languages WHERE lang = "+lang;
            r = s.executeQuery(sql);
            r.next();
            if(r.getString(1).equals(null)) {
                sql = "INSERT INTO languages VALUES("+lang+")";
            }
        }
        return false;
    }*/

    
}