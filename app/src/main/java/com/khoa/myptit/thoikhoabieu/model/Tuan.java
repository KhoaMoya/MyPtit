package com.khoa.myptit.thoikhoabieu.model;

/*
 * Created at 9/29/19 11:23 AM by Khoa
 */

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Tuan implements Serializable {

    private String mTenTuan;
    private String mValue;
    private String mNgayBatDau;
    private String mNgayKetThuc;
    private MonHoc[][] mMonHocs;

    public Tuan(String mTenTuan, String mValue, String mNgayBatDau, String mNgayKetThuc) {
        this.mTenTuan = mTenTuan;
        this.mValue = mValue;
        this.mNgayBatDau = mNgayBatDau;
        this.mNgayKetThuc = mNgayKetThuc;
        mMonHocs = new MonHoc[13][8];
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

    public MonHoc[][] getMonHocs() {
        return mMonHocs;
    }

    public void setMonHocs(MonHoc[][] monhocs) {
        this.mMonHocs = monhocs;
    }

    @NonNull
    @Override
    public String toString() {
        return mTenTuan + ": " + mNgayBatDau + " - " + mNgayKetThuc;
    }
}
