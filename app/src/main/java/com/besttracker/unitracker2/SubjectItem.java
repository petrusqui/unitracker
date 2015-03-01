package com.besttracker.unitracker2;

import android.graphics.Color;

/**
 * Created by psinc_000 on 28/02/2015.
 */
public class SubjectItem {

    private int subjectColor;
    private String subjectName;
    private float recommendTime;

    public SubjectItem( String name, int pickedColor ) {
       subjectColor = pickedColor;
       subjectName = name;
    }

    public SubjectItem( String name, int pickedColor, float time ) {
        subjectName = name;
        subjectColor = pickedColor;
        recommendTime = time;
    }

    public String get_subject() {
        return subjectName;
    }

    public float get_time() {
        return recommendTime;
    }

    public int get_color() {
        return subjectColor;
    }
}
