package com.khoa.myptit.thongbao.model;

/*
 * Created at 10/18/19 10:51 AM by Khoa
 */

import java.io.Serializable;
import java.util.ArrayList;

public class TatCaThongBao implements Serializable {

    private ArrayList<ThongBao> mListThongBao;
    private String mLastUpdate;


    public TatCaThongBao() {
        mListThongBao = new ArrayList<>();
        mLastUpdate = "";
    }

    public TatCaThongBao(ArrayList<ThongBao> mListThongBao, String mLastUpdate) {
        this.mListThongBao = mListThongBao;
        this.mLastUpdate = mLastUpdate;
    }

    public ArrayList<ThongBao> getListThongBao() {
        return mListThongBao;
    }

    public void setListThongBao(ArrayList<ThongBao> listThongBao) {
        this.mListThongBao = listThongBao;
    }

    public String getLastUpdate() {
        return mLastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.mLastUpdate = lastUpdate;
    }
}
