package org.squareroots.churchstuff.streamer;

import org.squareroots.churchstuff.youtube.live.CreateBroadcast;

import java.io.IOException;

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

    public void StartStreaming()
    {
        cb.Go(title, isPublic);
        handler.startStreaming();
        try {
            cb.WaitForActive();
            cb.TransitionToLive();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void StartRecording()
    {
        handler.startRecording();

    }
    public void StopRecording()
    {
        // This stuff happens when you click "stopRecording"
        handler.stopRecording();

    }

    public void StopStreaming()
    {
        // This stuff happens when you click "StopStreaming"
        handler.StopStreaming();
        try {
            cb.TransitionToDone();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

