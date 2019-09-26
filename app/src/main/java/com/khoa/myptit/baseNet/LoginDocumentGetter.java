package com.khoa.myptit.baseNet;

/*
 * Created at 9/23/19 2:05 PM by Khoa
 */

import android.util.Log;

import com.khoa.myptit.baseModel.User;
import com.khoa.myptit.baseNet.url.URL;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class LoginDocumentGetter extends Downloader{


    public LoginDocumentGetter(User mUser) {
        super(URL.URL_LOGIN, mUser);
    }

    @Override
    public void run() {
        try {
           mResponse = Jsoup.connect(mURL)
                    .data("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtMatKhau", mUser.getMaKhau())
                    .data("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtTaiKhoa", mUser.getMaSV())
                    .data("__EVENTTARGET", "")
                    .data("__EVENTARGUMENT", "")
                    .data("__VIEWSTATE", "")
                    .data("__VIEWSTATEGENERATOR", "")
                    .data("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$btnDangNhap", "")
                    .method(Connection.Method.POST)
                    .timeout(10000)
                    .execute();
        } catch (Exception e){
            mError = e.getMessage();
            Log.e("Loi", "LoginDocumentGetter: " + e.getMessage());
        }

        // post event download done
        EventBus.getDefault().post(this);
    }
}
