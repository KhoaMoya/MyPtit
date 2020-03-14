package com.khoa.myptit.thongbao.net;

/*
 * Created at 9/29/19 12:09 AM by Khoa
 */


import android.content.Context;
import android.util.Log;

import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.main.Utils;
import com.khoa.myptit.thongbao.model.TatCaThongBao;
import com.khoa.myptit.thongbao.model.ThongBao;
import com.khoa.myptit.thongbao.util.ParseResponse;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class ListThongBaoDownloader extends Thread {

    private final String mURL = "http://qldt.ptit.edu.vn/default.aspx?page=danhsachthongtin&type=0";

    public ListThongBaoDownloader(Context mContext) {
        super();
    }

    @Override
    public void run() {
        try {
            Document mDocument = Jsoup.connect(mURL).get();
            ArrayList<ThongBao> list = ParseResponse.convertToListThongBao(mDocument);
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.DOWNLOAD_FINNISH_NOTIFICATION, "list", new TatCaThongBao(list, Utils.getCurrentTime())));
        } catch (Exception e){
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, getClass().getName(), e));
            e.printStackTrace();
        }
    }
}
