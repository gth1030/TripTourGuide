package com.example.triptourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    List<String> dummyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dummyList.add("abc");
        dummyList.add("bcd");
        dummyList.add("bcd");
        dummyList.add("bcd");
        dummyList.add("bcd");
        dummyList.add("bcd");
        dummyList.add("bcd");
        dummyList.add("bcd");
        dummyList.add("bcd");
        dummyList.add("bcd");

        DBopenHelper dbHelper = new DBopenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        wheelView = findViewById(R.id.tripPicker);
        wheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelView.setSkin(WheelView.Skin.Common);
        wheelView.setWheelData(dummyList);

    }


    public void musicBtn(View view) {
        Intent intent = new Intent(this, MusicListener.class);
        startActivity(intent);
    }

    public List<String> GetTripList(SQLiteDatabase db) {
        db.execSQL();
    }

}
