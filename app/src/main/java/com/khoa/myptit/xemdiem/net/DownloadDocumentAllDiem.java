package com.khoa.myptit.xemdiem.net;

/*
 * Created at 10/21/19 9:22 PM by Khoa
 */

import android.util.Log;

import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.base.model.User;
import com.khoa.myptit.base.net.Downloader;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class DownloadDocumentAllDiem extends Downloader {

    public DownloadDocumentAllDiem(String tag, String mURL, User mUser) {
        super(tag, mURL, mUser);
    }

    /*

    __EVENTTARGET: ctl00$ContentPlaceHolder1$ctl00$lnkChangeview2
    __EVENTARGUMENT:
    __VIEWSTATE:
    __VIEWSTATEGENERATOR: CA0B0334
    ctl00$ContentPlaceHolder1$ctl00$txtChonHK:
     */

    @Override
    public void run() {
        try{
            mResponse = Jsoup.connect(mURL)
                    .data("__EVENTTARGET", "ctl00$ContentPlaceHolder1$ctl00$lnkChangeview2")
                    .data("__EVENTARGUMENT", "")
                    .data("__VIEWSTATE", mUser.getViewState())
                    .data("__VIEWSTATEGENERATOR", "CA0B0334")
                    .cookie("ASP.NET_SessionId", mUser.getCookie())
                    .header("Connection", "keep-alive")
                    .method(Connection.Method.POST)
                    .timeout(10000)
                    .execute();
        }catch (Exception e){
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, getClass().getName(), e));
            e.printStackTrace();
            this.mException = e;
        }

        EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.DOWNLOAD_FINNISH_DIEM, getTag(), this));
    }
}
