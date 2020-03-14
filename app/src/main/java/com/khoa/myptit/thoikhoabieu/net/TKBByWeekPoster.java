package com.khoa.myptit.thoikhoabieu.net;

/*
 * Created at 9/30/19 9:46 AM by Khoa
 */

import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.base.model.User;
import com.khoa.myptit.base.net.Downloader;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class TKBByWeekPoster extends Downloader {

    private String mMaHocKy;
    private String mTuan;

    public TKBByWeekPoster(String tag, String url, User user, String maHocKy, String tuan) {
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

//    __EVENTTARGET: ctl00$ContentPlaceHolder1$ctl00$ddlTuan
//    __EVENTARGUMENT:
//    __LASTFOCUS:
//    __VIEWSTATE:
//    __VIEWSTATEGENERATOR: CA0B0334
//    ctl00$ContentPlaceHolder1$ctl00$ddlChonNHHK: 20192
//    ctl00$ContentPlaceHolder1$ctl00$ddlLoai: 0
//    ctl00$ContentPlaceHolder1$ctl00$ddlTuan: Tuần 30 [Từ 02/03/2020 -- Đến 08/03/2020]

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
            e.printStackTrace();
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, getClass().getName(), e));
            this.mException = e;
        }

        EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.DOWNLOAD_FINNISH, getTag(), this));
    }
}
