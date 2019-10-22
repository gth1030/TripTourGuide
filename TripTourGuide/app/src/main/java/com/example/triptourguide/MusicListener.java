package com.example.triptourguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.triptourguide.Listners.YtPlayerStateChangeListner;
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
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.client.http.HttpTransport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MusicListener extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String DEVELOPER_KEY = "AIzaSyAg1hkaFMgL6Jh9ankdeJ58Hl0b54qOCI8";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    YouTubePlayerFragment myYouTubePlayerFragment;

    ListView musicNameListView;

    private static YouTube youtube;

    private static String[] youTubeList = new String[]{"P00HMxdsVZI", "Pkh8UtuejGw", "u1yVCeXYya4", "1XzY2ij_vL4" };
    private static String[] songList = new String[]{"Truth Hurts", "Senorita", "Someone You Loved", "Ran$om"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.content_trip_option_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_listener);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        musicNameListView = findViewById(R.id.MusicNameList);

        myYouTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager().findFragmentById(R.id.youtube_fragment);
        myYouTubePlayerFragment.initialize(DEVELOPER_KEY, this);


    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            Thread thread = new Thread(new YoutubeSearchRunner(this, youTubePlayer, musicNameListView));
            thread.start();
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


    protected YouTubePlayerView getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_fragment);
    }



    class YoutubeSearchRunner implements Runnable {

        private YouTubePlayer _youtubePlayer;

        ListView _musicListView;
        Activity _context;

        public YoutubeSearchRunner(Activity context, YouTubePlayer youTubePlayer, ListView musicListView) {
            _context = context;
            _youtubePlayer = youTubePlayer;
            _musicListView = musicListView;
        }


        private List<SearchResult> GetYoutubeSearch(String searchTerm) {
            try {

                HttpTransport transport = new NetHttpTransport();
                JsonFactory jsonFactory = new JacksonFactory();
                GoogleCredential credential = new GoogleCredential();

                youtube = new YouTube.Builder(transport, jsonFactory, credential).setApplicationName("youtube-cmdline-search-sample").build();

                // Define the API request for retrieving search results.
                YouTube.Search.List search = youtube.search().list("id,snippet");

                search.setKey(DEVELOPER_KEY);
                search.setQ(searchTerm);

                // Restrict the search results to only include videos. See:
                search.setType("video");

                // To increase efficiency, only retrieve the fields that the
                // application uses.
                search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
                search.setMaxResults(1L);



                // Call the API and print results.
                SearchListResponse searchResponse = search.execute();
                List<SearchResult> searchResultList = searchResponse.getItems();
                if (searchResultList != null) {
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

            List<String> rankedMusicNameList= MusicRankCollector.GetCountryMusicRank("Canada");

            final List<String> youTubeIdList = new ArrayList<>();
            final List<String> playableMusicNameList = new ArrayList<>();

            // TODO unblock this for network test
            /*for (int i = 0; i < rankedMusicNameList.size(); i++) {
                String songName = rankedMusicNameList.get(i);
                List<SearchResult> searchResults = GetYoutubeSearch(songName);
                if (!searchResults.isEmpty()) {
                    youTubeIdList.add(searchResults.get(0).getId().getVideoId());
                    playableMusicNameList.add(rankedMusicNameList.get(i));
                }
            }*/


            for (String a : youTubeList)
                youTubeIdList.add(a);
            for (String a : songList)
                playableMusicNameList.add(a);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(_context, R.layout.list_view_layout, R.id.music_title, playableMusicNameList);
                    _musicListView.setAdapter(adapter);
                }
            });

            _youtubePlayer.setPlaylistEventListener(new YtListener(playableMusicNameList, _musicListView, _context));
            _youtubePlayer.setPlayerStateChangeListener(new YtPlayerStateChangeListner(playableMusicNameList, _musicListView, _context));

            _youtubePlayer.cueVideos(youTubeIdList);
            _youtubePlayer.play();

        }
    }





}
