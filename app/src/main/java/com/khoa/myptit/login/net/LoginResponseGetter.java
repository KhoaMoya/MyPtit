package com.khoa.myptit.login.net;

/*
 * Created at 9/23/19 2:05 PM by Khoa
 */

import android.util.Log;

import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.viewmodel.LoginViewModel;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;


public class LoginResponseGetter extends Downloader {

    private final static String mURL = "http://qldt.ptit.edu.vn/default.aspx?page=gioithieu";

    public LoginResponseGetter(User mUser) {
        super(LoginViewModel.TAG, mURL, mUser);
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

        } catch (Exception e){
            mError = e.getMessage();
            Log.e("Loi", "LoginResponseGetter: " + e.getMessage());
        }

        // post event download done
        EventBus.getDefault().post(this);
    }
}
