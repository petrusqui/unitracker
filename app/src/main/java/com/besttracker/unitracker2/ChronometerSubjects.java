package com.besttracker.unitracker2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by psinc_000 on 01/03/2015.
 */
public class ChronometerSubjects extends Activity {

    Chronometer chronometer;
    TextView txtVSubject;
    Calendar startDate;
    Calendar endDate;
    DatabaseConection db;
    String subject;
    TextView totalTime;
    TextView monthlyTime;
    TextView weeklyTime;
    TextView daylyTime;
    long subjectId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chronometer);

        Intent intent = getIntent();
        subject = intent.getStringExtra(MainActivity.SUBJECT_SELECTED);

        // Instantiate of bd
        db = new DatabaseConection(this);
        db.open();

        // Set the title
        txtVSubject = (TextView)findViewById(R.id.chronometer_subject);
        txtVSubject.setText( subject );

        subjectId = db.longSubjectID(subject);

        // set the total time
        totalTime = (TextView)findViewById(R.id.chronometer_totalTime);
        String sTotalTime = parseLongToTime( db.getTotalTime( subjectId ));
        totalTime.setText( sTotalTime );
        // set the monthly time
        monthlyTime = (TextView)findViewById(R.id.chronometer_mothTime);
        String sMonthlyTime = parseLongToTime( db.getMonthTime( subjectId ));
        monthlyTime.setText( sMonthlyTime );
        // set the weekly time
        weeklyTime = (TextView)findViewById(R.id.chronometer_weekTime);
        String sWeeklyTime = parseLongToTime( db.getWeekTime( subjectId ));
        weeklyTime.setText( sWeeklyTime );
        // set the dayly time
        daylyTime = (TextView)findViewById(R.id.chronometer_todayTime);
        String sDaylyTime = parseLongToTime( db.getDaytime( subjectId ));
        daylyTime.setText( sDaylyTime );

        // we get the current time and start the chronometer
        startDate = Calendar.getInstance();
        chronometer = (Chronometer)findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        // When the user stops press the stop button stops the chronometer
        Button btnStop = (Button)findViewById(R.id.stop_chronometer);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                endDate = Calendar.getInstance();
                saveRegisterItem();
            }
        });
    }

    public static String parseLongToTime( Long time ) {
        time = time/1000;
        int minutes = time.intValue()/60;
        int seconds = time.intValue()%60;
        int hours = minutes/60;
        minutes = minutes%60;
        int days = hours/24;
        hours = hours%24;

        String s = "";
        if ( days > 0 ) {
            s = days + ":";
        }

        if (hours < 10 )
            s = s + "0" + hours + ":";
        else
            s = hours + ":";

        if (minutes < 10)
            s = s + "0" + minutes + ":";
        else
            s = s + minutes + ":";

        if (seconds < 10)
            s = s + "0" + seconds;
        else
            s = s + seconds;



        return s;
    }

    private void saveRegisterItem() {
        RegisterItem registerItem = new RegisterItem( startDate, endDate, subjectId );
        db.saveRegister( registerItem );
    }
}
