package org.squareroots.churchstuff.Misc;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileHandler {

    private static PrintWriter writer = null;
    private static String[] listOfFiles;


    public void scanDirectory(String directory) {

        File folder = new File(directory);
        listOfFiles = folder.list();


    }

    public String getNewFilePath(String directory) {

        File folder = new File(directory);
        String[] listOfFiles = folder.list();

        String path = directory + "\\" +findDifferenceBetweenArrays(listOfFiles, this.listOfFiles );
        System.out.println("Recorded file found at:" + path);

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
