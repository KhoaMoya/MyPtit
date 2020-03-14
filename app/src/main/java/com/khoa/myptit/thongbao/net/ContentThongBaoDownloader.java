package com.khoa.myptit.thongbao.net;

/*
 * Created at 9/29/19 12:10 AM by Khoa
 */

import android.util.Log;

import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.thongbao.util.ParseResponse;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class ContentThongBaoDownloader extends Thread {

    private String mURL;

    public ContentThongBaoDownloader(String mURL) {
        this.mURL = mURL;
    }

    @Override
    public void run() {
        try {
            Document mDocument = Jsoup.connect(mURL).get();
            String content = ParseResponse.convertToContent(mDocument);
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.DOWNLOAD_FINNISH_DETAIL_NOTIFICATION, "detail", content));
        } catch (Exception e){
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, getClass().getName(), e));
            e.printStackTrace();
        }
    }
}
