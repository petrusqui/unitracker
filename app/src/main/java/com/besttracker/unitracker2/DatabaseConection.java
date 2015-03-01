package com.besttracker.unitracker2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.besttracker.unitracker2.SubjectItem;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by psinc_000 on 28/02/2015.
 */
public class DatabaseConection  {

    /*  BD information */
    public static final String TABLE_SUBJECT = "subjects";
    public static final String SUBJECT_ID = "subjectId";
    public static final String SUBJECT_NAME = "name";
    public static final String SUBJECT_COLOR = "color";
    public static final String SUBJECT_TIME = "time";

    public static final String TABLE_REGISTERS = "registers";
    public static final String REGISTERS_ID = "registerId";
    public static final String REGISTERS_START = "start";
    public static final String REGISTERS_END = "end";
    public static final String REGISTERS_SUBJECTID = "subjectId";

    /* Class attributes */
    private Context classContext;
    private DatabaseHelper db;

    public DatabaseConection( Context context ) {
        classContext = context;
    }

    public void open() {
        db = new DatabaseHelper( classContext );
    }

    public long saveSubject( SubjectItem item ) {
        SQLiteDatabase sdb = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( SUBJECT_NAME, item.get_subject());
        values.put( SUBJECT_TIME, item.get_time() );
        values.put( SUBJECT_COLOR, "NADA");

        long newRowId;
        newRowId = sdb.insert(
                TABLE_SUBJECT,
                null,
                values );

        return newRowId;
    }

    public Cursor getAllSubjects() {
        SQLiteDatabase sdb = db.getReadableDatabase();
        String[] columns = {SUBJECT_NAME};
        Cursor cursor = sdb.query(false, TABLE_SUBJECT, columns, null, null, null, null, null, null );
        return cursor;
    }

    public long saveRegister( RegisterItem item ) {
        SQLiteDatabase sdb = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( REGISTERS_START, item.getStartTime());
        values.put( REGISTERS_END, item.getEndTime());
        values.put( REGISTERS_ID, item.getSubjectId());

        long newRowId = sdb.insert(
                TABLE_REGISTERS,
                null,
                values
        );

        return newRowId;
    }

    class DatabaseHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "DBUnitTracker.db";

        private static final String SQL_CREATION_SUBJECTS =
                " CREATE TABLE " + TABLE_SUBJECT + "( " +
                SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SUBJECT_NAME + " TEXT UNIQUE NOT NULL, " +
                SUBJECT_COLOR + " TEXT NOT NULL, " +
                SUBJECT_TIME + " INTEGER" +
                " );";

        private static final String SQL_CREATION_REGISTERS =
                " CREATE TABLE " + TABLE_REGISTERS + " ( " +
                REGISTERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                REGISTERS_START + " INT UNIQUE NOT NULL, " +
                REGISTERS_END + " INT NOT NULL, " +
                REGISTERS_SUBJECTID + " INTEGER NOT NULL, " +
                " FOREIGN KEY (" + REGISTERS_SUBJECTID + ") " + " REFERENCES " + TABLE_SUBJECT + "(" +SUBJECT_ID + ")"
                + ");";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATION_SUBJECTS);
            db.execSQL(SQL_CREATION_REGISTERS);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
