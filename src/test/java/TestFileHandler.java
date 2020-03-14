import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class TestFileHandler {
    File fileToWriteTo;
    PrintWriter writer = null;
    private File file = new File(System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\UIPreferences.txt");
    boolean makeFileIfFalse = true;
    @Test
    public void checkForFile() {
        fileToWriteTo = file;
        boolean exists = file.exists();
        System.out.println(exists);
        System.out.println("File:" + file.getName());
        System.out.println("Path: " + file.getPath());

        if (!exists) {

            if (makeFileIfFalse) {
                this.writeFile();
            }
        }

    }


    public void writeFile() {

        String filePath = fileToWriteTo.getPath();
        String fileName = fileToWriteTo.getName();
        String fileDirectory = filePath.replace("\\" + fileName, "");
        try {
            boolean success = new File(fileDirectory).mkdirs(); // FIXME: 8/29/2018 Make it build file provided.
            System.out.println("File built.");
            System.out.println(""+ success);

            writer = new PrintWriter(file, "UTF-8");
            System.out.println("Successfully created file.");
            }

        catch (FileNotFoundException e) {e.printStackTrace();}
        catch (UnsupportedEncodingException e) {e.printStackTrace();}
            writer.close();

    }
}