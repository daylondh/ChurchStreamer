package org.squareroots.churchstuff.streamer;

import com.google.api.services.youtube.model.*;
import org.squareroots.churchstuff.youtube.live.CreateBroadcast;

import java.io.IOException;

public class StreamManager {

    private CreateBroadcast cb = new CreateBroadcast();
    private ObsHandler handler = new ObsHandler();

    String title;
    boolean isPublic = false;

    public void init(String title, boolean isPublic)
    {
        this.title = title;
        this.isPublic = isPublic;
    }

    public void StartStreaming()
    {
        cb.updateYT(new LiveBroadcast().setSnippet(new LiveBroadcastSnippet().setTitle(title)));
        handler.startStreaming();
      //  cb.activate();
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
    }
}

