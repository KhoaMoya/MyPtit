package com.khoa.myptit.base.net;

/*
 * Created at 9/23/19 2:05 PM by Khoa
 */

import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.base.model.User;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;


public class LoginPoster extends Downloader {

    private final static String mURL = "http://qldt.ptit.edu.vn/default.aspx?page=gioithieu";

    public LoginPoster(String tag, User mUser) {
        super(tag, mURL, mUser);
    }

    @Override
    public void run() {
        try {
            mResponse = Jsoup.connect(mURL)
                    .data("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtMatKhau", mUser.getMaKhau())
                    .data("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtTaiKhoa", mUser.getMaSV())
                    .data("__EVENTARGUMENT", "")
                    .data("__VIEWSTATE", "")
                    .data("__VIEWSTATEGENERATOR", "")
                    .data("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$btnDangNhap", "")
                    .header("Connection", "keep-alive")
                    .method(Connection.Method.POST)
                    .timeout(10000)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, getClass().getName(), e));
            mException = e;
        }

        EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.LOGIN_FINNISH, getTag(), this));
    }
}
