package com.besttracker.unitracker2;

/**
 * Created by psinc_000 on 01/03/2015.
 */
public class RegisterItem {

    private String startTime;
    private String endTime;
    private long subjectId;

    RegisterItem( String start, String end, long subject ) {
        startTime = start;
        endTime = end;
        subjectId = subject;
    }

    String getStartTime() {
        return startTime;
    }

    String getEndTime() {
        return endTime;
    }

    long getSubjectId() {
        return subjectId;
    }
}
