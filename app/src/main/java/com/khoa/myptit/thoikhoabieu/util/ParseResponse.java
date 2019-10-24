package com.khoa.myptit.thoikhoabieu.util;

/*
 * Created at 9/28/19 11:37 PM by Khoa
 */

import android.content.Context;
import android.util.Log;

import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.net.Downloader;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.thoikhoabieu.model.HocKy;
import com.khoa.myptit.thoikhoabieu.model.MonHoc;
import com.khoa.myptit.thoikhoabieu.model.Tuan;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ParseResponse {

    public static ArrayList<Tuan> getListTuan(Downloader downloader) {
        try {
            ArrayList<Tuan> listTuan = new ArrayList<>();
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
                Log.e("Loi", ngayKT);
                String value = op.val();
                Tuan tuan = new Tuan(tenTuan, value, ngayBD, ngayKT);
                listTuan.add(tuan);
            }
            return listTuan;
        } catch (Exception e) {
            Log.e("Loi", e.getMessage());
            return new ArrayList<>();
        }
    }

    public static HocKy getHocKy(Downloader downloader) {
        try {
            Document document = Jsoup.parse(downloader.getResponse().body());
            Element selectHocKy = document.select("select[id=ctl00_ContentPlaceHolder1_ctl00_ddlChonNHHK]").first();
            Element selectedHocKy = selectHocKy.select("option[selected]").first();
            return new HocKy(selectedHocKy.val(), selectedHocKy.text());
        } catch (Exception e) {
            return new HocKy();
        }
    }

    public static MonHoc[][] parseDocumentTKB(Context mContent, Downloader downloader) {

//        getListHocKy(downloader);

        ArrayList<MonHoc> listMH = new ArrayList<>();
        Document document = Jsoup.parse(downloader.getResponse().body());

        // tìm ngày bắt đầu của tuần
        String ngayBatDauTuan = "";
        Element select = document.select("select[id=ctl00_ContentPlaceHolder1_ctl00_ddlTuan]").first();
        Element selectedOption = select.select("option[selected=selected]").first();
        if(selectedOption!=null) ngayBatDauTuan = selectedOption.text().substring(12, 22);


        // update viewstate
        Element viewState = document.select("input[id=__VIEWSTATE]").first();
        User user = new BaseRepository<User>().read(mContent, User.mFileName);
        user.setViewState(viewState.val());
        new BaseRepository<User>().write(mContent, User.mFileName, user);


//        Element selectTuan = document.select("select[id=ctl00_ContentPlaceHolder1_ctl00_ddlTuan]").first();
//        Element selectedTuan = selectTuan.select("option[selected=selected]").first();
//        Log.e("Loi", "Tuan: " + selectTuan.toString());
//        Log.e("Loi", selectTuan.size()+"");
//        Elements options = selectTuan.select("option");
//        for(Element option : options)
//            Log.e("Loi", option.toString());


//        Log.e("Loi", document.text());
        Element table = document.select("table[id=ctl00_ContentPlaceHolder1_ctl00_Table1]").first();
//        Log.e("Loi", table.text());
        Elements kips = table.select("td[onmouseover]");
        for (Element kip : kips) {

            String content = kip.attr("onmouseover");
            content = content.replace("ddrivetip(", "");
            content = content.replace(")", "");
            String[] arr = content.split(",");
            MonHoc monHoc = new MonHoc();
            monHoc.setMaLop(arr[0].replace("'", ""));
            monHoc.setTenMon(arr[1].replace("'", ""));
            monHoc.setMaMon(arr[2].replace("'", ""));
            monHoc.setThu(arr[3].replace("'", ""));
            monHoc.setSoTinChi(arr[4].replace("'", ""));
            monHoc.setPhongHoc(arr[5].replace("'", ""));
            monHoc.setTietBatDau(arr[6].replace("'", ""));
            monHoc.setSoTiet(arr[7].replace("'", ""));
            monHoc.setGiangVien(arr[8].replace("'", ""));
            monHoc.setNgayBatDau(arr[9].replace("'", ""));
            monHoc.setNgayKetThuc(arr[10].replace("'", ""));

            listMH.add(monHoc);
        }

        MonHoc[][] arrMonHoc = new MonHoc[13][8];
        for (int tiet = 1; tiet <= 12; tiet++) {
            for (int thu = 2; thu <= 7; thu++) {
                MonHoc monHoc = getMH(listMH, ngayBatDauTuan, tiet, thu);
                arrMonHoc[tiet][thu] = monHoc;
            }
        }

        return arrMonHoc;
    }


    public static MonHoc getMH(ArrayList<MonHoc> list, String ngayBatDauTuan, int tiet, int thu) {
        String[] arrThu = {"Thứ 0", "Thứ Một", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy", "Chủ Nhật"};
        ArrayList<String> listThu = new ArrayList<>(Arrays.asList(arrThu));

        for (int i = 0; i < list.size(); i++) {
            MonHoc monHoc = list.get(i);
            if (listThu.indexOf(monHoc.getThu()) == thu) {
                int tietKT = Integer.valueOf(monHoc.getTietBatDau()) + Integer.valueOf(monHoc.getSoTiet()) - 1;
                int tietBD = Integer.valueOf(monHoc.getTietBatDau());
                if (tiet <= tietKT && tiet >= tietBD) {
                    monHoc.setNgay(addDate(ngayBatDauTuan, thu - 2));
                    monHoc.setTiet(tiet);
                    return monHoc;
                }
            }
        }

        return new MonHoc(listThu.get(thu), tiet, addDate(ngayBatDauTuan, thu - 2));
    }

    public static int getCurrentTuan(ArrayList<Tuan> list) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date currentDate = formatter.parse(formatter.format(new Date()));

            for (int i = 0; i < list.size(); i++) {
                Tuan tuan = list.get(i);
                Date bdDate = formatter.parse(tuan.getNgayBatDau());
                Date ktDate = formatter.parse(tuan.getNgayKetThuc());
                if (currentDate.compareTo(bdDate) >= 0 && currentDate.compareTo(ktDate) <= 0) {
                    return i;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String addDate(String startDate, int numberDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String resultDate = "";
        try {
            Date date = df.parse(startDate);
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(date);
            cal.add(GregorianCalendar.DATE, numberDate);
            resultDate = df.format(cal.getTime());
        } catch (ParseException e) {
            Log.e("Loi", "Loi parse date: " + startDate );
        }
//        Log.e("Loi", startDate + " + " + numberDate + " = " + resultDate);
        return resultDate;
    }

}
