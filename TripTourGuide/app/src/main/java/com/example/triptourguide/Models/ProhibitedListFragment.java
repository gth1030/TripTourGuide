package com.example.triptourguide.Models;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.triptourguide.ProhibitedListGridViewAdapter;
import com.example.triptourguide.R;
import com.example.triptourguide.TravelItemProvider;
import com.example.triptourguide.TripUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProhibitedListFragment extends Fragment {

    GridView prohibitedListGridView;
    List<String> selectedprohibited;
    Map<String, List<String>> prohibitedItem = new HashMap<>();
    Context _context;


    public ProhibitedListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            JSONArray ProhibitedListJson = new JSONArray(TripUtils.ReadFileFromAsset(_context, "CountryProhibited.json"));
            for (int i = 0; i < ProhibitedListJson.length(); i++) {

                JSONObject country = ProhibitedListJson.getJSONObject(i);
                String countryname = country.getString("name");
                JSONArray prohibitedListArr = country.getJSONArray("prohibited");
                List<String> prohibitedList = new ArrayList<>();
                for (int j = 0; j < prohibitedListArr.length(); j++) {
                    prohibitedList.add(prohibitedListArr.getString(j));
                }
                prohibitedItem.put(countryname, prohibitedList);
            }

        } catch (JSONException e) {
            Log.d("error found", "error found");
        }

        // Inflate the layout for this fragment
        String country = "US";
        selectedprohibited  = prohibitedItem.get(country);
        prohibitedListGridView = (GridView)getView().findViewById(R.id.prohibitedListGridView);
        ProhibitedListGridViewAdapter adapter = new ProhibitedListGridViewAdapter(_context, selectedprohibited);
        prohibitedListGridView.setAdapter(adapter);
        prohibitedListGridView.setOnItemClickListener(new TravelItemProvider.ProhibitedListGridListener(adapter));

        return inflater.inflate(R.layout.fragment_prohibited_list, container, false);
    }

    class ProhibitedListGridListener implements AdapterView.OnItemClickListener {

        ProhibitedListGridViewAdapter _adapter;

        public ProhibitedListGridListener(ProhibitedListGridViewAdapter adapter) {
            _adapter = adapter;
        }

        public void ResetAdapter(ProhibitedListGridViewAdapter adapter){
            _adapter = adapter;
        }


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (_adapter.GridViewSelection[position]) {
                _adapter.GridViewSelection[position] = false;
                view.setBackgroundColor(Color.WHITE);
            } else {
                _adapter.GridViewSelection[position] = true;
                view.setBackgroundColor(Color.BLACK);
            }
            getActivity().runOnUiThread(){
                @Override
                public void run() {
                    prohibitedListGridView.setAdapter(_adapter);
                }
            });
        }
    }

}
