package com.example.triptourguide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.triptourguide.Listners.ProhibitedListGridViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class gridadapter extends AppCompatActivity {

    static GridView ProhibitedItemGridView;
    Map<String, List<String>> countryToProhItemMap = new HashMap<>();
    Map<String, String> itemToDescriptionMap = new HashMap<>();
    List<String> loadprohitem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridadapter);
        ProhibitedItemGridView = (GridView) findViewById(R.id.ProhItemGridView);

        try {
            JSONArray CountryProhibitedJson = new JSONArray(TripUtils.ReadFileFromAsset(this, "CountryProhibited.json"));
            for (int i = 0; i < CountryProhibitedJson.length(); i++) {

                JSONObject country = CountryProhibitedJson.getJSONObject(i);
                String countryname = country.getString("name");
                JSONArray prohitemlistArr = country.getJSONArray("prohibited");
                List<String> prohitemlist = new ArrayList<>();
                for (int j = 0; j < prohitemlistArr.length(); j++) {
                    prohitemlist.add(prohitemlistArr.getString(j));
                }
                countryToProhItemMap.put(countryname, prohitemlist);

            }

        } catch (JSONException e) {
            Log.d("error found", "error found");
        }

        try {
            JSONArray DescriptionJson = new JSONArray(TripUtils.ReadFileFromAsset(this, "ProhibitDescription.json"));
            for (int i = 0; i < DescriptionJson.length(); i++) {

                JSONObject item = DescriptionJson.getJSONObject(i);
                String itemname = item.getString("name");
                String description = item.getString("description");
                itemToDescriptionMap.put(itemname, description);

            }

        } catch (JSONException e) {
            Log.d("error found", "error found");
        }

        loadprohitem = countryToProhItemMap.get("Singapore");
        ProhibitedListGridViewAdapter adapter = new ProhibitedListGridViewAdapter(this, loadprohitem);
        ProhibitedItemGridView.setAdapter(adapter);

        ProhibitedItemGridView.setOnItemClickListener(new ProhibitedListGridListener(adapter,itemToDescriptionMap));


    }

    public static class ProhibitedListGridListener implements AdapterView.OnItemClickListener {

        ProhibitedListGridViewAdapter _adapter;
        private Map<String, String> _prohebitedItemToReason;

        public ProhibitedListGridListener(ProhibitedListGridViewAdapter adapter, Map<String, String> prohebitedItemToReason) {
            _adapter = adapter;
            _prohebitedItemToReason = prohebitedItemToReason;
        }

        public void ResetAdapter(ProhibitedListGridViewAdapter adapter) {
            _adapter = adapter;
        }


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Context context = view.getContext();
            String clickedText = ProhibitedItemGridView.getItemAtPosition(position).toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(clickedText);
            String iconid = clickedText+"_50";
            Class<R.drawable> drawable = R.drawable.class;
            Field field = null;
            try {
                field = drawable.getField( iconid );
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            int r = 0;
            try {
                r = field.getInt(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            builder.setMessage(_prohebitedItemToReason.get(clickedText));
            builder.setIcon(r);
            builder.setPositiveButton("확인", null);
            builder.show();
            }
        }
    }


