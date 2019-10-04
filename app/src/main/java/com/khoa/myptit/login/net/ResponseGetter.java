package com.khoa.myptit.login.net;



import android.util.Log;

import com.khoa.myptit.login.model.User;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

/*
 * Created at 9/24/19 1:51 PM by Khoa
 */

public class ResponseGetter extends Downloader {


    public ResponseGetter(String tag, String mURL, User mUser) {
        super(tag, mURL, mUser);
    }

    @Override
    public void run() {
        try {
            mResponse = Jsoup.connect(mURL)
                    .cookie("ASP.NET_SessionId", mUser.getCookie())
                    .method(Connection.Method.GET)
                    .execute();

        } catch (Exception e){
            mError = e.getMessage();
            Log.e("Loi", "ResponseGetter: " + e.getMessage());
        }
        EventBus.getDefault().post(this);
    }
}
