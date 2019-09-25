package com.khoa.myptit.net;

/*
 * Created at 9/24/19 1:51 PM by Khoa
 */

import android.util.Log;

import com.khoa.myptit.baseModel.User;
import com.khoa.myptit.util.ParseRespone;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class DocumentGetter extends Downloader {


    public DocumentGetter(String mURL, User mUser) {
        super(mURL, mUser);
    }

    @Override
    public void run() {
        try {
            mResponse = Jsoup.connect(mURL)
                    .cookie("ASP.NET_SessionId", mUser.getCookie())
                    .method(Connection.Method.GET)
                    .timeout(10000)
                    .execute();


        } catch (Exception e){
            mError = e.getMessage();
            Log.e("Loi", "DocumentGetter: " + e.getMessage());
        }
        EventBus.getDefault().post(this);
    }
}
