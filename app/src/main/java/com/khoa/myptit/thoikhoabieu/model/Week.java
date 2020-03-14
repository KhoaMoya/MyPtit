package com.khoa.myptit.thoikhoabieu.model;

/*
 * Created at 9/29/19 11:23 AM by Khoa
 */

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Week implements Serializable {

    private int id;
    private String mTenTuan;
    private String mValue;
    private String mNgayBatDau;
    private String mNgayKetThuc;
    private ArrayList<Subject> mSubjects;

    public Week(String mTenTuan, String mValue, String mNgayBatDau, String mNgayKetThuc) {
        this.mTenTuan = mTenTuan;
        this.mValue = mValue;
        this.mNgayBatDau = mNgayBatDau;
        this.mNgayKetThuc = mNgayKetThuc;
        mSubjects = new ArrayList<>();
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String mValue) {
        this.mValue = mValue;
    }

    public String getTenTuan() {
        return mTenTuan;
    }

    public void setTenTuan(String mTenTuan) {
        this.mTenTuan = mTenTuan;
    }

    public String getNgayBatDau() {
        return mNgayBatDau;
    }

    public void setNgayBatDau(String mNgayBatDau) {
        this.mNgayBatDau = mNgayBatDau;
    }

    public String getNgayKetThuc() {
        return mNgayKetThuc;
    }

    public void setNgayKetThuc(String mNgayKetThuc) {
        this.mNgayKetThuc = mNgayKetThuc;
    }

    public ArrayList<Subject> getSubjects() {
        return mSubjects;
    }

    public void setSubjects(ArrayList<Subject> mSubjects) {
        this.mSubjects = mSubjects;
    }

    @NonNull
    @Override
    public String toString() {
        return mTenTuan + ": " + mNgayBatDau + " - " + mNgayKetThuc;
    }
}
