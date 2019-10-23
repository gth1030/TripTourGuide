package com.example.triptourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.triptourguide.Models.CityTripEntity;
import com.example.triptourguide.Models.ProhibitedListFragment;

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

public class TravelItemProvider extends AppCompatActivity {

    TextView victory;
    String[] supplydata;
    String[] readysupplydata;
    ListView preparelist;
    ListView readylist;
    String[] preparedata;
    String[] selectedactivity = {"common", "rainy", "swimming"};
    Map<String, Set<String>> conditionToItemsMap = new HashMap<>();
    Map<String, List<String>> prohibitedItem = new HashMap<>();
    List<String> notreadysupply = new ArrayList<>();
    List<String> readysupply = new ArrayList<>();
    List<String> selectedprohibited;
    String pickedcondition;

    ProhibitedListFragment prohibitedListFragment = new ProhibitedListFragment();

    GridView prohibitedListGridView;


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

        supplydata = new String[0];
        preparedata = new String[0];
        readysupplydata = new String[0];
        victory = findViewById(R.id.victory);
        preparelist = findViewById(R.id.preparelist);
        readylist = findViewById(R.id.readylist);


        preparelist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                readysupply .add(supplydata[position]);
                notreadysupply.remove(supplydata[position]);
                supplydata = notreadysupply.toArray(new String[notreadysupply.size()]);
                readysupplydata = readysupply .toArray(new String[readysupply .size()]);
                Arrays.sort(supplydata);
                Arrays.sort(readysupplydata);
                Listapdapter supplyadapter = new Listapdapter();
                preparelist.setAdapter(supplyadapter);
                Listreadyapdapter listreadyapdapter = new Listreadyapdapter();
                readylist.setAdapter(listreadyapdapter);
                if(supplydata.length == 0 )
                    victory.setText("Time to Go!");
            }
        });

        readylist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                notreadysupply.add(readysupplydata[position]);
                readysupply .remove(readysupplydata[position]);

                supplydata = notreadysupply.toArray(new String[notreadysupply.size()]);
                readysupplydata = readysupply .toArray(new String[readysupply .size()]);

                Arrays.sort(supplydata);
                Arrays.sort(readysupplydata);
                Listreadyapdapter listreadyapdapter = new Listreadyapdapter();
                readylist.setAdapter(listreadyapdapter);
                Listapdapter supplyadapter = new Listapdapter();
                preparelist.setAdapter(supplyadapter);
                if(supplydata.length != 0 )
                    victory.setText("");

            }
        });

        try {
            JSONArray ProhibitedListJson = new JSONArray(TripUtils.ReadFileFromAsset(this, "CountryProhibited.json"));
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

        String country = "US";
        selectedprohibited  = prohibitedItem.get(country);
        prohibitedListGridView = (GridView)findViewById(R.id.prohibited_list_grid);
        ProhibitedListGridViewAdapter adapter = new ProhibitedListGridViewAdapter(this, selectedprohibited);
        prohibitedListGridView.setAdapter(adapter);
        prohibitedListGridView.setOnItemClickListener(new ProhibitedListGridListener(adapter));
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


    class Listapdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return supplydata.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.item_provider_row, null);
            }

            TextView supplyname = convertView.findViewById(R.id.textView4);
            supplyname.setText(supplydata[position]);

            return convertView;
        }
    }

    class Listreadyapdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return readysupplydata.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.item_provider_row, null);
            }

            TextView supplyname = convertView.findViewById(R.id.textView4);
            supplyname.setText(readysupplydata[position]);

            return convertView;
        }
    }


    public void supplymethod(View view) {
        notreadysupply = new ArrayList<>();
        preparelist = findViewById(R.id.preparelist);
        for (int i = 0; i < selectedactivity.length; i++) {
            pickedcondition = selectedactivity[i];
            if (!conditionToItemsMap.containsKey(pickedcondition))
                continue;
            notreadysupply.addAll(conditionToItemsMap.get(pickedcondition));
        }
        supplydata = notreadysupply.toArray(new String[notreadysupply.size()]);
        Arrays.sort(supplydata);
        Listapdapter supplyadapter = new Listapdapter();
        preparelist.setAdapter(supplyadapter);

        ProhibitedListGridViewAdapter adapter = new ProhibitedListGridViewAdapter(this, selectedprohibited);
        prohibitedListGridView.setAdapter(adapter);
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
                Intent intentPre = new Intent(this, TravelItemProvider.class);
                startActivity(intentPre);
                break;

            case R.id.in_trip:
                Intent intentIn = new Intent(this, MusicListener.class);
                startActivity(intentIn);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public static class ProhibitedListGridListener implements AdapterView.OnItemClickListener {

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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    prohibitedListGridView.setAdapter(_adapter);
                }
            });
        }
    }

    public void btnProhibitedmethon(View view){
        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction tran = manager.beginTransaction();
        tran.add(R.id.container, prohibitedListFragment);
        tran.commit();
    }

}
