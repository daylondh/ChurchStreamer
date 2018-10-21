import org.junit.Test;
import org.squareroots.churchstuff.youtube.data.Upload;

import java.io.IOException;

public class UploadToYT {
    Upload upload = new Upload();
    String path = "D:\\Users\\Alex Hooper\\Videos\\Molly.flv";
    @Test
    public void uploadVideo() {
        try {
            upload.start(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
