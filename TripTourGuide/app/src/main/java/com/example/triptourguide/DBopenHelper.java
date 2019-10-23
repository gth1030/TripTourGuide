package com.example.triptourguide;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.triptourguide.Models.CityTripEntity;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBopenHelper extends SQLiteOpenHelper {

    public DBopenHelper(Context context) {
        super(context, "user.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createScript = "create table Cities (\n" +
                " cityId integer primary key, " +
                "  tripId integer NOT NULL,\n" +
                "  tripOrder integer not null,\n" +
                "  countryName VARCHAR(150) not null,\n" +
                "  cityName VARCHAR(150) not null,\n" +
                "  startDate DATE NOT NULL,\n" +
                "  endDate DATE NOT NULL,\n" +
                "  CONSTRAINT FK_tripId_Cities FOREIGN KEY (tripId) REFERENCES Trip(id)\n" +
                ")";
        db.execSQL(createScript);
        String createCities = "create table Trip (id integer primary key, name text not null);";
        db.execSQL(createCities);
        String createActivity = "create table Activity (cityId integer not null, activityName VARCHAR(50) not null, " +
                "CONSTRAINT FK_cityID_Trip FOREIGN KEY (cityID) REFERENCES Cities(cityID));";
        db.execSQL(createActivity);
        LoadDemoData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CreateNewTrip(SQLiteDatabase db, List<CityTripEntity> cityTripEntityList, String tripName) {
        for (int i = 0; i < cityTripEntityList.size(); i++) {
            pushCityTripEntity(cityTripEntityList.get(i), tripName, i, db);
        }
    }

    public List<CityTripEntity> RetrieveTripDetail(SQLiteDatabase db, String tripName) {
        String query = "select id from Trip where name = \"" + tripName + "\"";
        Cursor c = db.rawQuery(query, null);
        c.moveToNext();
        int tripId = c.getInt(0);
        query = "select * from Cities where tripId = " + tripId + " order by tripOrder";
        c = db.rawQuery(query, null);

        List<CityTripEntity> tripList = new ArrayList<>();
        while (c.moveToNext()) {
            String countryName = c.getString(c.getColumnIndex("countryName"));
            String cityName = c.getString(c.getColumnIndex("cityName"));
            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            try {
                startDate.setTime(new SimpleDateFormat().parse(c.getString(c.getColumnIndex("startDate"))));
                endDate.setTime(new SimpleDateFormat().parse(c.getString(c.getColumnIndex("endDate"))));
            } catch (ParseException e) {
                Log.d("Error!!", "Parsing date error in data retrieve!!!");
            }
            query = "select activityName from Activity where cityID = " + c.getInt(c.getColumnIndex("cityId"));
            Cursor cActivity = db.rawQuery(query, null);
            List<String> activityList = new ArrayList<>();
            while (cActivity.moveToNext()) {
                activityList.add(cActivity.getString(0));
            }

            tripList.add(new CityTripEntity(countryName, cityName, startDate, endDate, activityList));
        }
        return tripList;
    }

    private void pushCityTripEntity(CityTripEntity cityTripEntity, String tripName, int tripOrder, SQLiteDatabase db) {

        String query = "insert into Trip (name) values (\"" + tripName +"\");";
        db.execSQL(query);
        
        query = "select id from Trip where name = \"" + tripName + "\"";
        Cursor c = db.rawQuery(query, null);
        c.moveToNext();
        int tripId = c.getInt(0);

        query = "insert into Cities (tripId, tripOrder, countryName, cityName, startDate, endDate) " +
                "values (\"" + tripId + "\", \"" + tripOrder + "\", \"" + cityTripEntity.CountryName + "\", \"" +
                cityTripEntity.CityName + "\", " + new SimpleDateFormat("yyyy-MM-dd").format(cityTripEntity.StartDate.getTime())
                + ", " + new SimpleDateFormat("yyyy-MM-dd").format(cityTripEntity.EndDate.getTime()) + ")";

        db.execSQL(query);

        query = "select cityId from Cities where tripId = " + tripId + " and cityName = \"" + cityTripEntity.CityName + "\"";

        c = db.rawQuery(query, null);
        c.moveToNext();
        int cityId = c.getInt(0);

        for (String activity : cityTripEntity.ActivityList) {
            query = "insert into Activity(cityId, activityName) values (" + cityId + ", " + activity + ")";
            db.execSQL(query);
        }
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

        query = "insert into Activity (cityId, activityName) values (1, \"swiming\"),(1, \"hiking\"),(1, \"\")" +
                ",(1, \"snorkel\"),(1, \"city\")";
        db.execSQL(query);

    }

}
