package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Theme extends AppCompatActivity {

    SharedPref sharedPref;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);
        // store users theme preference
        if(sharedPref.loadDarkMode()) {
            setTheme(R.style.darkTheme);
        } else setTheme(R.style.Theme_AlarmClock);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme);

        imageView = findViewById(R.id.lightMode);
        SwitchCompat switchCompat = findViewById(R.id.themeSwitch);

        // if switch is set to true then dark mode is enabled, image is kept the same
        if(sharedPref.loadDarkMode()) {
            switchCompat.setChecked(true);
            // switch image to the dark mode image if button is on
            imageView.setImageResource(R.drawable.darkmode);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPref.DarkModeState(b);
                imageView.setImageResource(R.drawable.darkmode);
                //this method prevents app from crashing when switching themes
                restartApp();
            }

            private void restartApp() {
                //Pass an intent through the theme class to restart the app
                Intent intent = new Intent(getApplicationContext(), Theme.class);
                startActivity(intent);
                finish();
            }

        });

        Button clockButton = findViewById(R.id.clock_button);
        clockButton.setOnClickListener(view -> {
            Toast.makeText(Theme.this, "Opening clock...", Toast.LENGTH_SHORT).show();
            openClockActivity();
        });

        Button alarmButton = findViewById(R.id.alarm_button);

        alarmButton.setOnClickListener(view -> {
            Toast.makeText(Theme.this, "Opening Alarm...", Toast.LENGTH_SHORT).show();
            openAlarmActivity();
        });

        Button stopwatchButton = findViewById(R.id.stopwatch_button);

        stopwatchButton.setOnClickListener(view -> {
            Toast.makeText(Theme.this, "Opening Themes...", Toast.LENGTH_SHORT).show();
            openStopwatchActivity();
        });
    }

    private void openClockActivity() {
        Intent clockIntent = new Intent(this, MainActivity.class);
        startActivity(clockIntent);
    }

    public void openAlarmActivity() {
        Intent alarmIntent = new Intent(this, Alarm.class);
        startActivity(alarmIntent);
    }

    private void openStopwatchActivity() {
        Intent stopwatchIntent = new Intent(this, stopwatch.class);
        startActivity(stopwatchIntent);
    }
}