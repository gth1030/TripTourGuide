package com.example.triptourguide.Fragments;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.triptourguide.Listners.YtPlayerStateChangeListener;
import com.example.triptourguide.MusicRankCollector;
import com.example.triptourguide.R;
import com.example.triptourguide.YtListener;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeFragmentX extends Fragment {


    public YoutubeFragmentX() {

    }


    private static final String API_KEY = "AIzaSyAg1hkaFMgL6Jh9ankdeJ58Hl0b54qOCI8";

    private static String[] youTubeList = new String[]{"P00HMxdsVZI", "Pkh8UtuejGw", "u1yVCeXYya4", "1XzY2ij_vL4" };
    private static String[] songList = new String[]{"Truth Hurts", "Senorita", "Someone You Loved", "Ran$om"};

    private boolean _apiCallAllowed = false;


    private ListView musicNameListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_youtube_fragment_x, container, false);
        musicNameListView = rootView.findViewById(R.id.MusicTitleList);

        YouTubePlayerSupportFragmentX youTubePlayerFragment = YouTubePlayerSupportFragmentX.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {

                    Thread thread = new Thread(new YoutubeSearchRunner(getActivity(), player, musicNameListView));
                    thread.start();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                String errorMessage = error.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });

        return rootView;
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

                YouTube youtube = new YouTube.Builder(transport, jsonFactory, credential).setApplicationName("youtube-cmdline-search-sample").build();

                // Define the API request for retrieving search results.
                YouTube.Search.List search = youtube.search().list("id,snippet");

                search.setKey(API_KEY);
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

            // TODO update _apiCallAllowed to true to get network result! **Google api call limits how many called can be from given api-key
            // for testing purpose is limited
            if (_apiCallAllowed) {

                for (int i = 0; i < rankedMusicNameList.size(); i++) {
                    String songName = rankedMusicNameList.get(i);
                    List<SearchResult> searchResults = GetYoutubeSearch(songName);
                    if (!searchResults.isEmpty()) {
                        youTubeIdList.add(searchResults.get(0).getId().getVideoId());
                        playableMusicNameList.add(rankedMusicNameList.get(i));
                    }
                }
            } else {
                for (String a : youTubeList)
                    youTubeIdList.add(a);
                for (String a : songList)
                    playableMusicNameList.add(a);
            }


            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(_context, R.layout.list_view_layout, R.id.music_title, playableMusicNameList);
                    _musicListView.setAdapter(adapter);
                }
            });

            _youtubePlayer.setPlaylistEventListener(new YtListener(playableMusicNameList, _musicListView, _context));
            _youtubePlayer.setPlayerStateChangeListener(new YtPlayerStateChangeListener(playableMusicNameList, _musicListView, _context));

            _youtubePlayer.cueVideos(youTubeIdList);
            _youtubePlayer.play();

        }
    }


}
