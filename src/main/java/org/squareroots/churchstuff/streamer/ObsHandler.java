package org.squareroots.churchstuff.streamer;

import java.io.File;
import java.io.IOException;

/**
 * Created by alexh on 3/1/2018.
 */
public class ObsHandler {
    Process p = null;

    public void start() {

        ProcessBuilder pb = new ProcessBuilder();
        File directory = new File("C:\\Program Files (x86)\\obs-studio\\bin\\64bit");
        pb.command("C:\\Program Files (x86)\\obs-studio\\bin\\64bit\\obs64.exe","--startstreaming");
        pb.directory(directory);
        try {
         p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        p.destroy();
    }
}
