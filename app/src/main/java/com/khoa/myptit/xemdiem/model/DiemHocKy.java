package com.khoa.myptit.xemdiem.model;

/*
 * Created at 10/21/19 10:26 PM by Khoa
 */

import java.io.Serializable;
import java.util.ArrayList;

public class DiemHocKy implements Serializable {

    private String mTenHocKy;
    private ArrayList<DiemMonHoc> mListMonHoc;
    private String mDiemTB10;
    private String mDiemTB4;
    private String mDiemTBTichLuy10;
    private String mDiemTBTichLuy4;
    private String mSoTinChiDat;
    private String mSoTinChiTichLuy;

    public DiemHocKy(String tenHocKy) {
        this.mTenHocKy = tenHocKy;
        mListMonHoc = new ArrayList<>();
    }

    public String getTenHocKy() {
        return mTenHocKy;
    }

    public void setTenHocKy(String mTenHocKy) {
        this.mTenHocKy = mTenHocKy;
    }

    public ArrayList<DiemMonHoc> getListMonHoc() {
        return mListMonHoc;
    }

    public void setListMonHoc(ArrayList<DiemMonHoc> mListMonHoc) {
        this.mListMonHoc = mListMonHoc;
    }

    public String getDiemTB10() {
        return mDiemTB10;
    }

    public void setDiemTB10(String mDiemTB10) {
        this.mDiemTB10 = mDiemTB10;
    }

    public String getDiemTB4() {
        return mDiemTB4;
    }

    public void setDiemTB4(String mDiemTB4) {
        this.mDiemTB4 = mDiemTB4;
    }

    public String getDiemTBTichLuy10() {
        return mDiemTBTichLuy10;
    }

    public void setDiemTBTichLuy10(String mDiemTBTichLuy10) {
        this.mDiemTBTichLuy10 = mDiemTBTichLuy10;
    }

    public String getDiemTBTichLuy4() {
        return mDiemTBTichLuy4;
    }

    public void setDiemTBTichLuy4(String mDiemTBTichLuy4) {
        this.mDiemTBTichLuy4 = mDiemTBTichLuy4;
    }

    public String getSoTinChiDat() {
        return mSoTinChiDat;
    }

    public void setSoTinChiDat(String mSoTinChiDat) {
        this.mSoTinChiDat = mSoTinChiDat;
    }

    public String getSoTinChiTichLuy() {
        return mSoTinChiTichLuy;
    }

    public void setSoTinChiTichLuy(String mSoTinChiTichLuy) {
        this.mSoTinChiTichLuy = mSoTinChiTichLuy;
    }
}
