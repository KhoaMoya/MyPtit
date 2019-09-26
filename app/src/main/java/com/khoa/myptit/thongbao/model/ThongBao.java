package com.khoa.myptit.thongbao.model;

/*
 * Created at 9/26/19 3:11 PM by Khoa
 */

import java.io.Serializable;

public class ThongBao implements Serializable {

    public static String mFileName = "thongbao.data";
    private String mTitle;
    private String mContent;
    private String mTime;
    private String mLink;

    public ThongBao() {
    }

    public ThongBao(String mTitle, String mContent, String mTime, String mLink) {
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mTime = mTime;
        this.mLink = mLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }
}
