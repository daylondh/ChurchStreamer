import org.junit.Test;

import java.io.File;

public class CanIAcessThisFlippinFile {
    @Test public void thing() {
        File file = new File(System.getProperty("user.home") + "\\ChurchStreamer\\preferences\\UIPreferences.txt");
        File f = new File(file.getAbsolutePath());
        System.out.println(f);
        System.out.println(String.valueOf(f.exists()));

    }
}
