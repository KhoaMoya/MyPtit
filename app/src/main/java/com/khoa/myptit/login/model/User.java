package com.khoa.myptit.login.model;

import java.io.Serializable;


/*
 * Created at 9/22/19 3:00 PM by Khoa
 */
public class User implements Serializable {


    public final static String mFileName = "user.data";
    public final static String mKeyCookie = "ASP.NET_SessionId";

    private String cookie;
    private String maSV;
    private String maKhau;
    private String hoTen;
    private String noiSinh;
    private String ngaySinh;
    private String lop;
    private String nganh;
    private String khoa;
    private String heDaoTao;
    private String coVanHocTap;
    private String mViewState;

    private static User mInstance;

    public static User getInstance(){
        if(mInstance == null){
            mInstance = new User();
        }
        return mInstance;
    }

    public String getViewState() {
        return mViewState;
    }

    public void setViewState(String mViewState) {
        this.mViewState = mViewState;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getMaKhau() {
        return maKhau;
    }

    public void setMaKhau(String maKhau) {
        this.maKhau = maKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNoiSinh() {
        return noiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getNganh() {
        return nganh;
    }

    public void setNganh(String nganh) {
        this.nganh = nganh;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getHeDaoTao() {
        return heDaoTao;
    }

    public void setHeDaoTao(String heDaoTao) {
        this.heDaoTao = heDaoTao;
    }

    public String getCoVanHocTap() {
        return coVanHocTap;
    }

    public void setCoVanHocTap(String coVanHocTap) {
        this.coVanHocTap = coVanHocTap;
    }

    public static void setInstance(User user) {
        mInstance = user;
    }
}
