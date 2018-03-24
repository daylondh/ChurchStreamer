import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.squareroots.churchstuff.calendar.LiturgicalCalendar;
import org.squareroots.churchstuff.streamer.ObsHandler;
import org.squareroots.churchstuff.youtube.Auth;
import org.squareroots.churchstuff.youtube.live.CreateBroadcast;

import java.io.IOException;
import java.util.Calendar;

public class YoutubeTests {

    @Test
    public void SomeYouTubeTestIdunno()
    {
        // Set up before run
        CreateBroadcast cb = new CreateBroadcast();
        LiturgicalCalendar lc = new LiturgicalCalendar(2018);
        String title = lc.LookupByDate(Calendar.MARCH, 23);

        // This stuff happens when you click "start"
        cb.Go(title, false);
        ObsHandler handler = new ObsHandler();
        handler.start();
        try {
            cb.WaitForActive();
            cb.TransitionToLive();


            Thread.sleep(30000);

            // This stuff happens when you click "stop"
            handler.stop();
            cb.TransitionToDone();

            // and now everything's done!
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}