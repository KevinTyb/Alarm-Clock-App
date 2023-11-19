package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class Alarm extends AppCompatActivity {

    SharedPref sharedPref;
    private TimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if(sharedPref.loadDarkMode()) {
            setTheme(R.style.darkTheme);
        } else setTheme(R.style.Theme_AlarmClock);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);

        picker = (TimePicker) findViewById(R.id.timePicker);

        // Alarm Buttons
        Button cancelButton = findViewById(R.id.timeCancelbtn);

        cancelButton.setOnClickListener(view -> setCancelAlarm());

        findViewById(R.id.timeSetbtn).setOnClickListener(view -> {

            // use calender object so the time is in milliseconds
            calendar = Calendar.getInstance();

            //version of the sdk of the device must be equal or greater to 23
            if(Build.VERSION.SDK_INT >= 23) {
                calendar.set(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        picker.getHour(),
                        picker.getMinute(),
                        0
                );
            } else {
                calendar.set(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        picker.getCurrentHour(),
                        picker.getCurrentMinute(),
                        0
                );
            }

            setAlarm(calendar.getTimeInMillis());

        });

        // Activity Buttons
        Button stopwatchButton = findViewById(R.id.stopwatch_button);
        Button clockButton = findViewById(R.id.clock_button);
        Button themeButton = findViewById(R.id.theme_button);

        stopwatchButton.setOnClickListener(view -> {
            Toast.makeText(Alarm.this, "Opening Stopwatch...", Toast.LENGTH_SHORT).show();
            openStopwatchActivity();
        });

        clockButton.setOnClickListener(view -> {
            Toast.makeText(Alarm.this, "Opening Clock...", Toast.LENGTH_SHORT).show();
            openClockActivity();
        });

        themeButton.setOnClickListener(view -> {
            Toast.makeText(Alarm.this, "Opening Themes...", Toast.LENGTH_SHORT).show();
            openThemeActivity();
        });

    }

    private void setAlarm(long timeInMillis) {

        //here we will set our alarm

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent =  new Intent(this, myBroadcastReceiver.class);

        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // we will repeat alarm everyday and also while device is sleeping
        alarmManager.setRepeating(AlarmManager.RTC,
                timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent);

        Toast.makeText(this, "Your alarm has been set", Toast.LENGTH_SHORT).show();
    }

    public void setCancelAlarm() {

        Intent intent = new Intent(this, myBroadcastReceiver.class);

        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Toast.makeText(this, "Your alarm has been cancelled", Toast.LENGTH_SHORT).show();
        }
        alarmManager.cancel(pendingIntent);
    }

    private void openStopwatchActivity() {
        Intent stopwatchIntent = new Intent(this, stopwatch.class);
        startActivity(stopwatchIntent);
    }

    public void openClockActivity() {
        Intent clockIntent = new Intent(this, MainActivity.class);
        startActivity(clockIntent);
    }

    private void openThemeActivity() {
        Intent themeIntent = new Intent(this, Theme.class);
        startActivity(themeIntent);
    }
}