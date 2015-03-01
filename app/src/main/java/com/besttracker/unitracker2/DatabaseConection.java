package com.besttracker.unitracker2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

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
        values.put( REGISTERS_START, item.getStartTime().getTimeInMillis());
        values.put( REGISTERS_END, item.getEndTime().getTimeInMillis());
        values.put( REGISTERS_SUBJECTID, item.getSubjectId());

        long newRowId = sdb.insert(
                TABLE_REGISTERS,
                null,
                values
        );

        return newRowId;
    }

    public long getTotalTime( long subjectId ) {
        SQLiteDatabase sdb = db.getReadableDatabase();

        String query = "SELECT SUM( " + REGISTERS_END  + " - " + REGISTERS_START + " ) " +
                " FROM " + TABLE_REGISTERS + " WHERE " + REGISTERS_SUBJECTID  + " = ?;";
        String[] args = { Long.toString(subjectId) };

        Cursor cursor = sdb.rawQuery( query, args );

        if ( cursor.moveToFirst() ) {
            return cursor.getLong(0);
        } else {
            return 0;
        }
    }

    public long getMonthTime( long subjectId ) {
        SQLiteDatabase sdb = db.getReadableDatabase();

        // Set begin to the begin of the month
        Calendar begin = Calendar.getInstance();
        begin.set(Calendar.DAY_OF_MONTH, begin.getActualMinimum(Calendar.DAY_OF_MONTH));
        begin.set(Calendar.HOUR, begin.getActualMinimum(Calendar.HOUR));
        begin.set(Calendar.MINUTE, begin.getActualMinimum(Calendar.MINUTE));
        begin.set(Calendar.SECOND, begin.getActualMinimum(Calendar.SECOND));
        begin.set(Calendar.MILLISECOND, begin.getActualMinimum(Calendar.MILLISECOND));
        long msBegin = begin.getTimeInMillis();

        // set end to the end of the month
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, begin.getActualMaximum(Calendar.DAY_OF_MONTH));
        begin.set(Calendar.HOUR, begin.getActualMaximum(Calendar.HOUR));
        begin.set(Calendar.MINUTE, begin.getActualMaximum(Calendar.MINUTE));
        begin.set(Calendar.SECOND, begin.getActualMaximum(Calendar.SECOND));
        begin.set(Calendar.MILLISECOND, begin.getActualMaximum(Calendar.MILLISECOND));
        long msEnd = end.getTimeInMillis();

        String query = "SELECT SUM( " + REGISTERS_END  + " - " + REGISTERS_START + " ) " +
                " FROM " + TABLE_REGISTERS +
                " WHERE (" + REGISTERS_SUBJECTID  + " = ? AND " +
                REGISTERS_START + " >= ? AND " +
                REGISTERS_END + " <= ?);";

        String query2 = "SELECT SUM( " + REGISTERS_END  + " - " + REGISTERS_START + " ) " +
                " FROM " + TABLE_REGISTERS + " WHERE " + REGISTERS_SUBJECTID  + " = ? AND " +
                REGISTERS_START + " >= ?;";
        String[] args = { Long.toString(subjectId), Long.toString(msBegin), Long.toString(msEnd) };
        String[] args2 = { Long.toString(subjectId), Long.toString(msBegin) };
        Cursor cursor = sdb.rawQuery( query, args );

        if ( cursor.moveToFirst() ) {
            return cursor.getLong(0);
        } else {
            return 0;
        }
    }

    public long getWeekTime( long subjectId ) {
        SQLiteDatabase sdb = db.getReadableDatabase();

        // Set begin to the begin of the week
        Calendar begin = Calendar.getInstance();
        begin.set(Calendar.DAY_OF_WEEK, begin.getActualMinimum(Calendar.DAY_OF_WEEK));
        begin.set(Calendar.HOUR, begin.getActualMinimum(Calendar.HOUR));
        begin.set(Calendar.MINUTE, begin.getActualMinimum(Calendar.MINUTE));
        begin.set(Calendar.SECOND, begin.getActualMinimum(Calendar.SECOND));
        begin.set(Calendar.MILLISECOND, begin.getActualMinimum(Calendar.MILLISECOND));
        long msBegin = begin.getTimeInMillis();

        // set end to the end of the week
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_WEEK, begin.getActualMaximum(Calendar.DAY_OF_WEEK));
        begin.set(Calendar.HOUR, begin.getActualMaximum(Calendar.HOUR));
        begin.set(Calendar.MINUTE, begin.getActualMaximum(Calendar.MINUTE));
        begin.set(Calendar.SECOND, begin.getActualMaximum(Calendar.SECOND));
        begin.set(Calendar.MILLISECOND, begin.getActualMaximum(Calendar.MILLISECOND));
        long msEnd = end.getTimeInMillis();

        String query = "SELECT SUM( " + REGISTERS_END  + " - " + REGISTERS_START + " ) " +
                " FROM " + TABLE_REGISTERS +
                " WHERE (" + REGISTERS_SUBJECTID  + " = ? AND " +
                REGISTERS_START + " >= ? AND " +
                REGISTERS_END + " <= ?);";;
        String[] args = { Long.toString(subjectId), Long.toString(msBegin), Long.toString(msEnd) };
        Cursor cursor = sdb.rawQuery( query, args );

        if ( cursor.moveToFirst() ) {
            return cursor.getLong(0);
        } else {
            return 0;
        }
    }

    public long getDaytime( long subjectId ) {
        SQLiteDatabase sdb = db.getReadableDatabase();

        // Set begin to the begin of the day
        Calendar begin = Calendar.getInstance();
        begin.set(Calendar.HOUR, begin.getActualMinimum(Calendar.HOUR));
        begin.set(Calendar.MINUTE, begin.getActualMinimum(Calendar.MINUTE));
        begin.set(Calendar.SECOND, begin.getActualMinimum(Calendar.SECOND));
        begin.set(Calendar.MILLISECOND, begin.getActualMinimum(Calendar.MILLISECOND));
        long msBegin = begin.getTimeInMillis();

        // set end to the end of the day
        Calendar end = Calendar.getInstance();
        begin.set(Calendar.HOUR, begin.getActualMaximum(Calendar.HOUR));
        begin.set(Calendar.MINUTE, begin.getActualMaximum(Calendar.MINUTE));
        begin.set(Calendar.SECOND, begin.getActualMaximum(Calendar.SECOND));
        begin.set(Calendar.MILLISECOND, begin.getActualMaximum(Calendar.MILLISECOND));
        long msEnd = end.getTimeInMillis();

        String query = "SELECT SUM( " + REGISTERS_END  + " - " + REGISTERS_START + " ) " +
                " FROM " + TABLE_REGISTERS +
                " WHERE (" + REGISTERS_SUBJECTID  + " = ? AND " +
                REGISTERS_START + " >= ? AND " +
                REGISTERS_END + " <= ?);";;
        String[] args = { Long.toString(subjectId), Long.toString(msBegin), Long.toString(msEnd) };
        Cursor cursor = sdb.rawQuery( query, args );

        if ( cursor.moveToFirst() ) {
            return cursor.getLong(0);
        } else {
            return 0;
        }
    }

    /* If we provide a valid subject name return the index of the subject */
    public long longSubjectID( String subject ) {
        SQLiteDatabase sdb = db.getReadableDatabase();
        String[] columns = {SUBJECT_ID};
        String where = SUBJECT_NAME +" = ?";
        String[] whereArgs = {subject};
        String limit = "1";
        Cursor cursor = sdb.query(false, TABLE_SUBJECT, columns, where, whereArgs, null, null, null, limit );

        cursor.moveToFirst();
        return cursor.getLong( 0 );
    }

    public void close() {
        db.close();
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
                REGISTERS_START + " INTEGER UNIQUE NOT NULL, " +
                REGISTERS_END + " INTEGER NOT NULL, " +
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
