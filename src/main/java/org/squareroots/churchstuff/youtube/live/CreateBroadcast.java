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
import org.squareroots.churchstuff.Bulletins.CommandLineHandler;
import org.squareroots.churchstuff.Bulletins.OnBulletinRecieved;
import org.squareroots.churchstuff.youtube.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class CreateBroadcast {

    private static YouTube youtube;
    private CreateLiveStream cls = new CreateLiveStream();

    public LiveBroadcast updateYT(LiveBroadcast broadcast, String title) {
        try {
            // Authorize the request.
            Credential credential = Auth.authorize(Lists.newArrayList("https://www.googleapis.com/auth/youtube"), "createbroadcast");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("Church Streamer").build();

            LiveBroadcastContentDetails details = new LiveBroadcastContentDetails();
            details.setEnableClosedCaptions(true);
            details.setEnableContentEncryption(true);
            details.setEnableDvr(true);
            details.setEnableEmbed(true);
            details.setRecordFromStart(true);
            details.setEnableAutoStart(true);
            broadcast.setContentDetails(details);
            CreateLiveStream cls = new CreateLiveStream();
            LiveStream ls = cls.Create(youtube);
            LiveBroadcastSnippet snippet = new LiveBroadcastSnippet();
            populateBulletin(snippet);
            //snippet.setScheduledEndTime(null));
            snippet.setScheduledStartTime(new DateTime(System.currentTimeMillis() + 1000));
            snippet.setTitle(title);
            broadcast.setSnippet(snippet);
            // Add the status object property to the LiveBroadcast object.
            LiveBroadcastStatus status = new LiveBroadcastStatus();
            status.setPrivacyStatus("public");
            broadcast.setStatus(status);

            YouTube.LiveBroadcasts.Insert liveStreamRequest = youtube.liveBroadcasts().insert("snippet,contentDetails,status", broadcast);
            broadcast= liveStreamRequest.execute();

            YouTube.LiveBroadcasts.Bind request = youtube.liveBroadcasts().bind(broadcast.getId(), "snippet");
            broadcast = request.setStreamId(ls.getId()).execute();
            return broadcast;
        }
        catch (Exception e) {e.printStackTrace();}
        return null;
    }

    private void populateBulletin(LiveBroadcastSnippet broadcastSnippet) {
        broadcastSnippet.setDescription("We are Bethlehem Lutheran Church, in Fairborn, OH. \n" +
                "Website: https://bethlehem7.org . \n" +
                "This service's bulletin: " + createURL() +
                "\n Thank you for watching our services." +
                "Matthew 28:19 :  Therefore go and make" +
                " disciples of all nations, baptizing them " +
                "in the name of the Father and of the Son and of the Holy Spirit");

    }
    private static String createURL() {
        //Year, month, day
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yy");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        return "https://bulletins.churchstreamer.org?t="+  String.valueOf(System.currentTimeMillis() * .001) + "&d=" + date;
    }

    public YouTube getYoutube(){ return youtube;}

}
