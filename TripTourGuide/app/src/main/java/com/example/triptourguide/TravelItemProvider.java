package com.example.triptourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelItemProvider extends AppCompatActivity {

    TextView victory;
    Button okaybutton;
    String[] supplydata;
    String[] readysupplydata;
    ListView preparelist;
    ListView readylist;
    String[] preparedata;
    String[] selectedactivity = {"common", "rainy", "swimming"};
    Map<String, List<String>> Bostonsupply = new HashMap<>();
    List<String> notreadysupply = new ArrayList<>();
    List<String> readysupply = new ArrayList<>();
    String pickedcondition;

    private String getJsonOttawaString() {
        String json = "";

        try {
            InputStream is = getAssets().open("OttawaPrepare.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private String getJsonBostonString() {
        String json = "";

        try {
            InputStream is = getAssets().open("BostonPrepare.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private String getJsonMontrealString() {
        String json = "";

        try {
            InputStream is = getAssets().open("MontrealPrepare.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private String getJsonSingaporeString() {
        String json = "";

        try {
            InputStream is = getAssets().open("SingaporePrepare.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_item_provider);

        supplydata = new String[0];
        preparedata = new String[0];
        readysupplydata = new String[0];
        victory = (TextView)findViewById(R.id.victory);
        preparelist = (ListView) findViewById(R.id.preparelist);
        readylist = (ListView)findViewById(R.id.readylist);


        preparelist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                readysupply .add(supplydata[position]);
                notreadysupply.remove(supplydata[position]);
                supplydata = notreadysupply.toArray(new String[notreadysupply.size()]);
                readysupplydata = readysupply .toArray(new String[readysupply .size()]);
                ArraySort.main(supplydata);
                ArraySort.main(readysupplydata);
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

                ArraySort.main(supplydata);
                ArraySort.main(readysupplydata);
                Listreadyapdapter listreadyapdapter = new Listreadyapdapter();
                readylist.setAdapter(listreadyapdapter);
                Listapdapter supplyadapter = new Listapdapter();
                preparelist.setAdapter(supplyadapter);
                if(supplydata.length != 0 )
                    victory.setText("");

            }
        });


        try {
            JSONArray BostonPrepareJson = new JSONArray(getJsonBostonString());
            for (int i = 0; i < BostonPrepareJson.length(); i++) {
                JSONObject object = BostonPrepareJson.getJSONObject(i);
                String condition = object.getString("name");
                JSONArray supplyListArr = object.getJSONArray("prepareList");
                List<String> supplyList = new ArrayList<>();
                for (int j = 0; j < supplyListArr.length(); j++) {
                    supplyList.add(supplyListArr.getString(j));
                }
                Bostonsupply.put(condition, supplyList);

            }
        } catch (JSONException e) {
            Log.d("error found", "error found");
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

            TextView supplyname = (TextView)convertView.findViewById(R.id.textView4);
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

            TextView supplyname = (TextView)convertView.findViewById(R.id.textView4);
            supplyname.setText(readysupplydata[position]);

            return convertView;
        }
    }


    public void supplymethod(View view) {
        notreadysupply = new ArrayList<>();
        preparelist = (ListView) findViewById(R.id.preparelist);
        for (int i = 0; i < selectedactivity.length; i++) {
            pickedcondition = selectedactivity[i];
            if (!Bostonsupply.containsKey(pickedcondition))
                continue;
            notreadysupply.addAll(Bostonsupply.get(pickedcondition));
        }
        supplydata = notreadysupply.toArray(new String[notreadysupply.size()]);
        ArraySort.main(supplydata);
        Listapdapter supplyadapter = new Listapdapter();
        preparelist.setAdapter(supplyadapter);

    }

    public static class ArraySort {
        public static void main(String[] args) {
            String temp = "";
            int arySize = args.length;
            for (int i = 0; i < arySize - 1; i++) {
                for (int j = i; j < arySize; j++) {
                    if (args[i].compareTo(args[j]) > 0) {
                        temp = args[i];
                        args[i] = args[j];
                        args[j] = temp;
                    }
                }
            }
        }
    }
}
