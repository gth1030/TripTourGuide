package com.example.triptourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TripSettings extends AppCompatActivity {


    CountryCodePicker ccp;
    TextView text3;
    Map<String, List<Map<String, List<String>>>> countryToState = new HashMap<>();


    private String getJsonString()
    {
        String json = "";

        try {
            InputStream is = getAssets().open("CityNames.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
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
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        text3 = findViewById(R.id.textView3);

        try {
            JSONArray countryCityNamesJson = new JSONArray(getJsonString());
            for (int i = 0; i < countryCityNamesJson.length(); i++) {

                JSONObject country = countryCityNamesJson.getJSONObject(i);
                String countryName = country.getString("name");
                JSONObject state = country.getJSONObject("states");
                Iterator<String> keys = state.keys();

                countryToState.put(countryName, new ArrayList<Map<String, List<String>>>());

                while(keys.hasNext()) {
                    Map<String, List<String>> stateMap = new HashMap<>();
                    countryToState.get(countryName).add(stateMap);
                    String stateName = keys.next();
                    JSONArray cities = state.getJSONArray(stateName);
                    List<String> cityList = new ArrayList<>();
                    for (int j = 0; j < cities.length(); j++) {
                        cityList.add(cities.getString(j));
                    }
                    stateMap.put(stateName, cityList);
                }

            }

            String a = "";

        } catch (JSONException e) {
            Log.d("error found", "error found");
        }


        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
                public void onCountrySelected() {
            }
        });


    }



}
