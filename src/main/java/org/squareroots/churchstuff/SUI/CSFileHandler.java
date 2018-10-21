package org.squareroots.churchstuff.SUI;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CSFileHandler {

    private PrintWriter writer = null;

    public boolean checkForFile(File file, boolean makeFileIfFalse) { //call this function for

        boolean exists = file.exists();
        if (exists) {
            System.out.println(file + " exists? = " + exists);
        }
        if (!exists) {
            System.err.println(file + " exists = " + exists);
            if (makeFileIfFalse) {
                this.makeFile(file);
            }
        }

        return exists;
    }


    public void makeFile(File file) {

        String filePath = file.getPath();
        String fileName = file.getName();
        String fileDirectory = filePath.replace("\\" + fileName, "");

        try {
            boolean success = new File(fileDirectory).mkdirs();
            System.out.println(file +" built.");
            System.out.println("Directory construction completed? = "+ success);
            System.out.println("Finalizing encoding for " + file+ "...");
            writer = new PrintWriter(file, "UTF-8");
            System.out.println("Successfully created file.");
        }

        catch (FileNotFoundException e) {e.printStackTrace();}
        catch (UnsupportedEncodingException e) {e.printStackTrace();}
        writer.close();

    }
    public String fileToString(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }
    public void writeToFile(File file, String content) {
        try {
            FileWriter fw = new FileWriter(file);
                    fw.write(content);
                    fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String fileToString(File file) {
        String filePath = file.getPath();
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }
}
