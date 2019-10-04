package com.khoa.myptit.thoikhoabieu.model;

/*
 * Created at 10/2/19 1:59 PM by Khoa
 */

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class HocKy implements Serializable {

    public static String mFileName = "hocky.data";
    private String mMaHocKy;
    private String mTenHocKy;
    private ArrayList<Tuan> mListTuan;

    public HocKy() {
        this.mListTuan = new ArrayList<>();
        this.mTenHocKy = "";
        this.mMaHocKy = "";
    }

    public HocKy(String mMaHocKy, String mTenHocKy) {
        this.mMaHocKy = mMaHocKy;
        this.mTenHocKy = mTenHocKy;
        this.mListTuan = new ArrayList<>();
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

    public ArrayList<Tuan> getListTuan() {
        return mListTuan;
    }

    public void setListTuan(ArrayList<Tuan> mListTuan) {
        this.mListTuan = mListTuan;
    }

    @NonNull
    @Override
    public String toString() {
        return "Hoc Ky: " + mMaHocKy + " - " + mTenHocKy;
    }
}
