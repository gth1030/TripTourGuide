package com.example.triptourguide;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class prohItemFragment extends Fragment {

    static GridView ProbItemGridView;
    private Map<String, Set<String>> countrytoprohMap;
    private Set<String> choosenproh;
    Map<String, List<String>> prohibiteditem = new HashMap<>();
    List<String> selectedprohibited;
    Context _context;


    public prohItemFragment(Map<String, Set<String>> countrytoprohMap, Set<String> choosenproh) {
        countrytoprohMap = countrytoprohMap;
        choosenproh = choosenproh;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_proh_item, container, false);
        ProbItemGridView = (GridView)getView().findViewById(R.id.ProhItemGridView);
        // Inflate the layout for this fragment

        Set<String> items = new HashSet<>();
        for (String country : choosenproh) {
            if (countrytoprohMap.containsKey(country))
                items.addAll(countrytoprohMap.get(country));
        }

        try {
            JSONArray ProhibitedListJson = new JSONArray(TripUtils.ReadFileFromAsset(getActivity(), "CountryProhibited.json"));
            for (int i = 0; i < ProhibitedListJson.length(); i++) {

                JSONObject country = ProhibitedListJson.getJSONObject(i);
                String countryname = country.getString("name");
                JSONArray prohibitedListArr = country.getJSONArray("prohibited");
                List<String> prohibitedList = new ArrayList<>();
                for (int j = 0; j < prohibitedListArr.length(); j++) {
                    prohibitedList.add(prohibitedListArr.getString(j));
                }
                prohibiteditem.put(countryname, prohibitedList);
            }

        } catch (JSONException e) {
            Log.d("error found", "error found");
        }

        String country = "US";
        selectedprohibited  = prohibiteditem.get(country);
        ProbItemGridView = (GridView)getView().findViewById(R.id.ProhItemGridView);
        com.example.triptourguide.ProhibitedListGridViewAdapter adapter = new com.example.triptourguide.ProhibitedListGridViewAdapter(getActivity(), selectedprohibited);
        ProbItemGridView.setAdapter(adapter);
        ProbItemGridView.setOnItemClickListener(new ProhibitedListGridListener(adapter));





        return inflater.inflate(R.layout.fragment_proh_item, container, false);
    }

    public static class ProhibitedListGridListener implements AdapterView.OnItemClickListener {

        com.example.triptourguide.ProhibitedListGridViewAdapter _adapter;

        public ProhibitedListGridListener(com.example.triptourguide.ProhibitedListGridViewAdapter adapter) {
            _adapter = adapter;
        }

        public void ResetAdapter(com.example.triptourguide.ProhibitedListGridViewAdapter adapter){
            _adapter = adapter;
        }


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }

    }

}
