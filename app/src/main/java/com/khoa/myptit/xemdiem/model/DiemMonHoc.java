package com.khoa.myptit.xemdiem.model;

/*
 * Created at 10/21/19 10:26 PM by Khoa
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class DiemMonHoc implements Serializable, Parcelable {

    public final static String mFileName = "diem.data";
    private String mMaMonHoc;
    private String mTenMonHoc;
    private String mSoTinChi;
    private String mThi;
    private String mTK10;
    private String mTK4;

    public DiemMonHoc(String mMaMonHoc, String mTenMonHoc, String mSoTinChi, String mThi, String mTK10, String mTK4) {
        this.mMaMonHoc = mMaMonHoc;
        this.mTenMonHoc = mTenMonHoc;
        this.mSoTinChi = mSoTinChi;
        this.mThi = mThi;
        this.mTK10 = mTK10;
        this.mTK4 = mTK4;
    }

    public String getMaMonHoc() {
        return mMaMonHoc;
    }

    public void setMaMonHoc(String mMaMonHoc) {
        this.mMaMonHoc = mMaMonHoc;
    }

    public String getTenMonHoc() {
        return mTenMonHoc;
    }

    public void setTenMonHoc(String mTenMonHoc) {
        this.mTenMonHoc = mTenMonHoc;
    }

    public String getSoTinChi() {
        return mSoTinChi;
    }

    public void setSoTinChi(String mSoTinChi) {
        this.mSoTinChi = mSoTinChi;
    }

    public String getTK10() {
        return mTK10;
    }

    public void setTK10(String mTK10) {
        this.mTK10 = mTK10;
    }

    public String getTK4() {
        return mTK4;
    }

    public void setTK4(String mTK4) {
        this.mTK4 = mTK4;
    }

    public String getThi() {
        return mThi;
    }

    public void setmThi(String mThi) {
        this.mThi = mThi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
