package com.khoa.myptit.thoikhoabieu.model;

/*
 * Created at 10/2/19 1:59 PM by Khoa
 */

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Semester implements Serializable {

    public static String mFileName = "hocky.data";
    private String mMaHocKy;
    private String mTenHocKy;
    private ArrayList<Week> mListWeek;
    private String mLastUpdate;

    public Semester() {
        this.mListWeek = new ArrayList<>();
        this.mTenHocKy = "";
        this.mMaHocKy = "";
        this.mLastUpdate = "";
    }

    public Semester(String mMaHocKy, String mTenHocKy) {
        this.mMaHocKy = mMaHocKy;
        this.mTenHocKy = mTenHocKy;
        this.mListWeek = new ArrayList<>();
    }

    public String getLastUpdate() {
        return mLastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.mLastUpdate = lastUpdate;
    }

    public String getMaHocKy() {
        return mMaHocKy;
    }

    public void setMaHocKy(String mMaHocKy) {
        this.mMaHocKy = mMaHocKy;
    }

    public String getTenHocKy() {
        return mTenHocKy;
    }

    public void setTenHocKy(String mTenHocKy) {
        this.mTenHocKy = mTenHocKy;
    }

    public ArrayList<Week> getListTuan() {
        return mListWeek;
    }

    public void setListWeek(ArrayList<Week> mListWeek) {
        this.mListWeek = mListWeek;
    }

    @NonNull
    @Override
    public String toString() {
        return "Hoc Ky: " + mMaHocKy + " - " + mTenHocKy;
    }
}
