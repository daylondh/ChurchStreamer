package org.squareroots.churchstuff.Misc;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CSFileHandler {

    private static PrintWriter writer = null;
    private static String[] listOfFiles;

    public static boolean checkForFile(File file, boolean makeFileIfFalse) { //call this function for

        boolean exists = file.exists();
        if (!exists) {
            if (makeFileIfFalse) {
                makeFile(file);
            }
        }

        return exists;
    }


    public static void makeFile(File file) {

        String filePath = file.getPath();
        String fileName = file.getName();
        String fileDirectory = filePath.replace("\\" + fileName, "");

        try {
            boolean success = new File(fileDirectory).mkdirs();
            System.out.println(file +" built.");
            System.out.println("Finalizing encoding for " + file+ "...");
            writer = new PrintWriter(file, "UTF-8");
            System.out.println("Successfully created file.");
        }

        catch (FileNotFoundException e) {e.printStackTrace();}
        catch (UnsupportedEncodingException e) {e.printStackTrace();}
        writer.close();

    }

    public static String fileToString(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    public static void writeToFile(File file, String content) {
        try {
            FileWriter fw = new FileWriter(file);
                    fw.write(content);
                    fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Broken
    public static String fileToString(File file) {
        String filePath = file.getPath();
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    public void scanDirectory(String directory) {

        File folder = new File(directory);
        listOfFiles = folder.list();


    }

    public String getNewFilePath(String directory) {

        File folder = new File(directory);
        String[] listOfFiles = folder.list();

        String path = directory + "\\" +findDifferenceBetweenArrays(listOfFiles, this.listOfFiles );
        System.out.println("Recorded file found at:" + path);
        CSLogger.logData("Recorded file found at:" + path);

        return path;
    }

    private String findDifferenceBetweenArrays(String[] largeArray, String[] smallArray) {
        int indexOfLargeArray = 0;
        while(indexOfLargeArray < largeArray.length) {
            boolean containsString = smallArrayContains(largeArray[indexOfLargeArray], smallArray);

            if(!containsString) {
                System.out.println(largeArray[indexOfLargeArray] + " is the different one. From findDifferenceBetweenArrays function.");
                return largeArray[indexOfLargeArray];}
            else {indexOfLargeArray++;}

        }
        return null;
    }

    private boolean smallArrayContains(String whatToCheckFor, String[] smallArray) {
        int indexOfSmallArray = 0;
        while (indexOfSmallArray < smallArray.length) {


            if (indexOfSmallArray >= smallArray.length) {
                System.out.println(smallArray[indexOfSmallArray] + " Contains" + whatToCheckFor + " = false. From smallArrayContains function.");
                return false; }
            if (smallArray[indexOfSmallArray].contains(whatToCheckFor)) {return true;}
            else {indexOfSmallArray++;}

        }

        return false;
    }

    /**
     * Only ever call this method if you want to clear all preferences, logs, and history.
     */
    public static void restoreDefault() {
        try {
            File file1 = new File(System.getProperty("user.home") + "\\AppData\\Local\\ChurchStreamer\\preferences\\StreamPrivacy.csdat");
            File file2 = new File(System.getProperty("user.home") + "\\AppData\\Local\\ChurchStreamer\\preferences\\UIPreferences.csdat");
            File file3 = new File(System.getProperty("user.home") + "\\AppData\\Local\\ChurchStreamer\\logs.log");
            File file4 = new File(System.getProperty("user.home") + "\\AppData\\Local\\ChurchStreamer\\preferences\\MayStream.csdat");
            File subdirectory = new File(System.getProperty("user.home") + "\\AppData\\Local\\ChurchStreamer\\preferences");
            File directory = new File(System.getProperty("user.home") + "\\AppData\\Local\\ChurchStreamer");

            file1.delete();
            file2.delete();
            file3.delete();
            subdirectory.delete();
            directory.delete();
        }
    catch (Exception e) {
        e.printStackTrace();
    }
    }
}
