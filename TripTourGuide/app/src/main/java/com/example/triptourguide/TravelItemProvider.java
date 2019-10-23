package com.example.triptourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.triptourguide.Models.CityTripEntity;
import com.example.triptourguide.Models.ItemPrepRecycleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener;

public class TravelItemProvider extends AppCompatActivity {

    Map<String, Set<String>> conditionToItemsMap = new HashMap<>();
    RecyclerView itemPrepRecycleView;

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

        itemPrepRecycleView = findViewById(R.id.item_prep_recycle);


        Set<String> items = new HashSet<>();
        for (String condition : choosenActivities) {
            if (conditionToItemsMap.containsKey(condition))
                items.addAll(conditionToItemsMap.get(condition));
        }
        itemPrepRecycleView.setLayoutManager(new LinearLayoutManager(this));
        final ItemPrepRecycleAdapter itemPrepAdapter = new ItemPrepRecycleAdapter(items, this);

        SwipeDismissRecyclerViewTouchListener onTouchDismissListener = new SwipeDismissRecyclerViewTouchListener.Builder(
                itemPrepRecycleView,
                new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(View view) {
                        int id = itemPrepRecycleView.getChildLayoutPosition(view);
                        itemPrepAdapter.removeItemOnIndex(id);

                    }
                })
                .setIsVertical(false)
                .setItemTouchCallback(
                        new SwipeDismissRecyclerViewTouchListener.OnItemTouchCallBack() {
                            @Override
                            public void onTouch(int index) {
                                // Do what you want when item be touched
                            }
                        })
                .setItemClickCallback(new SwipeDismissRecyclerViewTouchListener.OnItemClickCallBack() {
                    @Override
                    public void onClick(int position) {
                        // Do what you want when item be clicked
                    }
                }).create();

        itemPrepRecycleView.setOnTouchListener(onTouchDismissListener);
        itemPrepRecycleView.setAdapter(itemPrepAdapter);

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

}
