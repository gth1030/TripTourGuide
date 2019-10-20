package com.example.triptourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    WheelView wheelView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBopenHelper dbHelper = new DBopenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        wheelView = findViewById(R.id.tripPicker);
        wheelView.setWheelClickable(true);
        wheelView.setOnWheelItemClickListener(new TripNameClickedListener());
        wheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelView.setSkin(WheelView.Skin.Common);
        wheelView.setWheelData(GetTripList(db));
    }


    public void musicBtn(View view) {
        Intent intent = new Intent(this, MusicListener.class);
        startActivity(intent);
    }

    public List<String> GetTripList(SQLiteDatabase db) {

        List<String> tripList = new ArrayList<>();
        Cursor c = db.rawQuery("select * from Trip", null);
        while (c.moveToNext()) {
            int nameInd = c.getColumnIndex("name");
            String tripName = c.getString(nameInd);
            tripList.add(tripName);
        }

        tripList.add("+ new Trip");
        return tripList;
    }

}
