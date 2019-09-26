package com.khoa.myptit.thongbao.util;

/*
 * Created at 9/26/19 3:38 PM by Khoa
 */

import android.content.Context;
import android.util.Log;

import com.khoa.myptit.baseNet.DocumentGetter;
import com.khoa.myptit.baseRepository.BaseRepository;
import com.khoa.myptit.thongbao.model.ThongBao;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ParseResponse {

    public static ArrayList<ThongBao> parseDocument(Context mContext, DocumentGetter documentGetter){

        ArrayList<ThongBao> listThongBao = new ArrayList<>();

//        if(!documentGetter.getError().equals("")) {
            try {
                Document document = documentGetter.getResponse().parse();
                Element elementBody = document.select("table[id=ctl00_ContentPlaceHolder1_ctl00_tbThongTin]").first();
                Elements mThongBaos = elementBody.select("a[href]");
                for (Element eThongBao : mThongBaos) {
                    String link = eThongBao.attr("abs:href");
                    Log.e("Loi", "link: " + link);

                    String content = eThongBao.text();
                    Log.e("Loi", "content: " + content);

                    if (content.length() > 0) {
                        String time = content.substring(content.length() - 12, content.length());
                        time = time.trim();
                        time = time.replace("(", "");
                        time = time.replace(")", "");
                        Log.e("Loi", "time: " + time);

                        String title = content.substring(0, content.length() - 13);
                        title = title.trim();
                        title = title.replace("...", "");
                        Log.e("Loi", "title: " + title);

//                        String detail = getDetail(link);

                        ThongBao thongBao = new ThongBao(title, "", time, link);
                        listThongBao.add(thongBao);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//        } else {
//            Log.e("Loi", "Loi jsoup thong bao: " + documentGetter.getError());
//        }

        new BaseRepository<ArrayList<ThongBao>>().write(mContext, ThongBao.mFileName, listThongBao);
            return listThongBao;
    }

    public static String getDetail(final String link){
        String content = "";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection.Response response = Jsoup.connect(link)
                            .method(Connection.Method.GET)
                            .timeout(10000)
                            .execute();

                    Document document = response.parse();
                    Element body = document.selectFirst("table[id=ctl00_ContentPlaceHolder1_ctl00_tbThongTin");
                    Log.e("Loi", body.text());

                }catch (Exception e){
                    Log.e("Loi", e.getMessage());
                }
            }
        }).start();
        return content;
    }
}
