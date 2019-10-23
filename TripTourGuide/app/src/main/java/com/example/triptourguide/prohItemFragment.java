package com.example.triptourguide;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class prohItemFragment extends Fragment {

    GridView ProbItemGridView;
    private Map<String, Set<String>> countrytoprohMap;
    private Set<String> choosenproh;


    public prohItemFragment((Map<String, Set<String>> countrytoprohMap, Set<String> choosenproh) {
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





        return inflater.inflate(R.layout.fragment_proh_item, container, false);
    }

}
