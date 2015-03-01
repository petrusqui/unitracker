package com.besttracker.unitracker2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

/**
 * Created by psinc_000 on 01/03/2015.
 */
public class ChronometerSubjects extends Activity {

    Chronometer chronometer;
    TextView txtVSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chronometer);

        Intent intent = getIntent();
        String subject = intent.getStringExtra(MainActivity.SUBJECT_SELECTED);

        // Set the title
        txtVSubject = (TextView)findViewById(R.id.chronometer_subject);
        txtVSubject.setText( subject );

        chronometer = (Chronometer)findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        Button btnStop = (Button)findViewById(R.id.stop_chronometer);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
            }
        });
    }
}
