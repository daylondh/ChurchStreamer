import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteToFIle {
    @Test public void writeFile() {
        try {
            String darktheme = "yes";
            File directory = new File (System.getProperty("user.home") +"\\ChurchStreamer\\preferences\\UIPreferences.txt"); // TODO: 7/30/2018 Copy this to main code
            File file = new File (System.getProperty("user.home") +"\\ChurchStreamer\\preferences\\UIPreferences.txt");


            if (!directory.exists()) {

                boolean success = new java.io.File(System.getProperty("user.home"), "\\ChurchStreamer\\preferences").mkdirs();
                System.out.println(success);
                PrintWriter writer = new PrintWriter(System.getProperty("user.home")+"\\ChurchStreamer\\preferences\\UIPreferences.txt", "UTF-8");
                writer.close();
            }




                 FileWriter fw = new FileWriter(System.getProperty("user.home")+"\\ChurchStreamer\\preferences\\UIPreferences.txt");
                   fw.write(darktheme);
                   fw.close();


        } catch (IOException e1) {
            e1.printStackTrace();

            System.out.println("It no working");
        }
    }
}
