package org.squareroots.churchstuff.streamer;

import java.io.File;
import java.io.IOException;

/**
 * Created by alexh on 3/1/2018.
 */
public class ObsHandler { // TODO: 9/25/2018 Make StopStreaming actually work.
    Process p = null;


    public void startStreaming() {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");
        ProcessBuilder pb = new ProcessBuilder();
        if (isWindows) {
          //  File directory = new File("C:\\Program Files (x86)\\obs-studio\\bin\\64bit");
            //pb.command("C:\\Program Files (x86)\\obs-studio\\bin\\64bit\\obs64.exe", "--startstreaming");
            File directory = new File("C:\\Program Files\\obs-studio\\bin\\64bit");
            pb.command("C:\\Program Files\\obs-studio\\bin\\64bit\\obs64.exe", "--startstreaming");
            pb.directory(directory);
        } else {
            pb.command("obs", "--startstreaming");
        }
        try {
            p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startRecording() {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");
        ProcessBuilder pb = new ProcessBuilder();
        if (isWindows) {
           // File directory = new File("C:\\Program Files (x86)\\obs-studio\\bin\\64bit");
           // pb.command("C:\\Program Files (x86)\\obs-studio\\bin\\64bit\\obs64.exe", "--startrecording");
            File directory = new File("C:\\Program Files\\obs-studio\\bin\\64bit");
            pb.command("C:\\Program Files\\obs-studio\\bin\\64bit\\obs64.exe", "--startrecording");

            pb.directory(directory);
        } else {
            pb.command("obs", "--startrecording");
        }
        try {
            p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void StopStreaming() {p.destroy();}
    public void stopRecording() {p.destroy();}

}
