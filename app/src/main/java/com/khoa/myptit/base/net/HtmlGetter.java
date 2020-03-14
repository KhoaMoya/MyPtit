package com.khoa.myptit.base.net;


import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.base.model.User;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

/*
 * Created at 9/24/19 1:51 PM by Khoa
 */

public class HtmlGetter extends Downloader {

    public HtmlGetter(String tag, String mURL, User mUser) {
        super(tag, mURL, mUser);
    }

    @Override
    public void run() {
        try {
            mResponse = Jsoup.connect(mURL)
                    .cookie("ASP.NET_SessionId", mUser.getCookie())
                    .method(Connection.Method.GET)
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, getClass().getName(), e));
            mException = e;
        }
        EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.DOWNLOAD_FINNISH, getTag(), this));
    }
}
