package com.example.triptourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TripSettings extends AppCompatActivity {


    CountryCodePicker ccp;
    Spinner statespinner;
    Spinner cityspinner;
    Map<String, Map<String, List<String>>> countryToState = new HashMap<>();
    Map<String, List<String>> cityactivity = new HashMap<>();
    String pickedcountry;
    String pickedstate;
    String pickedcity;
    List<String> selectedstate;
    List<String> selectedcity;
    List<String> selectedactivities;
    String[] statedata;
    String[] citydata;
    String[] activitydata;
    Context _context;

    GridView activityListGridView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_settings);

        _context = this;
        statedata = new String[0];
        citydata = new String[0];
        activitydata = new String[0];
        ccp = findViewById(R.id.ccp);
        statespinner = findViewById(R.id.statespinner);
        ArrayAdapter<String> stateadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statedata);
        statespinner.setAdapter(stateadapter);

        cityspinner = findViewById(R.id.cityspinner);
        ArrayAdapter<String> cityadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, citydata);
        cityspinner.setAdapter(cityadapter);

        StateListener stateListener = new StateListener();
        statespinner.setOnItemSelectedListener(stateListener);


        activityListGridView = findViewById(R.id.activity_list_grid);
        ActivityListGridViewAdapter adapter = new ActivityListGridViewAdapter(this, new ArrayList<String>());
        activityListGridView.setAdapter(adapter);
        activityListGridView.setOnItemClickListener(new ActivityListGridListner(adapter));

        CityListener cityListener = new CityListener();
        cityspinner.setOnItemSelectedListener(cityListener);

        try {
            JSONArray countryCityNamesJson = new JSONArray(TripUtils.ReadFileFromAsset(_context, "CityNames.json"));
            for (int i = 0; i < countryCityNamesJson.length(); i++) {

                JSONObject country = countryCityNamesJson.getJSONObject(i);
                String countryName = country.getString("name");

                JSONObject state = country.getJSONObject("states");

                Iterator<String> keys = state.keys();

                countryToState.put(countryName, new HashMap<String, List<String>>());

                while(keys.hasNext()) {
                    Map<String, List<String>> stateMap = countryToState.get(countryName);
                    String stateName = keys.next();
                    JSONArray cities = state.getJSONArray(stateName);
                    List<String> cityList = new ArrayList<>();
                    for (int j = 0; j < cities.length(); j++) {
                        cityList.add(cities.getString(j));
                    }
                    stateMap.put(stateName, cityList);
                }

            }

        } catch (JSONException e) {
            Log.d("error found", "error found");
        }

        try {
            JSONArray CityActivityJson = new JSONArray(TripUtils.ReadFileFromAsset(this, "CityActivityList.json"));
            for (int i = 0; i < CityActivityJson.length(); i++) {

                JSONObject city = CityActivityJson.getJSONObject(i);
                String cityname = city.getString("name");
                JSONArray cityListArr = city.getJSONArray("activityList");
                List<String> cityList = new ArrayList<>();
                for (int j = 0; j < cityListArr.length(); j++) {
                    cityList.add(cityListArr.getString(j));
                }
                cityactivity.put(cityname, cityList);
            }

        } catch (JSONException e) {
            Log.d("error found", "error found");
        }


        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                pickedcountry = ccp.getSelectedCountryName();
                selectedstate = new ArrayList<>();

                Set<String> statesSet = countryToState.get(pickedcountry).keySet();

                for (String state : statesSet) {
                    selectedstate.add(state);
                }
                statedata = selectedstate.toArray(new String[selectedstate.size()]);
                ArrayAdapter<String> stateadapter = new ArrayAdapter<>(_context, android.R.layout.simple_spinner_item, statedata);
                statespinner.setAdapter(stateadapter);
            }
        });


    }

    class StateListener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            pickedstate = (String) statespinner.getSelectedItem();
            selectedcity = new ArrayList<>();
            selectedcity = countryToState.get(pickedcountry).get(pickedstate);

            citydata = selectedcity.toArray(new String[selectedcity.size()]);
            ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(_context, android.R.layout.simple_spinner_item, citydata);
            cityspinner.setAdapter(cityAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class CityListener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            pickedcity = (String) cityspinner.getSelectedItem();
            selectedactivities = new ArrayList<>();
            if(cityactivity.get(pickedcity) == null)
                return;
            selectedactivities = cityactivity.get(pickedcity);

            ActivityListGridViewAdapter adapter = new ActivityListGridViewAdapter(_context, selectedactivities);
            ((ActivityListGridListner) activityListGridView.getOnItemClickListener()).ResetAdapter(adapter);
            activityListGridView.setAdapter(adapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class ActivityListGridListner implements AdapterView.OnItemClickListener {

        ActivityListGridViewAdapter _adapter;

        public ActivityListGridListner(ActivityListGridViewAdapter adapter) {
            _adapter = adapter;
        }

        public void ResetAdapter(ActivityListGridViewAdapter adapter){
            _adapter = adapter;
        }


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (_adapter.GridViewSelection[position]) {
                _adapter.GridViewSelection[position] = false;
                view.setBackgroundColor(Color.GRAY);
            } else {
                _adapter.GridViewSelection[position] = true;
                view.setBackgroundColor(Color.BLACK);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityListGridView.setAdapter(_adapter);
                }
            });
        }
    }

}
