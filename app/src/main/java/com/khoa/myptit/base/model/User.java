package com.khoa.myptit.base.model;

import java.io.Serializable;


/*
 * Created at 2/26/20 4:22 PM by Khoa
 */

/*
 * Created at 9/22/19 3:00 PM by Khoa
 */
public class User implements Serializable {


    public final static String mFileName = "user.data";
    public final static String mKeyCookie = "ASP.NET_SessionId";

    private String cookie;
    private String maSV;
    private String maKhau;

    private String mViewState;

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

}
