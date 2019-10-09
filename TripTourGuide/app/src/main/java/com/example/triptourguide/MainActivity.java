package com.example.triptourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Calendar startDate;
    private Calendar endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DateRangeCalendarView calendarview = (DateRangeCalendarView) findViewById(R.id.calendarView);

        calendarview.setSelectedDateRange(startDate, endDate);
    }

    public interface CalendarListener {
        void onFirstDateSelected(Calendar startDate);

        void onDateRangeSelected(Calendar startDate, Calendar endDate);


    }

    public void btnBeforemethod(View view) {
        Intent intent = new Intent(this, before_trip.class);
        startActivity(intent);
    }
    public void btnWhilemethod(View view) {
        Intent intent = new Intent(this,while_trip.class);
        startActivity(intent);
    }
    public void btnAftermethod(View view) {
        Intent intent = new Intent(this, after_trip.class);
        startActivity(intent);
    }



    class CalendarLIstener implements DateRangeCalendarView.CalendarListener {

        @Override
        public void onFirstDateSelected(Calendar startDate) {
            Toast.makeText(MainActivity.this, "Start Date: " + startDate.getTime().toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDateRangeSelected(Calendar startDate, Calendar endDate) {
            Toast.makeText(MainActivity.this, "Start Date: " + startDate.getTime().toString() + " End date: " + endDate.getTime().toString(), Toast.LENGTH_SHORT).show();
        }


    }
}