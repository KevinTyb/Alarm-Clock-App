package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class stopwatch extends AppCompatActivity {

    SharedPref sharedPref;
    TextView timer;
    int mins, secs, MilliSec;
    long MilliSecTimer, startTime, timeBuff, updateTimer = 0L;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        // store users theme preference
        if(sharedPref.loadDarkMode()) {
            setTheme(R.style.darkTheme);
        } else setTheme(R.style.Theme_AlarmClock);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stopwatch);

        timer = findViewById(R.id.stopwatch);
        handler = new Handler();
        Button reset = findViewById(R.id.restart_button);

        Button clockButton = findViewById(R.id.clock_button);

        clockButton.setOnClickListener(view -> {
            Toast.makeText(stopwatch.this, "Opening clock...", Toast.LENGTH_SHORT).show();
            openClockActivity();
        });

        Button alarmButton = findViewById(R.id.alarm_button);

        alarmButton.setOnClickListener(view -> {
            Toast.makeText(stopwatch.this, "Opening Alarm...", Toast.LENGTH_SHORT).show();
            openAlarmActivity();
        });

        Button themeButton = findViewById(R.id.theme_button);

        themeButton.setOnClickListener(view -> {
            Toast.makeText(stopwatch.this, "Opening Themes...", Toast.LENGTH_SHORT).show();
            openThemeActivity();
        });

        Button play = findViewById(R.id.play_button);
        play.setOnClickListener(view -> {

            // Generate clock that is counted in milliseconds
            startTime = SystemClock.uptimeMillis();
            //runnable run after specified amount of time
            // using runnable as an interface that executes threads
            handler.postDelayed(r, 0);

            // set reset to false while clock is running
            reset.setEnabled(false);

        });

        Button pause = findViewById(R.id.pause_button);

        pause.setOnClickListener(view -> {

            //retrieve current time on stopwatch
            timeBuff += MilliSecTimer;

            handler.removeCallbacks(r);
            // let the time to reset if on pause
            reset.setEnabled(true);


        });


        reset.setOnClickListener(view -> {
            // reset all times to 0
            // L is used to instantiate the time to 0 seconds
            MilliSecTimer = 0L;
            startTime = 0L;
            timeBuff = 0L;
            updateTimer = 0L;
            secs = 0;
            mins = 0;
            MilliSec = 0;
        });
    }

    public Runnable r = new Runnable() {
        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        @Override
        public void run() {
            MilliSecTimer = SystemClock.uptimeMillis() - startTime;
            updateTimer = timeBuff + MilliSecTimer;

            // calculate seconds, minutes, and milliseconds so that every 60 seconds, the
            // minute increments. For every 1000 milliseconds, a second is incremented
            secs = (int) (updateTimer / 1000);
            mins = secs / 60;
            secs = secs % 60;
            MilliSec = (int) (updateTimer % 1000);

            timer.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", MilliSec));

            handler.postDelayed(this, 60);
        }
    };


    private void openClockActivity() {
        Intent clockIntent = new Intent(this, MainActivity.class);
        startActivity(clockIntent);
    }

    public void openAlarmActivity() {
        Intent alarmIntent = new Intent(this, Alarm.class);
        startActivity(alarmIntent);
    }

    private void openThemeActivity() {
        Intent themeIntent = new Intent(this, Theme.class);
        startActivity(themeIntent);
    }
}