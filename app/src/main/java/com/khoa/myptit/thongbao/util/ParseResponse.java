package com.khoa.myptit.thongbao.util;

/*
 * Created at 9/26/19 3:38 PM by Khoa
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.khoa.myptit.login.net.DocumentGetter;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.thongbao.model.ThongBao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ParseResponse {

    public static ArrayList<ThongBao> parseDocument(Context mContext, DocumentGetter documentGetter) {

        ArrayList<ThongBao> listThongBao = new ArrayList<>();

        if(!documentGetter.getError().isEmpty()) {
            Toast.makeText(mContext, "Error parse document: " + documentGetter.getError(), Toast.LENGTH_SHORT).show();
            return listThongBao;
        }

        try {
//        Document document = Jsoup.parse(documentGetter.getResponse().body());
            Document document = documentGetter.getResponse().parse();
            Element elementBody = document.select("table[id=ctl00_ContentPlaceHolder1_ctl00_tbThongTin]").first();
            if (elementBody == null) return listThongBao;

            Elements mThongBaos = elementBody.select("a[href]");
            if (mThongBaos == null) return listThongBao;

            for (Element eThongBao : mThongBaos) {
                String link = eThongBao.absUrl("href");
                link = link.trim();
                Log.e("Loi", "link: " + link);

                String content = eThongBao.text();
                Log.e("Loi", "content: " + content);

                if (content.length() > 0) {
                    String time = content.substring(content.length() - 12);
                    time = time.trim();
                    time = time.replace("(", "");
                    time = time.replace(")", "");
                    Log.e("Loi", "time: " + time);

                    String title = content.substring(0, content.length() - 13);
                    title = title.trim();
                    title = title.replace("...", "");
                    Log.e("Loi", "title: " + title);

                    ThongBao thongBao = new ThongBao(title, "", time, link);
                    listThongBao.add(thongBao);
                }
            }
        }catch (Exception e){
            Log.e("Loi", "Loi parse document: " + e.getMessage());
        }
        new BaseRepository<ArrayList<ThongBao>>().write(mContext, ThongBao.mFileName, listThongBao);
        return listThongBao;
    }

    public static String getDetail(Context mContext, DocumentGetter documentGetter) {
        String content = "";

        Document document = Jsoup.parse(documentGetter.getResponse().body());

        Elements elements = document.select("td[class=TextThongTin]");
        Element contain = elements.get(1);
        Elements items = contain.select("p");
        for(Element e : items ){
            content = content + "\n" + e.text();
        }
        return content;
    }
}
