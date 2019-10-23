package com.example.triptourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.triptourguide.Models.CityTripEntity;
import com.example.triptourguide.Models.ItemPrepRecycleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener;

public class TravelItemProvider extends AppCompatActivity {

    Map<String, Set<String>> conditionToItemsMap = new HashMap<>();
    Map<String, List<String>> countryToItemsMap = new HashMap<>();
    PrepItemFragment prepItemFragment;
    ProhItemFragment prohItemFragment;
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

        Set<String> choosenActivities = new HashSet<>();
        choosenActivities.add("common");
        for (CityTripEntity cityTripEntity : cityTripEntityList)
            choosenActivities.addAll(cityTripEntity.ActivityList);

        Set<String> items = new HashSet<>();
        for (String condition : choosenActivities) {
            if (conditionToItemsMap.containsKey(condition))
                items.addAll(conditionToItemsMap.get(condition));
        }

        list = countryToItemsMap.get("US");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        prepItemFragment = new PrepItemFragment(conditionToItemsMap, choosenActivities);
        prohItemFragment = new ProhItemFragment(list);
        ft.replace(R.id.item_provider_container, prepItemFragment);
        ft.commit();
    }


    private void CountryToProhItemMap(String countryname) {
        try {
            JSONArray CountryProhJson = new JSONArray(TripUtils.ReadFileFromAsset(this, "CountryProhibited.json"));
            for (int i = 0; i < CountryProhJson.length(); i++) {
                JSONObject country = CountryProhJson.getJSONObject(i);
                String countryN = country.getString("name");
                JSONArray prohibitedListArr = country.getJSONArray("prohibited");
                List<String> prohibitedList = new ArrayList<>();
                for (int j = 0; j < prohibitedListArr.length(); j++) {
                    prohibitedList.add(prohibitedListArr.getString(j));
                }
                countryToItemsMap.put(countryN, prohibitedList);
            }

        } catch (JSONException e) {
            Log.d("error found", "error found");
        }
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
                break;

            case R.id.in_trip:
                Intent intentIn = new Intent(this, MusicListener.class);
                startActivity(intentIn);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void btnpreparemethod(View view){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        tran.replace(R.id.item_provider_container, prepItemFragment);
        tran.commit();
    }

    public void btnprohibitedmethod(View view){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        tran.replace(R.id.item_provider_container, prohItemFragment);
        tran.commit();

    }

}
