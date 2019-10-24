package com.khoa.myptit.thongbao.net;

/*
 * Created at 9/29/19 12:09 AM by Khoa
 */


import android.content.Context;
import android.util.Log;

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
    private Context mContext;

    public ListThongBaoDownloader(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public void run() {
        try {
            Document mDocument = Jsoup.connect(mURL).get();
            ArrayList<ThongBao> list = ParseResponse.convertToListThongBao(mContext, mDocument);
            EventBus.getDefault().post(new TatCaThongBao(list, Utils.getCurrentTime()));
        } catch (Exception e){
            Log.e("Loi", "Download tat ca thong bao: " + e.getMessage());
        }
    }
}
