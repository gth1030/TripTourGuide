package com.example.triptourguide;

import androidx.appcompat.app.AlertDialog;
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
import com.example.triptourguide.Models.ConsulateModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class TravelItemProvider extends AppCompatActivity {

    Map<String, Set<String>> conditionToItemsMap = new HashMap<>();
    Map<String, List<String>> countryToItemsMap = new HashMap<>();
    Map<String, ConsulateModel> countryToConsulateMap = new HashMap<>();
    PrepItemFragment prepItemFragment;
    List<String> list;

    private String getCountryItemJson(String countryName) {
        return TripUtils.ReadFileFromAsset(this, countryName + "Prepare.json");
    }

    private String getCountryConsulateJson(String countryName) {
        return TripUtils.ReadFileFromAsset(this, countryName + "Consulate.json");
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
        ft.replace(R.id.item_provider_container, prepItemFragment);
        ft.commit();
    }

    private void populateCountryToCosulateMap() {
        String[] builtInConsulateCountry = new String[]{"Singapore", "United States", "Canada"};
        for (String countryName : builtInConsulateCountry) {
            try {
                JSONArray countryConsulateJson = new JSONArray(getCountryConsulateJson(countryName));
                for (int i = 0; i < countryConsulateJson.length(); i++) {
                    JSONObject contryConsulateJson = countryConsulateJson.getJSONObject(i);
                    String city = contryConsulateJson.getString("name");
                    String address = contryConsulateJson.getString("address");
                    String telephone = contryConsulateJson.getString("telephone");
                    String fax = contryConsulateJson.getString("fax");
                    String homepage = contryConsulateJson.getString("homepage");
                    JSONArray consulateListArr = contryConsulateJson.getJSONArray("jurisdiction");
                    List<String> jurisdictions = new ArrayList<>();
                    for (int j = 0; j < consulateListArr.length(); j++) {
                        jurisdictions.add(consulateListArr.getString(j));
                    }
                    countryToConsulateMap.put(country, new ConsulateModel(city, address, telephone, fax, homepage, jurisdictions));
                }
            } catch(JSONException e) {
                Log.d("error found", "error found");
            }
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

            case R.id.country_consulate:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("영사관 정보");
                builder.setIcon(R.drawable.consulate_50);
                builder.setMessage("");
                builder.setPositiveButton("확인", null);
                builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnpreparemethod(View view){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        tran.replace(R.id.item_provider_container, prepItemFragment);
        tran.commit();
    }


    public void testBtn(View view) {
        Intent intent = new Intent(this, gridadapter.class);
        startActivity(intent);
    }

}
