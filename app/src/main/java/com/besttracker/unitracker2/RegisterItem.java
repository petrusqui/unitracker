package com.besttracker.unitracker2;

import java.util.Calendar;

/**
 * Created by psinc_000 on 01/03/2015.
 */
public class RegisterItem {

    private Calendar startTime;
    private Calendar endTime;
    private long subjectId;

    RegisterItem( Calendar start, Calendar end, long subject ) {
        startTime = start;
        endTime = end;
        subjectId = subject;
    }

    Calendar getStartTime() {
        return startTime;
    }

    Calendar getEndTime() {
        return endTime;
    }

    long getSubjectId() {
        return subjectId;
    }
}
