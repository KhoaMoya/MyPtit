package com.khoa.myptit.thoikhoabieu.util;

/*
 * Created at 9/28/19 11:37 PM by Khoa
 */

import android.content.Context;

import com.khoa.myptit.base.dialog.MyDialog;
import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.base.model.User;
import com.khoa.myptit.base.net.Downloader;
import com.khoa.myptit.base.repository.BaseRepository;
import com.khoa.myptit.base.util.ParseResponse;
import com.khoa.myptit.thoikhoabieu.model.Semester;
import com.khoa.myptit.thoikhoabieu.model.Subject;
import com.khoa.myptit.thoikhoabieu.model.Week;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ParseResponseTKB extends ParseResponse {

    public static ArrayList<Week> getListTuan(Context context, Downloader downloader) {
        ArrayList<Week> listWeek = new ArrayList<>();
        if(downloader==null) return listWeek;
        if(downloader.getException()!=null){
//            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, "ParseResponseTKB : getListTuan", downloader.getException()));
            return listWeek;
        }
        try {
            Document document = Jsoup.parse(downloader.getResponse().body());

            Element select = document.select("select[id=ctl00_ContentPlaceHolder1_ctl00_ddlTuan]").first();
            Elements options = select.select("option");
            for (Element op : options) {
//            Tuần 01 [Từ 12/08/2019 -- Đến 18/08/2019]
//            01234567890123456789012345678901234567890
                String string = op.text();
                String tenTuan = string.substring(0, 7);
                String ngayBD = string.substring(12, 22);
                String ngayKT = string.substring(29, 40);
                String value = op.val();
                Week week = new Week(tenTuan, value, ngayBD, ngayKT);
                listWeek.add(week);
            }
            return listWeek;
        } catch (Exception e) {
            e.printStackTrace();
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, "ParseResponseTKB : getListTuan",e));
        }
        return listWeek;
    }

    public static Semester getHocKy(Context context, Downloader downloader) {
        if(downloader==null) return new Semester();
        if(downloader.getException()!=null){
//            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, "ParseResponseTKB : getHocKy", downloader.getException()));
            return new Semester();
        }
        try {
            Document document = Jsoup.parse(downloader.getResponse().body());
            Element selectHocKy = document.select("select[id=ctl00_ContentPlaceHolder1_ctl00_ddlChonNHHK]").first();
            Element selectedHocKy = selectHocKy.select("option[selected]").first();
            return new Semester(selectedHocKy.val(), selectedHocKy.text());
        } catch (Exception e) {
            e.printStackTrace();
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, "ParseResponseTKB : getHocKy", e));
        }
        return new Semester();
    }

    public static ArrayList<Subject> parseDocumentTKB(Context context, Downloader downloader) {
        ArrayList<Subject> subjects = new ArrayList<>();
        if(downloader==null) return subjects;
        if(downloader.getException()!=null){
//            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, "ParseResponseTKB : parseDocumentTKB", downloader.getException()));
            return subjects;
        }
        try {
            Document document = Jsoup.parse(downloader.getResponse().body());

            // tìm ngày bắt đầu của tuần
            String ngayBatDauTuan = "";
            Element select = document.select("select[id=ctl00_ContentPlaceHolder1_ctl00_ddlTuan]").first();
            Element selectedOption = select.select("option[selected=selected]").first();
            if (selectedOption != null) ngayBatDauTuan = selectedOption.text().substring(12, 22);


            // update viewstate
            Element viewState = document.select("input[id=__VIEWSTATE]").first();
            User user = new BaseRepository<User>().read(context, User.mFileName);
            user.setViewState(viewState.val());
            new BaseRepository<User>().write(context, User.mFileName, user);


//        Element selectTuan = document.select("select[id=ctl00_ContentPlaceHolder1_ctl00_ddlTuan]").first();
//        Element selectedTuan = selectTuan.select("option[selected=selected]").first();
//        Log.e("Loi", "Week: " + selectTuan.toString());
//        Log.e("Loi", selectTuan.size()+"");
//        Elements options = selectTuan.select("option");
//        for(Element option : options)
//            Log.e("Loi", option.toString());


            Element table = document.select("table[id=ctl00_ContentPlaceHolder1_ctl00_Table1]").first();
            Elements kips = table.select("td[onmouseover]");

            String[] arrThu = {"Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy", "Chủ Nhật"};
            ArrayList<String> listThu = new ArrayList<>(Arrays.asList(arrThu));

            for (Element kip : kips) {

                String content = kip.attr("onmouseover");
                content = content.replace("ddrivetip(", "");
                content = content.replace(")", "");
                String[] arr = content.split(",");
                Subject subject = new Subject();
                subject.classCode = arr[0].replace("'", "");
                subject.subjectName = arr[1].replace("'", "");
                subject.subjectCode = arr[2].replace("'", "");
                subject.day = listThu.indexOf(arr[3].replace("'", "")) + 2;
                subject.soTinChi = arr[4].replace("'", "");
                subject.roomName = arr[5].replace("'", "");
                subject.startLesson = Integer.valueOf(arr[6].replace("'", ""));
                subject.durationLesson = Integer.valueOf(arr[7].replace("'", ""));
                subject.teacher = arr[8].replace("'", "");
                subject.startDate = arr[9].replace("'", "");
                subject.endDate = arr[10].replace("'", "");

                subjects.add(subject);
            }

        }catch (Exception e){
            e.printStackTrace();
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, "ParseResponseTKB : parseDocumentTKB", e));
        }
        return subjects;
    }


    public static int getCurrentWeekIndex(ArrayList<Week> list) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date currentDate = formatter.parse(formatter.format(new Date()));

            for (int i = 0; i < list.size(); i++) {
                Week week = list.get(i);
                Date bdDate = formatter.parse(week.getNgayBatDau());
                Date ktDate = formatter.parse(week.getNgayKetThuc());
                if (currentDate.compareTo(bdDate) >= 0 && currentDate.compareTo(ktDate) <= 0) {
                    return i;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, "ParseResponseTKB : getCurrentWeekIndex", e));
        }
        return 0;
    }
}
