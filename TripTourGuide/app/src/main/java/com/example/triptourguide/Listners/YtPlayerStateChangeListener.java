package com.example.triptourguide.Listners;

import android.app.Activity;
import android.graphics.Color;
import android.widget.ListView;
import android.widget.TextView;

import com.example.triptourguide.R;
import com.google.android.youtube.player.YouTubePlayer;

import java.util.List;

public class YtPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

    private ListView _musicListView;
    private boolean initialPlay = true;

    public YtPlayerStateChangeListener(ListView musicListView) {
        _musicListView = musicListView;
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {
        if (initialPlay) {
            _musicListView.getChildAt(0).findViewById(R.id.music_title).setBackgroundColor(Color.RED);
            YtListener.setUpFadeAnimation((TextView) _musicListView.getChildAt(0).findViewById(R.id.music_title), 0);
        }
        initialPlay = false;
    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }
}
