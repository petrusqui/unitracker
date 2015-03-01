package com.besttracker.unitracker2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

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

        totalTime = (TextView)findViewById(R.id.chronometer_totalTime);
        subjectId = db.longSubjectID(subject);
        totalTime.setText(Float.toString(db.getTotalTime( subjectId )));

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

    private void saveRegisterItem() {
        RegisterItem registerItem = new RegisterItem( startDate, endDate, subjectId );
        db.saveRegister( registerItem );
    }
}
