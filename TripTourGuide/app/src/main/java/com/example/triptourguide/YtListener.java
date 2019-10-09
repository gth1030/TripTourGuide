package com.example.triptourguide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;

import java.util.List;

public class YtListener implements YouTubePlayer.PlaylistEventListener {

    private List<String> _musicNameList;
    private ListView _musicListView;
    private Activity _context;
    private int _currentInd = 0;
    private int _backGroundColor = Color.parseColor("#804A98CC");

    public YtListener(List<String> musicNameList, ListView musicListView, Activity context) {
        _musicNameList = musicNameList;
        _musicListView = musicListView;
        _context = context;
    }

    @Override
    public void onPrevious() {
        _currentInd = Math.max(0, _currentInd - 1);
        UpdateMusicListVIew();
    }

    @Override
    public void onNext() {
        _currentInd = Math.min(_musicNameList.size(), _currentInd + 1);
        UpdateMusicListVIew();
    }

    @Override
    public void onPlaylistEnded() {

    }

    private void UpdateMusicListVIew() {
        _context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView v = _musicListView.getChildAt(_currentInd).findViewById(R.id.textView);
                v.setBackgroundColor(Color.RED);
                setUpFadeAnimation(v);
                if (_currentInd != 0) {
                    TextView vp = _musicListView.getChildAt(_currentInd - 1).findViewById(R.id.textView);
                    vp.setBackgroundColor(_backGroundColor);
                    vp.clearAnimation();
                }
                if (_currentInd < _musicNameList.size() - 1) {
                    TextView vp = _musicListView.getChildAt(_currentInd + 1).findViewById(R.id.textView);
                    vp.setBackgroundColor(_backGroundColor);
                    vp.clearAnimation();
                }
            }
        });
    }


    private void setUpFadeAnimation(final TextView textView) {
        // Start from 0.1f if you desire 90% fade animation
        final Animation fadeIn = new AlphaAnimation(0.3f, 1.0f);
        fadeIn.setDuration(1000);
        fadeIn.setStartOffset(100);
        // End to 0.1f if you desire 90% fade animation
        final Animation fadeOut = new AlphaAnimation(1.0f, 0.3f);
        fadeOut.setDuration(1000);
        fadeOut.setStartOffset(100);

        fadeIn.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0) {
                // start fadeOut when fadeIn ends (continue)
                textView.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        fadeOut.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0) {
                // start fadeIn when fadeOut ends (repeat)
                textView.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        textView.startAnimation(fadeOut);
    }

}
