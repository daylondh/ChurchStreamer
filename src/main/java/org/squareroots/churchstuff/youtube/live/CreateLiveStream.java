package org.squareroots.churchstuff.youtube.live;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;

public class CreateLiveStream {

    public LiveStream Create(YouTube youtubeService)
    {
        YouTube.LiveStreams.List request = null;
        try {
            request = youtubeService.liveStreams()
                    .list("snippet,cdn,contentDetails,status");
            LiveStreamListResponse response = request.setMine(true).execute();
            if(!response.isEmpty() && response.getItems().size() >1) {
                return response.getItems().stream().filter(r-> r.getSnippet().getTitle().contains("Church")).findFirst().get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Define the LiveStream object, which will be uploaded as the request body.
        LiveStream liveStream = new LiveStream();

        // Add the cdn object property to the LiveStream object.
        CdnSettings cdn = new CdnSettings();
        cdn.setFrameRate("60fps");
        cdn.setIngestionType("rtmp");
        cdn.setResolution("720p");
        liveStream.setCdn(cdn);

        // Add the contentDetails object property to the LiveStream object.
        LiveStreamContentDetails contentDetails = new LiveStreamContentDetails();
        contentDetails.setIsReusable(true);
        liveStream.setContentDetails(contentDetails);

        // Add the snippet object property to the LiveStream object.
        LiveStreamSnippet snippet = new LiveStreamSnippet();
        snippet.setTitle("Church Livestream");
        liveStream.setSnippet(snippet);

        // Define and execute the API request
        YouTube.LiveStreams.Insert request2 = null;
        try {
            request2 = youtubeService.liveStreams()
                    .insert("snippet,cdn,contentDetails,status", liveStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LiveStream response = null;
        try {
            response = request2.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liveStream;
    }
}
