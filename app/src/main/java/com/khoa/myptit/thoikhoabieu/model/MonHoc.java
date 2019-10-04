package com.khoa.myptit.thoikhoabieu.model;

/*
 * Created at 9/29/19 11:25 AM by Khoa
 */

import java.io.Serializable;

public class MonHoc implements Serializable {

    private String mMaMon;
    private String mMaLop;
    private String mTenMon;
    private String mPhongHoc;
    private String mNhom;
    private String mSoTinChi;
    private String mTietBatDau;
    private String mSoTiet;
    private String mThu;
    private String mLop;
    private String mGiangVien;
    private String mThi;
    private String mNgayBatDau;
    private String mNgayKetThuc;
    private String mTongKet4;
    private String mTongKet10;
    private String mTrangThai;
    private int mIdGhiChu;


    public MonHoc() {
    }

    public String getMaLop() {
        return mMaLop;
    }

    public void setMaLop(String mMaLop) {
        this.mMaLop = mMaLop;
    }

    public String getMaMon() {
        return mMaMon;
    }

    public String getThu() {
        return mThu;
    }

    public void setThu(String mThu) {
        this.mThu = mThu;
    }

    public String getTietBatDau() {
        return mTietBatDau;
    }

    public String getSoTiet() {
        return mSoTiet;
    }

    public void setSoTiet(String mSoTiet) {
        this.mSoTiet = mSoTiet;
    }

    public void setTietBatDau(String mTietBatDau) {
        this.mTietBatDau = mTietBatDau;
    }

    public void setMaMon(String mMaMon) {
        this.mMaMon = mMaMon;
    }

    public String getTenMon() {
        return mTenMon;
    }

    public void setTenMon(String mTenMon) {
        this.mTenMon = mTenMon;
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

    public String getPhongHoc() {
        return mPhongHoc;
    }

    public void setPhongHoc(String mPhongHoc) {
        this.mPhongHoc = mPhongHoc;
    }

    public String getNhom() {
        return mNhom;
    }

    public void setNhom(String mNhom) {
        this.mNhom = mNhom;
    }

    public String getSoTinChi() {
        return mSoTinChi;
    }

    public void setSoTinChi(String mSoTinChi) {
        this.mSoTinChi = mSoTinChi;
    }

    public String getLop() {
        return mLop;
    }

    public void setLop(String mLop) {
        this.mLop = mLop;
    }

    public String getGiangVien() {
        return mGiangVien;
    }

    public void setGiangVien(String mGiangVien) {
        this.mGiangVien = mGiangVien;
    }

    public String getThi() {
        return mThi;
    }

    public void setThi(String mThi) {
        this.mThi = mThi;
    }

    public String getTongKet4() {
        return mTongKet4;
    }

    public void setTongKet4(String mTongKet4) {
        this.mTongKet4 = mTongKet4;
    }

    public String getTongKet10() {
        return mTongKet10;
    }

    public void setTongKet10(String mTongKet10) {
        this.mTongKet10 = mTongKet10;
    }

    public String getTrangThai() {
        return mTrangThai;
    }

    public void setTrangThai(String mTrangThai) {
        this.mTrangThai = mTrangThai;
    }

    public int getIdGhiChu() {
        return mIdGhiChu;
    }

    public void setIdGhiChu(int mIdGhiChu) {
        this.mIdGhiChu = mIdGhiChu;
    }
}
