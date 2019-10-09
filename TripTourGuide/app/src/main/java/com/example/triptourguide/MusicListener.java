package com.example.triptourguide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MusicListener extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String DEVELOPER_KEY = "AIzaSyA25f1_VvgFd8z8ohCNW8A5jCzjHE9kbag";
    private static final String VIDEO_ID = "eKc-SxD0HZE";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    YouTubePlayerFragment myYouTubePlayerFragment;

    private static YouTube youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_listener);
        myYouTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager().findFragmentById(R.id.youtube_fragment);
        myYouTubePlayerFragment.initialize(DEVELOPER_KEY, this);


    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            List<String> playList = new ArrayList<>();

            Thread thread = new Thread(new YoutubeSearchRunner());
            thread.start();

            playList.add(VIDEO_ID);
            playList.add("yMsHSaFBAYw");
            youTubePlayer.cueVideos(playList);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)",
                    youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            getYouTubePlayerProvider().initialize(DEVELOPER_KEY, this);
        }
    }
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView)findViewById(R.id.youtube_fragment);
    }


    private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {

        System.out.println("\n=============================================================");
        System.out.println(
                "   First " + 10L + " videos for search on \"" + query + "\".");
        System.out.println("=============================================================\n");

        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) {

            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();

            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
            if (rId.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();

                System.out.println(" Video Id" + rId.getVideoId());
                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
                System.out.println(" Thumbnail: " + thumbnail.getUrl());
                System.out.println("\n-------------------------------------------------------------\n");
            }
        }
    }


    class YoutubeSearchRunner implements Runnable {


        private List<SearchResult> GetYoutubeSearch() {
            try {
                // This object is used to make YouTube Data API requests. The last
                // argument is required, but since we don't need anything
                // initialized when the HttpRequest is initialized, we override
                // the interface and provide a no-op function.

                HttpTransport transport = new NetHttpTransport();
                JsonFactory jsonFactory = new JacksonFactory();
                GoogleCredential credential = new GoogleCredential();

                youtube = new YouTube.Builder(transport, jsonFactory, credential).setApplicationName("youtube-cmdline-search-sample").build();

                // Prompt the user to enter a query term.
                String queryTerm = "after you";

                // Define the API request for retrieving search results.
                YouTube.Search.List search = youtube.search().list("id,snippet");

                // Set your developer key from the {{ Google Cloud Console }} for
                // non-authenticated requests. See:
                // {{ https://cloud.google.com/console }}
                search.setKey(DEVELOPER_KEY);
                search.setQ(queryTerm);

                // Restrict the search results to only include videos. See:
                // https://developers.google.com/youtube/v3/docs/search/list#type
                search.setType("video");

                // To increase efficiency, only retrieve the fields that the
                // application uses.
                search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
                search.setMaxResults(10L);



                // Call the API and print results.
                SearchListResponse searchResponse = search.execute();
                List<SearchResult> searchResultList = searchResponse.getItems();
                if (searchResultList != null) {
                    prettyPrint(searchResultList.iterator(), queryTerm);
                    return searchResultList;
                }
            } catch (GoogleJsonResponseException e) {
                System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                        + e.getDetails().getMessage());
            } catch (IOException e) {
                System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return null;
        }

        @Override
        public void run() {
            GetYoutubeSearch();
        }
    }

}
