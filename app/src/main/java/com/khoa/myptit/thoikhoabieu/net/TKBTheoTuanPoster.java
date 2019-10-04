package com.khoa.myptit.thoikhoabieu.net;

/*
 * Created at 9/30/19 9:46 AM by Khoa
 */

import android.util.Log;

import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.net.Downloader;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class TKBTheoTuanPoster extends Downloader {

    private String mMaHocKy;
    private String mTuan;

    public TKBTheoTuanPoster(String tag, String url, User user, String maHocKy, String tuan) {
        super(tag, url, user);
        this.mMaHocKy = maHocKy;
        this.mTuan = tuan;
    }


//    __EVENTTARGET: ctl00$ContentPlaceHolder1$ctl00$ddlTuan
//                   ctl00$ContentPlaceHolder1$ctl00$ddlChonNHHK
//    __EVENTARGUMENT:
//    __LASTFOCUS:
//    __VIEWSTATE:
//    __VIEWSTATEGENERATOR: CA0B0334
//    ctl00$ContentPlaceHolder1$ctl00$ddlChonNHHK: 20191
//    ctl00$ContentPlaceHolder1$ctl00$ddlLoai: 0
//    ctl00$ContentPlaceHolder1$ctl00$ddlTuan: Tuần 09 [Từ 07/10/2019 -- Đến 13/10/2019]

    @Override
    public void run() {
        try {
            mResponse = Jsoup.connect(mURL)
                    .data("ctl00$ContentPlaceHolder1$ctl00$ddlChonNHHK", mMaHocKy)
                    .data("ctl00$ContentPlaceHolder1$ctl00$ddlLoai", "0")
                    .data("ctl00$ContentPlaceHolder1$ctl00$ddlTuan", mTuan)
                    .data("__EVENTTARGET", "ctl00$ContentPlaceHolder1$ctl00$ddlTuan")
                    .data("__EVENTARGUMENT", "")
                    .data("__LASTFOCUS", "")
                    .data("__VIEWSTATE", mUser.getViewState())
                    .data("__VIEWSTATEGENERATOR", "CA0B0334")
                    .cookie("ASP.NET_SessionId", mUser.getCookie())
                    .header("Connection", "keep-alive")
                    .method(Connection.Method.POST)
                    .timeout(10000)
                    .execute();
        } catch (Exception e) {
            Log.e("Loi", e.getMessage());
        }

        EventBus.getDefault().post(this);
    }
}
