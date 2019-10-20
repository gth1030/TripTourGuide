package com.example.triptourguide;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;


public class YtFragment extends Fragment {
    private FragmentActivity myContext;

    private YouTubePlayer YPlayer;
    private static final String YoutubeDeveloperKey = "AIzaSyAg1hkaFMgL6Jh9ankdeJ58Hl0b54qOCI8";
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    public YtFragment() {
    }


    @Override
    public void onAttach(Activity activity) {

        if (activity instanceof FragmentActivity) {
            myContext = (FragmentActivity) activity;
        }

        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_yt, container, false);

        YouTubePlayerSupportFragmentX youTubePlayerFragment = YouTubePlayerSupportFragmentX.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(YoutubeDeveloperKey, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    YPlayer.setFullscreen(true);
                    YPlayer.loadVideo("P00HMxdsVZI");
                    YPlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                Log.d("error", "error encountered!!!!!!!!");

            }
        });
        return rootView;
    }




}
