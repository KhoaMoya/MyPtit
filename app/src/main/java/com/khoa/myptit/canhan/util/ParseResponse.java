package com.khoa.myptit.canhan.util;

/*
 * Created at 10/19/19 10:51 AM by Khoa
 */

import com.khoa.myptit.canhan.model.ThongTin;
import com.khoa.myptit.login.repository.BaseRepository;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ParseResponse {

    public static ThongTin convertToThongTin(Document document){
        ThongTin thongTin = new ThongTin();

        Element elementMaSV = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblMaSinhVien]").first();
        if(elementMaSV!=null) thongTin.setMaSV(elementMaSV.text());

        Element elementTenSV = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblTenSinhVien]").first();
        if(elementTenSV!=null) thongTin.setHoTen(elementTenSV.text());

        Element elementNoiSinh = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblNoiSinh]").first();
        if(elementNoiSinh!=null) thongTin.setNoiSinh(elementNoiSinh.text());

        Element elementNgaySinh = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblNgaySinh]").first();
        if(elementNgaySinh!=null) thongTin.setNgaySinh(elementNgaySinh.text());

        Element elementLop = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblLop]").first();
        if(elementLop!=null) thongTin.setLop(elementLop.text());

        Element elementNganh = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lbNganh]").first();
        if(elementNganh!=null) thongTin.setNganh(elementNganh.text());

        Element elementKhoa = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblKhoa]").first();
        if(elementKhoa!=null) thongTin.setKhoa(elementKhoa.text());

        Element elementHeDt = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblHeDaoTao]").first();
        if(elementHeDt!=null) thongTin.setHeDaoTao(elementHeDt.text());

        Element elementKhoaHoc = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblKhoaHoc]").first();
        if(elementKhoaHoc!=null) thongTin.setKhoaHoc(elementKhoaHoc.text());

        Element elementCvht = document.select("span[id=ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblCVHT]").first();
        if(elementCvht!=null) thongTin.setCoVanHocTap(elementCvht.text());

        return thongTin;
    }

}
