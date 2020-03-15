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
import java.util.Date;
import java.util.List;

public class CreateBroadcast {

    private static YouTube youtube;

    public void updateYT(LiveBroadcast broadcast) {
        try {
            // Authorize the request.
            Credential credential = Auth.authorize(Lists.newArrayList("https://www.googleapis.com/auth/youtube"), "createbroadcast");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("Church Streamer").build();

            populateBulletin(broadcast.getSnippet());

            YouTube.LiveBroadcasts.List liveStreamRequest = youtube.liveBroadcasts().list("id,status,snippet").setMine(true).setBroadcastType("persistent");
            LiveBroadcastListResponse returnedList = liveStreamRequest.execute();
            //Just a conversion
            List<LiveBroadcast> liveBroadcasts = returnedList.getItems();

            if (liveBroadcasts != null && liveBroadcasts.size() > 0) {
                LiveBroadcast liveBroadcast = liveBroadcasts.get(0);
                if (liveBroadcast != null) {
                    setIt(broadcast.setId(liveBroadcast.getId()));
                }
            }


        }
        catch (Exception e) {e.printStackTrace();}
    }

    private void setIt(LiveBroadcast broadcast) {

        try {

            YouTube.LiveBroadcasts.Update request;
            request = youtube.liveBroadcasts()
                    .update("snippet", broadcast);
            LiveBroadcast response = request.execute();
            System.out.println("=========================RESPONSE===============================");
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void populateBulletin(LiveBroadcastSnippet broadcastSnippet) {
        CommandLineHandler.getURL(new OnBulletinRecieved() {
            @Override
            public void doOnSuccess(String url) {
                broadcastSnippet.setDescription("We are Bethlehem Lutheran Church, in Fairborn, OH. \n" +
                        "Website: https://bethlehem7.org . \n" +
                        "This service's bulletin: " + url +
                        "\n Thank you for watching our services." +
                        "Matthew 28:19 :  Therefore go and make" +
                        " disciples of all nations, baptizing them " +
                        "in the name of the Father and of the Son and of the Holy Spirit");
            }

            @Override
            public void doOnFailure() {
                broadcastSnippet.setDescription("We are Bethlehem Lutheran Church, in Fairborn, OH. \n" +
                        "Website: https://bethlehem7.org . \n" +
                        "Thank you for watching our services. \n" +
                        "Matthew 28:19 :  Therefore go and make " +
                        "disciples of all nations, baptizing them " +
                        "in the name of the Father and of the Son and of the Holy Spirit");
            }
        });

    }

}
