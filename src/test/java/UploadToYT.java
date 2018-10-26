import org.junit.Test;
import org.squareroots.churchstuff.youtube.data.Upload;
import org.squareroots.churchstuff.youtube.data.UploadVideo;

public class UploadToYT {
    Upload upload = new Upload();
    String path = "C:\\Users\\Alex Hooper\\Videos\\Molly.flv";
    @Test
    public void uploadVideo() {
        try {
            UploadVideo.go(path, "Yet another test for the Upload Protocol.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
