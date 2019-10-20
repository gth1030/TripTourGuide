package com.example.triptourguide;

import android.content.Context;
import android.content.Intent;

import com.wx.wheelview.widget.WheelView;

public class TripNameClickedListener implements WheelView.OnWheelItemClickListener {

    private Context _context;

    public TripNameClickedListener(Context mainContext) {
        _context = mainContext;
    }

    @Override
    public void onItemClick(int position, Object o) {
        Intent intent = new Intent(_context, TripProviderActivity.class);
        _context.startActivity(intent);
    }
}
