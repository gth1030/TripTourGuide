package com.example.triptourguide.Listners;

import android.app.Activity;
import android.graphics.Color;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ListView;
import android.widget.TextView;

import com.example.triptourguide.R;
import com.google.android.youtube.player.YouTubePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YtListener implements YouTubePlayer.PlaylistEventListener {

    private List<String> _musicNameList;
    private ListView _musicListView;
    private Activity _context;
    private int _currentInd = 0;
    private int _backGroundColor = Color.parseColor("#804A98CC");
    private static Boolean[] isAnimationPlayable;

    public YtListener(List<String> musicNameList, ListView musicListView, Activity context) {
        _musicNameList = musicNameList;
        _musicListView = musicListView;
        _context = context;
        isAnimationPlayable = new Boolean[musicNameList.size()];
        Arrays.fill(isAnimationPlayable, false);
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
                TextView v = _musicListView.getChildAt(_currentInd).findViewById(R.id.music_title);
                v.setBackgroundColor(Color.RED);
                isAnimationPlayable[_currentInd] = true;
                setUpFadeAnimation(v, _currentInd);
                if (_currentInd != 0) {
                    isAnimationPlayable[_currentInd - 1] = false;
                    TextView vp = _musicListView.getChildAt(_currentInd - 1).findViewById(R.id.music_title);
                    vp.setBackgroundColor(_backGroundColor);
                    vp.clearAnimation();
                }
                if (_currentInd < _musicNameList.size() - 1) {
                    isAnimationPlayable[_currentInd + 1] = false;
                    TextView vp = _musicListView.getChildAt(_currentInd + 1).findViewById(R.id.music_title);
                    vp.setBackgroundColor(_backGroundColor);
                    vp.clearAnimation();
                }
            }
        });
    }


    public static void setUpFadeAnimation(final TextView textView, final int viewInd) {
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
                if (isAnimationPlayable[viewInd])
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
                if (isAnimationPlayable[viewInd])
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
