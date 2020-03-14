package com.khoa.myptit.thongbao.viewmodel;

/*
 * Created at 9/26/19 3:11 PM by Khoa
 */

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.base.model.User;
import com.khoa.myptit.base.repository.BaseRepository;
import com.khoa.myptit.thongbao.adapter.ThongBaoRecycleViewAdapter;
import com.khoa.myptit.thongbao.model.TatCaThongBao;
import com.khoa.myptit.thongbao.model.ThongBao;
import com.khoa.myptit.thongbao.net.ListThongBaoDownloader;

public class ThongBaoViewModel extends ViewModel {

    public MutableLiveData<TatCaThongBao> mTatCaThongBao;
    public ThongBaoRecycleViewAdapter mAdapter;
    private Context context;

    public void init(Context context) {
        this.context = context;
        mTatCaThongBao = new MutableLiveData<>(new TatCaThongBao());
        mAdapter = new ThongBaoRecycleViewAdapter();
    }

    public void loadThongBao() {
        TatCaThongBao tatCaThongBao = new BaseRepository<TatCaThongBao>().read(context, ThongBao.mFileName);
        if (tatCaThongBao != null) mTatCaThongBao.setValue(tatCaThongBao);
        else refreshThongBao();
    }

    public void updateThongBao(TatCaThongBao tatCaThongBao) {
        mAdapter.setThongBaos(tatCaThongBao.getListThongBao());
    }

    public void refreshThongBao() {
        new ListThongBaoDownloader(context).start();
    }
}
