package com.khoa.myptit.canhan.model;

/*
 * Created at 10/19/19 10:55 AM by Khoa
 */

import java.io.Serializable;

public class ThongTin implements Serializable {

    public static String mFileName = "thongtin.data";

    private String maSV;
    private String hoTen;
    private String noiSinh;
    private String ngaySinh;
    private String lop;
    private String nganh;
    private String khoa;
    private String khoaHoc;
    private String heDaoTao;
    private String coVanHocTap;

    public ThongTin() {
    }

    public ThongTin(String maSV, String hoTen, String noiSinh, String ngaySinh, String lop, String nganh, String khoa, String khoaHoc, String heDaoTao, String coVanHocTap) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.noiSinh = noiSinh;
        this.ngaySinh = ngaySinh;
        this.lop = lop;
        this.nganh = nganh;
        this.khoa = khoa;
        this.khoaHoc = khoaHoc;
        this.heDaoTao = heDaoTao;
        this.coVanHocTap = coVanHocTap;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNoiSinh() {
        return noiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getNganh() {
        return nganh;
    }

    public void setNganh(String nganh) {
        this.nganh = nganh;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getKhoaHoc() {
        return khoaHoc;
    }

    public void setKhoaHoc(String khoaHoc) {
        this.khoaHoc = khoaHoc;
    }

    public String getHeDaoTao() {
        return heDaoTao;
    }

    public void setHeDaoTao(String heDaoTao) {
        this.heDaoTao = heDaoTao;
    }

    public String getCoVanHocTap() {
        return coVanHocTap;
    }

    public void setCoVanHocTap(String coVanHocTap) {
        this.coVanHocTap = coVanHocTap;
    }
}
