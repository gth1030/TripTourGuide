package com.example.triptourguide;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubePlayer;


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


        return rootView;
    }




}
