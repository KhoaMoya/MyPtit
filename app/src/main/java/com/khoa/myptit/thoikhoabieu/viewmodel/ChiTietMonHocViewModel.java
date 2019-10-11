package com.khoa.myptit.thoikhoabieu.viewmodel;

/*
 * Created at 10/6/19 12:08 PM by Khoa
 */

import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.thoikhoabieu.model.MonHoc;

public class ChiTietMonHocViewModel extends ViewModel {

    public ObservableInt mShowInfo;
    public ObservableInt mShowNothing;
    public MonHoc mMonHoc;

    public void init(MonHoc mMonHoc) {
        this.mMonHoc = mMonHoc;
        if (mMonHoc == null) {
            this.mShowInfo = new ObservableInt(View.GONE);
            this.mShowNothing = new ObservableInt(View.VISIBLE);
        } else {
            this.mShowInfo = new ObservableInt(View.VISIBLE);
            this.mShowNothing = new ObservableInt(View.GONE);
        }
    }

    public String getTenMH() {
        return (mMonHoc != null) ? mMonHoc.getTenMon() : "";
    }

    public String getNhom() {
        return (mMonHoc != null) ? mMonHoc.getMaMon() : "";
    }

    public String getLop() {
        return (mMonHoc != null) ? mMonHoc.getMaLop() : "";
    }

    public String getPhong() {
        return (mMonHoc != null) ? mMonHoc.getPhongHoc() : "";
    }

    public String getGiangVien() {
        return (mMonHoc != null) ? mMonHoc.getGiangVien() : "";
    }

    public String getTinChi() {
        return (mMonHoc != null) ? mMonHoc.getSoTinChi() : "";
    }

}
