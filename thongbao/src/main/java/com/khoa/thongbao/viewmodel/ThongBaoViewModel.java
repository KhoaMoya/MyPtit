package com.khoa.thongbao.viewmodel;

/*
 * Created at 9/25/19 11:29 PM by Khoa
 */

/*
 * Created at 9/25/19 11:28 PM by Khoa
 */

import java.io.Serializable;

public class ThongBaoViewModel implements Serializable {

    private String mTitle;
    private String mTime;
    private String mContent;

    public ThongBaoViewModel(String mTitle, String mTime, String mContent) {
        this.mTitle = mTitle;
        this.mTime = mTime;
        this.mContent = mContent;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }
}
