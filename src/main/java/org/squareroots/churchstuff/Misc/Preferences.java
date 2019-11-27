package org.squareroots.churchstuff.Misc;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Preferences {
    public static final String PATH = "./src/main/resources/preferences.json";
    public static boolean getIsDarkTheme() {
        File file = new File(PATH);
        try {
            FileReader reader = new FileReader(file);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(reader);
            return Boolean.parseBoolean(jsonObj.get("dark").toString());


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean streamEnabled() {
        File file = new File(PATH);
        try {
            FileReader reader = new FileReader(file);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(reader);
            return Boolean.parseBoolean(jsonObj.get("streamEnabled").toString());


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String getPrivacy() {
        File file = new File(PATH);
        try {
            FileReader reader = new FileReader(file);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(reader);
            return jsonObj.get("privacy").toString();


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return "public";
    }

    public static void setDarkTheme(boolean bool) {
        File file = new File(PATH);
        try {
            FileReader reader = new FileReader(file);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(reader);
            jsonObj.put("dark", bool);
            FileWriter writer = new FileWriter(PATH);
            writer.write(jsonObj.toJSONString());
            writer.close();


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPrivacy(String privacy) {
        File file = new File(PATH);
        try {
            FileReader reader = new FileReader(file);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(reader);
            jsonObj.put("privacy", privacy);
            FileWriter writer = new FileWriter(PATH);
            writer.write(jsonObj.toJSONString());
            writer.close();


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void setStreamEnabled(boolean bool) {
        File file = new File(PATH);
        try {
            FileReader reader = new FileReader(file);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(reader);
            jsonObj.put("streamEnabled", bool);
            FileWriter writer = new FileWriter(PATH);
            writer.write(jsonObj.toJSONString());
            writer.close();


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
