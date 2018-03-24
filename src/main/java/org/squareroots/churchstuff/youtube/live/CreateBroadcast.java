/*
 * Copyright (c) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.squareroots.churchstuff.youtube.live;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.DateTime;
import org.squareroots.churchstuff.youtube.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Use the YouTube Live Streaming API to insert a broadcast and a stream
 * and then bind them together. Use OAuth 2.0 to authorize the API requests.
 *
 * @author Ibrahim Ulukaya
 */
public class CreateBroadcast {

    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;
    private LiveBroadcast liveBroadcast;
    private LiveStream liveStream;

    /**
     * Create and insert a liveBroadcast resource.
     */
    public void Go(String broadcastTitle, boolean isPublic) {

        // This OAuth 2.0 access scope allows for full read/write access to the
        // authenticated user's account.
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "createbroadcast");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("Church Streamer").build();

            // Create a snippet with the title and scheduled start and end
            // times for the broadcast. Currently, those times are hard-coded.
            LiveBroadcastSnippet broadcastSnippet = new LiveBroadcastSnippet();
            broadcastSnippet.setTitle(broadcastTitle);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime plusSeconds = now.plusSeconds(5);
            LocalDateTime plusMinutes = now.plusMinutes(20);
            Date startTime = Date.from(plusSeconds.atZone(ZoneId.systemDefault()).toInstant());
            Date endTime= Date.from(plusMinutes.atZone(ZoneId.systemDefault()).toInstant());
            broadcastSnippet.setScheduledStartTime(new DateTime(startTime));

            // Set the broadcast's privacy status to "private". See:
            // https://developers.google.com/youtube/v3/live/docs/liveBroadcasts#status.privacyStatus
            LiveBroadcastStatus status = new LiveBroadcastStatus();
            status.setPrivacyStatus(isPublic? "public":"private");

            LiveBroadcast broadcast = new LiveBroadcast();
            broadcast.setKind("youtube#liveBroadcast");
            broadcast.setSnippet(broadcastSnippet);
            broadcast.setStatus(status);

            LiveBroadcastContentDetails lbcd = new LiveBroadcastContentDetails();
            MonitorStreamInfo monitorStreamInfo = new MonitorStreamInfo();
            monitorStreamInfo.setEnableMonitorStream(false);
            lbcd.setMonitorStream(monitorStreamInfo);
            broadcast.setContentDetails(lbcd);

            // Construct and execute the API request to insert the broadcast.
            YouTube.LiveBroadcasts.Insert liveBroadcastInsert =
                    youtube.liveBroadcasts().insert("snippet,status,contentDetails", broadcast);
            LiveBroadcast returnedBroadcast = liveBroadcastInsert.execute();

            // Print information from the API response.
            System.out.println("\n================== Returned Broadcast ==================\n");
            System.out.println("  - Id: " + returnedBroadcast.getId());
            System.out.println("  - Title: " + returnedBroadcast.getSnippet().getTitle());
            System.out.println("  - Description: " + returnedBroadcast.getSnippet().getDescription());
            System.out.println("  - Published At: " + returnedBroadcast.getSnippet().getPublishedAt());
            System.out.println(
                    "  - Scheduled Start Time: " + returnedBroadcast.getSnippet().getScheduledStartTime());
            System.out.println(
                    "  - Scheduled End Time: " + returnedBroadcast.getSnippet().getScheduledEndTime());

            liveStream = GetExistingStream();
            // Prompt the user to enter a title for the video stream.
            String title = getStreamTitle();
            LiveStream returnedStream;
            if (liveStream == null) {
                // Create a snippet with the video stream's title.
                LiveStreamSnippet streamSnippet = new LiveStreamSnippet();
                streamSnippet.setTitle(title);
                // Define the content distribution network settings for the
                // video stream. The settings specify the stream's format and
                // ingestion type. See:
                // https://developers.google.com/youtube/v3/live/docs/liveStreams#cdn
                CdnSettings cdnSettings = new CdnSettings();
                cdnSettings.setFormat("720p");
                cdnSettings.setIngestionType("rtmp");

                liveStream = new LiveStream();
                liveStream.setKind("youtube#liveStream");
                liveStream.setSnippet(streamSnippet);
                liveStream.setCdn(cdnSettings);
                YouTube.LiveStreams.Insert liveStreamInsert =
                        youtube.liveStreams().insert("snippet,cdn", liveStream);
                returnedStream = liveStreamInsert.execute();
            }
            else
            {
                returnedStream = liveStream;
            }
                // Print information from the API response.
                System.out.println("\n================== Returned Stream ==================\n");
                System.out.println("  - Id: " + returnedStream.getId());
                System.out.println("  - Title: " + returnedStream.getSnippet().getTitle());
                System.out.println("  - Description: " + returnedStream.getSnippet().getDescription());
                System.out.println("  - Published At: " + returnedStream.getSnippet().getPublishedAt());
            // Construct and execute a request to bind the new broadcast
            // and stream.
            YouTube.LiveBroadcasts.Bind liveBroadcastBind =
                    youtube.liveBroadcasts().bind(returnedBroadcast.getId(), "id,contentDetails");
            liveBroadcastBind.setStreamId(returnedStream.getId());
            returnedBroadcast = liveBroadcastBind.execute();
            returnedBroadcast.getContentDetails().set("enableMonitorStream", false);
            liveBroadcast = returnedBroadcast;

            // Print information from the API response.
            System.out.println("\n================== Returned Bound Broadcast ==================\n");
            System.out.println("  - Broadcast Id: " + returnedBroadcast.getId());
            System.out.println(
                    "  - Bound Stream Id: " + returnedBroadcast.getContentDetails().getBoundStreamId());

        } catch (GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
    }

    private LiveStream GetExistingStream() throws IOException {
        // Create a request to list liveStream resources.
        YouTube.LiveStreams.List livestreamRequest = youtube.liveStreams().list("id,snippet,cdn");

        // Modify results to only return the user's streams.
        livestreamRequest.setMine(true);

        // Execute the API request and return the list of streams.
        LiveStreamListResponse returnedListResponse = livestreamRequest.execute();
        List<LiveStream> returnedList = returnedListResponse.getItems();
        for(LiveStream stream: returnedList)
        {
            if(stream.getSnippet().getTitle().equals(getStreamTitle())) {
                return stream;
            }
        }
        return null;
    }

    public void WaitForActive() throws IOException, InterruptedException {
        YouTube.LiveStreams.List liveStreamRequest = youtube.liveStreams().list("id,status").setId(liveBroadcast.getContentDetails().getBoundStreamId());
        LiveStreamListResponse returnedList = liveStreamRequest.execute();
        List<LiveStream> liveStreams = returnedList.getItems();
        if (liveStreams != null && liveStreams.size() > 0) {
            LiveStream liveStream = liveStreams.get(0);
            if (liveStream != null)
                while (!liveStream.getStatus().getStreamStatus()
                        .equals("active")) {
                    Thread.sleep(1000);
                    returnedList = liveStreamRequest.execute();
                    liveStreams = returnedList.getItems();
                    liveStream = liveStreams.get(0);
                }
        }
    }
    public void WaitForTesting() throws IOException, InterruptedException {
        YouTube.LiveStreams.List liveStreamRequest = youtube.liveStreams().list("id,status").setId(liveBroadcast.getContentDetails().getBoundStreamId());
        LiveStreamListResponse returnedList = liveStreamRequest.execute();
        List<LiveStream> liveStreams = returnedList.getItems();
        if (liveStreams != null && liveStreams.size() > 0) {
            LiveStream liveStream = liveStreams.get(0);
            if (liveStream != null)
                while (!liveStream.getStatus().getStreamStatus().equals("testing") && !liveStream.getStatus().getStreamStatus().equals("active")) {
                    Thread.sleep(1000);
                    returnedList = liveStreamRequest.execute();
                    liveStreams = returnedList.getItems();
                    liveStream = liveStreams.get(0);
                }
        }
    }

    public void TransitionToDone() throws IOException {
        YouTube.LiveBroadcasts.Transition transitionRequest = youtube.liveBroadcasts().transition(
                "complete", liveBroadcast.getId(), "status");
        transitionRequest.execute();
    }

    public void TransitionToLive() throws IOException {
        YouTube.LiveBroadcasts.Transition transitionRequest = youtube.liveBroadcasts().transition(
                "live", liveBroadcast.getId(), "status");
        transitionRequest.execute();
    }

    /*
     * Prompt the user to enter a title for a stream.
     */
    private static String getStreamTitle(){

        String title = "BethlehemLutheranChurchLiveStream";
        return title;
    }

}
