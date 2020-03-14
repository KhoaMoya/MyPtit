package com.khoa.myptit.thoikhoabieu.model;

/*
 * Created at 9/29/19 11:25 AM by Khoa
 */

import com.khoa.scheduleview.SimpleSubject;

import java.io.Serializable;

public class Subject extends SimpleSubject {

    public String subjectCode;
    public String classCode;
    public String group;
    public String soTinChi;
    public String teacher;
    public String startDate;
    public String endDate;
    public String tuition;

    public Subject() {
    }

    public Subject(int day, int startLesson, int durationLesson, String subjectName, String roomName) {
        super(day, startLesson, durationLesson, subjectName, roomName);
    }


    }
