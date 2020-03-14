package com.khoa.myptit.xemhocphi.util;

/*
 * Created at 10/20/19 7:37 PM by Khoa
 */

import android.content.Context;
import android.util.Log;

import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.base.net.Downloader;
import com.khoa.myptit.base.repository.BaseRepository;
import com.khoa.myptit.thoikhoabieu.model.Subject;
import com.khoa.myptit.xemhocphi.model.HocPhi;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHocPhi {

    public static HocPhi convertToHocPhi(Context context, Downloader downloader) {
        HocPhi hocPhi = new HocPhi();

        try {
            Document document = Jsoup.parse(downloader.getResponse().body());

            // số tài khoản
            Element elementSoTK = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_lblSoTaiKhoan]").first();
            if (elementSoTK != null)
                hocPhi.setSoTaiKhoan(elementSoTK.text().substring(39, elementSoTK.text().length() - 1));

            Element table = document.select("table[id=ctl00_ContentPlaceHolder1_ctl00_gvHocPhi]").first();
            if (table != null) {
                Elements rows = table.select("tr");
                for (Element row : rows) {
                    Subject subject = new Subject();
                    Elements allColumn = row.select("td");
                    if (allColumn.size() > 9) {
                        if (!allColumn.get(2).text().isEmpty()) {
                            subject.subjectName = allColumn.get(2).text();
                            if (!allColumn.get(5).text().isEmpty())
                                subject.soTinChi = allColumn.get(5).text();
                            if (!allColumn.get(9).text().isEmpty())
                                subject.tuition = allColumn.get(9).text();
                            hocPhi.getListMonHoc().add(subject);
                        }
                    }
                }
            }

            Element elementTongSoTC = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_SoTinChiHP]").first();
            if (elementTongSoTC != null) hocPhi.setTongSoTinChi(elementTongSoTC.text());

            Element elementTongSoTinChiHocPhi = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_SoTinChi]").first();
            if (elementTongSoTinChiHocPhi != null)
                hocPhi.setTongSoTinChiHocPhi(elementTongSoTinChiHocPhi.text());

            Element elementTongSoTienHocPhiHocKy = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_lblphaiDong]").first();
            if (elementTongSoTienHocPhiHocKy != null)
                hocPhi.setTongSoTienHocPhiHocKy(elementTongSoTienHocPhiHocKy.text());

            Element elementSoTienDongToiThieuLanDau = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_lblDongLanDau1]").first();
            if (elementSoTienDongToiThieuLanDau != null)
                hocPhi.setSoTienDongToiThieuLanDau(elementSoTienDongToiThieuLanDau.text());

            Element elementSoTienDaDongTrongHocKy = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_lblDaDongHKOffline]").first();
            if (elementSoTienDaDongTrongHocKy != null)
                hocPhi.setSoTienDaDongTrongHocKy(elementSoTienDaDongTrongHocKy.text());

            Element elementConNoHocKy = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_lblConNoHocKy]").first();
            if (elementConNoHocKy != null) hocPhi.setSoTienConNo(elementConNoHocKy.text());

            // last update span id="ctl00_ContentPlaceHolder1_ctl00_lblNote"
            Element elementLastUpdate = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_lblNote]").first();
            if (elementLastUpdate != null)
                hocPhi.setLastUpdate(elementLastUpdate.text().substring(2, elementLastUpdate.text().length() - 2));
        }catch (Exception e){
            e.printStackTrace();
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, "convertToHocPhi", e));
        }

        return hocPhi;
    }
}
