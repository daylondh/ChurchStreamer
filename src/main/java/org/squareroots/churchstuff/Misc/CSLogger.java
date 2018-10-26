package org.squareroots.churchstuff.Misc;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CSLogger {
    static CSFileHandler csfh = new CSFileHandler();
    static File logFile = new File((System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\logs.txt"));
    public static void logData(String data) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();


        csfh.checkForFile(logFile, true);


        try (PrintWriter logger = new PrintWriter(new FileWriter(logFile, true))) {
            logger.println(dtf.format(time) + ": " + data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
