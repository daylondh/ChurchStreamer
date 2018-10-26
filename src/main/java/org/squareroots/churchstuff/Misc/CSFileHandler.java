package org.squareroots.churchstuff.Misc;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CSFileHandler {

    private PrintWriter writer = null;
    private String[] listOfFiles;

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
                System.out.println(smallArray[indexOfSmallArray] + " Contains" + whatToCheckFor + " = false. From smallArrayContains function.");return false; }
            if (smallArray[indexOfSmallArray].contains(whatToCheckFor)) {return true;}
            else {indexOfSmallArray++;}

        }

        return false;
    }
}
