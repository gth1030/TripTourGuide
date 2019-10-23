package com.example.triptourguide;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
class ProhItemFragment extends ListFragment {

    private List<String> data ;
    private String[] converteddata;

    public ProhItemFragment(List<String> list) {
        data = list;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proh_item2, container, false);
        ListAdapter listAdapter = new ListAdapter();
        ListView itemlist = (ListView)view.findViewById(R.id.itemlist);
        converteddata = data.toArray(new String[data.size()]);
        itemlist.setAdapter(listAdapter);


        return view;
    }

    class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return converteddata.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.row, null);

                TextView textview = (TextView)convertView.findViewById(R.id.textView);

                textview.setText(converteddata[position]);
            }
            return convertView;
        }
    }

}
