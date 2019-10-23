package com.example.triptourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    WheelView wheelView;
    Button newTrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBopenHelper dbHelper = new DBopenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        wheelView = findViewById(R.id.tripPicker);
        wheelView.setWheelClickable(true);
        wheelView.setOnWheelItemClickListener(new TripNameClickedListener(this));
        wheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelView.setSkin(WheelView.Skin.Common);
        wheelView.setWheelData(GetTripList(db));
        wheelView.setWheelSize(5);

        WheelView.WheelViewStyle wheelStyle= new WheelView.WheelViewStyle();
        wheelStyle.selectedTextColor = Color.BLACK;
        wheelStyle.textColor = Color.GRAY;
        wheelStyle.selectedTextSize = 20;
        wheelView.setStyle(wheelStyle);

        newTrip = findViewById(R.id.new_trip);

    }

    public void newTripBtn(View view) {
        Intent intent = new Intent(this, TripCreator.class);
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
        return tripList;
    }

}
