package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPref sharedPref;
    //create variables for the digital and analog clock
    @SuppressLint("StaticFieldLeak")
    private static Button buttonSwap;
    @SuppressLint("StaticFieldLeak")
    private static DigitalClock dClock;
    @SuppressLint("StaticFieldLeak")
    private static AnalogClock aClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if(sharedPref.loadDarkMode()) {
            setTheme(R.style.darkTheme);
        } else setTheme(R.style.Theme_AlarmClock);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clockSwitch();

        Button alarmButton = findViewById(R.id.alarm_button);
        Button stopwatchButton = findViewById(R.id.stopwatch_button);
        Button themeButton = findViewById(R.id.theme_button);

        stopwatchButton.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "Opening Stopwatch...", Toast.LENGTH_SHORT).show();
            openStopwatchActivity();
        });

        alarmButton.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "Opening Alarm...", Toast.LENGTH_SHORT).show();
            openAlarmActivity();
        });

        themeButton.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "Opening Themes...", Toast.LENGTH_SHORT).show();
            openThemeActivity();
        });
    }

    private void openStopwatchActivity() {
        Intent stopwatchIntent = new Intent(this, stopwatch.class);
        startActivity(stopwatchIntent);
    }

    public void openAlarmActivity() {
        Intent alarmIntent = new Intent(this, Alarm.class);
        startActivity(alarmIntent);
    }

    private void openThemeActivity() {
        Intent themeIntent = new Intent(this, Theme.class);
        startActivity(themeIntent);
    }

    public void clockSwitch() {

        //cast variables
        buttonSwap = findViewById(R.id.clockSwap);
        dClock = findViewById(R.id.digitalClock);
        aClock = findViewById(R.id.analogClock);

        buttonSwap.setOnClickListener(view -> {

            // If digital clock is visible then analogue clock will become invisibe
            // and vice versa
            if(dClock.getVisibility() == DigitalClock.GONE) {
                dClock.setVisibility(DigitalClock.VISIBLE);
                aClock.setVisibility(AnalogClock.GONE);
            } else {
                aClock.setVisibility(AnalogClock.VISIBLE);
                dClock.setVisibility(View.GONE);
            }
        });
    }
}