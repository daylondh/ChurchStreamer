import org.junit.Test;
import org.squareroots.churchstuff.streamer.ObsHandler;

/**
 * Created by alexh on 3/1/2018.
 */
public class ObsTests {


    @Test
    public void ObsStartTest() {
        ObsHandler handler = new ObsHandler();
        handler.start();

    }
}
