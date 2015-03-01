package com.besttracker.unitracker2;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity implements DialogAddSubject.GetSubjectDataListener {

    DatabaseConection db;
    ListView list;
    TextView totalTime;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;

    public static final String SUBJECT_SELECTED = "subject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseConection(this);
        db.open();

        list = (ListView)findViewById(R.id.main_listView_subjects);
        fillListView();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChronometerSubjects.class);
                String subject = (String)parent.getItemAtPosition(position);
                intent.putExtra( SUBJECT_SELECTED, subject );
                startActivity(intent);
                db.close();
            }
        });

        totalTime = (TextView)findViewById(R.id.main_totalTodayTime);
        String sTotalTime = ChronometerSubjects.parseLongToTime( db.getTotalDayTime());
        totalTime.setText(sTotalTime);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if ( id == R.id.action_add_subject ) {
            FragmentManager fm = getFragmentManager();
            DialogAddSubject dialogAddSubject = new DialogAddSubject();
            dialogAddSubject.show(fm, null);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveSubjectDialog(String subject, String time) {
        if ( time.equals("") )
            time = "0";
        SubjectItem subjectItem = new SubjectItem(subject, Color.BLACK, Float.parseFloat(time) );
        db.saveSubject(subjectItem);
        arrayList.add( subject );
        arrayAdapter.notifyDataSetChanged();
    }

    public void fillListView() {
       arrayList = new ArrayList<String>();
        Cursor cursor = db.getAllSubjects();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String subject = cursor.getString( 0 );
            arrayList.add(subject); //add the item
            cursor.moveToNext();
        }

        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                arrayList
        );

        list.setAdapter( arrayAdapter );
    }
}
