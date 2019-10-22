package com.example.triptourguide;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.triptourguide.Models.CityTripEntity;

import java.util.ArrayList;
import java.util.List;

public class TripCreator extends AppCompatActivity {

    ListView cityTripListView;
    Button newTripButton;

    List<CityTripEntity> cityTripList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_creator);

        cityTripListView = findViewById(R.id.trip_list);
        newTripButton = findViewById(R.id.create_city_button);

    }

    public void addTripButton(View view) {
        Intent intent = new Intent(this, TripSettings.class);
        startActivityForResult(intent, 1);
    }

    public void composeTripButton(View view) {

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CityTripEntity cityTripEntity = null;
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
               cityTripEntity = (CityTripEntity) data.getSerializableExtra("newCity");
            }

            cityTripList.add(cityTripEntity);

            TripCreatorListAdapter adapter = new TripCreatorListAdapter(this);
            adapter.UpdateCityTripEntity(cityTripList);
            cityTripListView.setAdapter(adapter);

        }

    }
}
