package com.example.triptourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.triptourguide.Fragments.MarkedMapFragment;
import com.example.triptourguide.Fragments.PrepItemFragment;
import com.example.triptourguide.Fragments.YoutubeFragmentX;
import com.example.triptourguide.Models.CityTripEntity;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TripServiceProvider extends AppCompatActivity {

    public static Map<String, Set<String>> conditionToItemsMap = new HashMap<>();
    public static Set<String> chosenActivities;
    Map<String, List<String>> countryToItemsMap = new HashMap<>();
    List<String> list;

    private String getCountryItemJson(String countryName) {
        return TripUtils.ReadFileFromAsset(this, countryName + "Prepare.json");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_item_provider);
        String tripName = getIntent().getStringExtra("tripName");
        DBopenHelper dbHelper = new DBopenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<CityTripEntity> cityTripEntityList = dbHelper.RetrieveTripDetail(db, tripName);

        populateConditionToItemMap(cityTripEntityList);

        chosenActivities = new HashSet<>();
        chosenActivities.add("common");
        for (CityTripEntity cityTripEntity : cityTripEntityList)
            chosenActivities.addAll(cityTripEntity.ActivityList);

        Set<String> items = new HashSet<>();
        for (String condition : chosenActivities) {
            if (conditionToItemsMap.containsKey(condition))
                items.addAll(conditionToItemsMap.get(condition));
        }

        list = countryToItemsMap.get("US");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new PrepItemFragment(conditionToItemsMap, chosenActivities);
        ft.replace(R.id.item_provider_container, fragment);
        ft.commit();
    }


    private void populateConditionToItemMap(List<CityTripEntity> cityTripEntityList) {
        for (CityTripEntity cityTripEntity : cityTripEntityList) {
            try {
                JSONArray cityPrepareJson = new JSONArray(getCountryItemJson(cityTripEntity.CityName));
                for (int i = 0; i < cityPrepareJson.length(); i++) {
                    JSONObject object = cityPrepareJson.getJSONObject(i);
                    String condition = object.getString("name");
                    JSONArray supplyListArr = object.getJSONArray("prepareList");
                    if (!conditionToItemsMap.containsKey(condition))
                        conditionToItemsMap.put(condition, new HashSet<String>());
                    for (int j = 0; j < supplyListArr.length(); j++) {
                        conditionToItemsMap.get(condition).add(supplyListArr.getString(j));
                    }
                }
            } catch(JSONException e) {
                Log.d("error found", "error found");
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.content_trip_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.pre_trip:
                FragmentTransaction itemPrepFt = getSupportFragmentManager().beginTransaction();
                Fragment itemPrepFragment = new PrepItemFragment(conditionToItemsMap, chosenActivities);
                itemPrepFt.replace(R.id.item_provider_container, itemPrepFragment);
                itemPrepFt.commit();
                break;

            case R.id.in_trip:
                YoutubeFragmentX youtubeFragmentX = new YoutubeFragmentX();
                FragmentTransaction youtubeFt = getSupportFragmentManager().beginTransaction();
                youtubeFt.replace(R.id.item_provider_container, youtubeFragmentX);
                youtubeFt.commit();
                break;

            case R.id.post_trip:
                MarkedMapFragment fragment = new MarkedMapFragment(this);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.item_provider_container, fragment);
                ft.commit();

                getSupportFragmentManager().executePendingTransactions();
                SupportMapFragment mapFragment = (SupportMapFragment)fragment.getChildFragmentManager().findFragmentById(R.id.marked_map_fragment);
                mapFragment.getMapAsync(fragment);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
