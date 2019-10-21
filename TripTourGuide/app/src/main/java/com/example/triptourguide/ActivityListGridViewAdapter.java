package com.example.triptourguide;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityListGridViewAdapter extends BaseAdapter {

    private Context _context;
    private List<String> _activityList;
    public Boolean[] GridViewSelection;

    Map<String, Integer> activityToImageIdMap;

    public ActivityListGridViewAdapter(Context context, List<String> activityList) {
        _context = context;
        _activityList = activityList;
        setActivityToImageIdMap();
        GridViewSelection = new Boolean[_activityList.size()];
        for (int i = 0; i < GridViewSelection.length; i++)
            GridViewSelection[i] = false;
    }

    public void ResetActivityList(List<String> activityList) {
        _activityList = activityList;
        GridViewSelection = new Boolean[_activityList.size()];
        for (int i = 0; i < GridViewSelection.length; i++)
            GridViewSelection[i] = false;
    }

    @Override
    public int getCount() {
        return _activityList.size();
    }

    @Override
    public Object getItem(int position) {
        return _activityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(_context);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        if (!activityToImageIdMap.containsKey(_activityList.get(position))) {
            imageView.setImageResource(R.drawable.ic_launcher_background);
            return imageView;
        }

        imageView.setImageResource(activityToImageIdMap.get(_activityList.get(position)));
        imageView.setBackgroundColor(GridViewSelection[position] ? Color.BLACK : Color.GRAY);
        return imageView;
    }

    private void setActivityToImageIdMap() {
        activityToImageIdMap = new HashMap<>();
        activityToImageIdMap.put("city", R.drawable.building_50);
        activityToImageIdMap.put("cycle", R.drawable.cycle_50);
        activityToImageIdMap.put("sail", R.drawable.sail_50);
        activityToImageIdMap.put("snorkel", R.drawable.snorkel_50);
    }


}

