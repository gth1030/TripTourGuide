package com.example.triptourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TripSettings extends AppCompatActivity {


    CountryCodePicker ccp;
    Spinner statespinner;
    Spinner cityspinner;
    Spinner activityspinner;
    TextView text3;
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

    private String getJson1String()
    {
        String json = "";

        try {
            InputStream is = getAssets().open("CityNames.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return json;
    }

    private String getJson2String()
    {
        String json = "";

        try {
            InputStream is = getAssets().open("CityActivityList.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_settings);

        _context = this;
        statedata = new String[0];
        citydata = new String[0];
        activitydata = new String[0];
        ccp = findViewById(R.id.ccp);
        text3 = findViewById(R.id.textView3);
        statespinner = findViewById(R.id.statespinner);
        ArrayAdapter<String> stateadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statedata);
        statespinner.setAdapter(stateadapter);

        cityspinner = findViewById(R.id.cityspinner);
        ArrayAdapter<String> cityadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, citydata);
        cityspinner.setAdapter(cityadapter);

        StateListener stateListener = new StateListener();
        statespinner.setOnItemSelectedListener(stateListener);

        activityspinner = findViewById(R.id.activityspinner);
        ArrayAdapter<String> activitiyadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, activitydata);
        activityspinner.setAdapter(activitiyadapter);

        CityListener cityListener = new CityListener();
        cityspinner.setOnItemSelectedListener(cityListener);

        try {
            JSONArray countryCityNamesJson = new JSONArray(getJson1String());
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
            JSONArray CityActivityJson = new JSONArray(getJson2String());
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
                text3.setText(ccp.getSelectedCountryName());
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
            ArrayAdapter<String> cityadapter = new ArrayAdapter<>(_context, android.R.layout.simple_spinner_item, citydata);
            cityspinner.setAdapter(cityadapter);
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

            activitydata = selectedactivities.toArray(new String[selectedactivities.size()]);
            ArrayAdapter<String> activityadapter = new ArrayAdapter<>(_context, android.R.layout.simple_spinner_item, activitydata);
            activityspinner.setAdapter(activityadapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
