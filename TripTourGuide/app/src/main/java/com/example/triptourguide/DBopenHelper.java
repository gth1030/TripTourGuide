package com.example.triptourguide;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.triptourguide.Models.CityTripEntity;

import java.sql.PreparedStatement;

public class DBopenHelper extends SQLiteOpenHelper {

    public DBopenHelper(Context context) {
        super(context, "user.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createScript = "create table Cities (\n" +
                "  tripId integer NOT NULL,\n" +
                "  tripOrder integer unique not null,\n" +
                "  countryName VARCHAR(150) not null,\n" +
                "  cityName VARCHAR(150) not null,\n" +
                "  startDate DATE NOT NULL,\n" +
                "  endDate DATE NOT NULL,\n" +
                "  CONSTRAINT FK_tripId_Cities FOREIGN KEY (tripId) REFERENCES Trip(id)\n" +
                ")";
        db.execSQL(createScript);
        String createCities = "create table Trip (id integer primary key, name text not null);";
        db.execSQL(createCities);
        LoadDemoData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CreateNewTrip(SQLiteDatabase db) {

    }

    private void pushCityTripEntity(CityTripEntity cityTripEntity, String tripName, SQLiteDatabase db) {

        String query = "insert into Trip (name) values (\"" + tripName +"\");";
        db.execSQL(query);
        
        query = "select * from Trip where name = \"" + tripName + "\"";
        Cursor c = db.rawQuery(query, null);

    }



    private void LoadDemoData(SQLiteDatabase db) {
        String query = "insert into Trip (name) values (\"미국 캐나다 여행!!\");";
        db.execSQL(query);
        query = "insert into Trip (name) values (\"싱가포르 여행 :) \");";
        db.execSQL(query);

        query = "insert into\n" +
                "  Cities (\n" +
                "    tripId,\n" +
                "    tripOrder,\n" +
                "    countryName,\n" +
                "    cityName,\n" +
                "    startDate,\n" +
                "    endDate\n" +
                "  )\n" +
                "values\n" +
                "  (\n" +
                "    1,\n" +
                "    1,\n" +
                "    \"United States\",\n" +
                "    \"Boston\",\n" +
                "    2019 -10 -22,\n" +
                "    2019 -10 -24\n" +
                "  );";
        db.execSQL(query);

        query = "insert into\n" +
                "  Cities (\n" +
                "    tripId,\n" +
                "    tripOrder,\n" +
                "    countryName,\n" +
                "    cityName,\n" +
                "    startDate,\n" +
                "    endDate\n" +
                "  )\n" +
                "values\n" +
                "  (\n" +
                "    1,\n" +
                "    2,\n" +
                "    \"Canada\",\n" +
                "    \"Montreal\",\n" +
                "    2019 -10 -24,\n" +
                "    2019 -10 -26\n" +
                "  );";
        db.execSQL(query);

        query = "insert into\n" +
                "  Cities (\n" +
                "    tripId,\n" +
                "    tripOrder,\n" +
                "    countryName,\n" +
                "    cityName,\n" +
                "    startDate,\n" +
                "    endDate\n" +
                "  )\n" +
                "values\n" +
                "  (\n" +
                "    1,\n" +
                "    3,\n" +
                "    \"Canada\",\n" +
                "    \"Ottawa\",\n" +
                "    2019 -10 -26,\n" +
                "    2019 -10 -30\n" +
                "  );";
        db.execSQL(query);

    }

}
