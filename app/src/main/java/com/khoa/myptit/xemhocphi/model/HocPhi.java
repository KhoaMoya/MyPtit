package com.khoa.myptit.xemhocphi.model;

/*
 * Created at 10/20/19 7:19 PM by Khoa
 */

import com.khoa.myptit.thoikhoabieu.model.MonHoc;

import java.io.Serializable;
import java.util.ArrayList;

public class HocPhi implements Serializable {


    public static String mFileName = "hocphi.data";
    private String mTongSoTinChi;
    private String mTongSoTinChiHocPhi;
    private String mTongSoTienHocPhiHocKy;
    private String mSoTienDongToiThieuLanDau;
    private String mSoTienDaDongTrongHocKy;
    private String mSoTienConNo;
    private ArrayList<MonHoc> mListMonHoc;
    private String mLastUpdate;
    private String mSoTaiKhoan;

    public HocPhi() {
        mListMonHoc = new ArrayList<>();
    }

    public String getTongSoTinChi() {
        return mTongSoTinChi;
    }

    public void setTongSoTinChi(String mTongSoTinChi) {
        this.mTongSoTinChi = mTongSoTinChi;
    }

    public String getTongSoTinChiHocPhi() {
        return mTongSoTinChiHocPhi;
    }

    public void setTongSoTinChiHocPhi(String mTongSoTinChiHocPhi) {
        this.mTongSoTinChiHocPhi = mTongSoTinChiHocPhi;
    }

    public String getTongSoTienHocPhiHocKy() {
        return mTongSoTienHocPhiHocKy;
    }

    public void setTongSoTienHocPhiHocKy(String mTongSoTienHocPhiHocKy) {
        this.mTongSoTienHocPhiHocKy = mTongSoTienHocPhiHocKy;
    }

    public String getSoTienDongToiThieuLanDau() {
        return mSoTienDongToiThieuLanDau;
    }

    public void setSoTienDongToiThieuLanDau(String mSoTienDongToiThieuLanDau) {
        this.mSoTienDongToiThieuLanDau = mSoTienDongToiThieuLanDau;
    }

    public String getSoTienDaDongTrongHocKy() {
        return mSoTienDaDongTrongHocKy;
    }

    public void setSoTienDaDongTrongHocKy(String mSoTienDaDongTrongHocKy) {
        this.mSoTienDaDongTrongHocKy = mSoTienDaDongTrongHocKy;
    }

    public String getSoTienConNo() {
        return mSoTienConNo;
    }

    public void setSoTienConNo(String mSoTienConNo) {
        this.mSoTienConNo = mSoTienConNo;
    }

    public ArrayList<MonHoc> getListMonHoc() {
        return mListMonHoc;
    }

    public void setListMonHoc(ArrayList<MonHoc> mListMonHoc) {
        this.mListMonHoc = mListMonHoc;
    }

    public String getLastUpdate() {
        return mLastUpdate;
    }

    public void setLastUpdate(String mLastUpdate) {
        this.mLastUpdate = mLastUpdate;
    }

    public String getSoTaiKhoan() {
        return mSoTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        this.mSoTaiKhoan = soTaiKhoan;
    }
}
