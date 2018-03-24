package org.squareroots.churchstuff.streamer;

import org.squareroots.churchstuff.calendar.LiturgicalCalendar;
import org.squareroots.churchstuff.youtube.live.CreateBroadcast;

import java.io.IOException;
import java.util.Calendar;

public class StreamManager {

    CreateBroadcast cb = new CreateBroadcast();
    ObsHandler handler = new ObsHandler();
    String title;
    boolean isPublic = false;

    public void init(String title, boolean isPublic)
    {
        this.title = title;
        this.isPublic = isPublic;
    }

    public void Start()
    {
        cb.Go(title, isPublic);
        handler.start();
        try {
            cb.WaitForActive();
            cb.TransitionToLive();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Stop()
    {
        // This stuff happens when you click "stop"
        handler.stop();
        try {
            cb.TransitionToDone();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

