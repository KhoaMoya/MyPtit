package com.khoa.myptit.thongbao.viewmodel;

/*
 * Created at 9/26/19 3:11 PM by Khoa
 */

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.thongbao.adapter.ThongBaoRecycleViewAdapter;
import com.khoa.myptit.thongbao.model.TatCaThongBao;
import com.khoa.myptit.thongbao.model.ThongBao;
import com.khoa.myptit.thongbao.net.ListThongBaoDownloader;
import com.khoa.myptit.thongbao.view.ThongBaoFragment;

import java.util.ArrayList;

public class ThongBaoViewModel extends ViewModel {

    public MutableLiveData<TatCaThongBao> mTatCaThongBao;
    public ThongBaoRecycleViewAdapter mAdapter;
    private User mUser;
    private ThongBaoFragment mThongBaoFragment;
    public ObservableField<String> mLastUpdate;
    public ObservableInt showLoading;
    public ObservableInt showThongBao;

    public void init(ThongBaoFragment thongBaoFragment){
        mThongBaoFragment = thongBaoFragment;
        mTatCaThongBao = new MutableLiveData<>(new TatCaThongBao());
        mAdapter = new ThongBaoRecycleViewAdapter(this);
        mUser = new BaseRepository<User>().read(mThongBaoFragment.getContext(), User.mFileName);
        mLastUpdate = new ObservableField<>();
        showLoading = new ObservableInt(View.VISIBLE);
        showThongBao = new ObservableInt(View.GONE);
    }

    public void loadThongBao(){
        showLoading.set(View.VISIBLE);
        showThongBao.set(View.GONE);
        TatCaThongBao tatCaThongBao = new BaseRepository<TatCaThongBao>().read(mThongBaoFragment.getContext(), ThongBao.mFileName);
        if(tatCaThongBao!=null) mTatCaThongBao.setValue(tatCaThongBao);
        else refreshThongBao();
    }

    public ThongBaoFragment getFragment(){
        return mThongBaoFragment;
    }

    public void updateThongBao(TatCaThongBao tatCaThongBao){
        showLoading.set(View.GONE);
        showThongBao.set(View.VISIBLE);
        mAdapter.notifyDataSetChanged();
        mLastUpdate.set("Cập nhật lần cuối: " + tatCaThongBao.getLastUpdate());
    }

    public void refreshThongBao(){
            showLoading.set(View.VISIBLE);
            showThongBao.set(View.GONE);
            new ListThongBaoDownloader(mThongBaoFragment.getContext()).start();
    }

    public User getUser(){
        return mUser;
    }

    public ThongBaoRecycleViewAdapter getAdapter(){
        return mAdapter;
    }

    public ArrayList<ThongBao> getListThongBao(){
        return mTatCaThongBao.getValue().getListThongBao();
    }

    public String getTitleAt(int position){
        return mTatCaThongBao.getValue().getListThongBao().get(position).getTitle();
    }

    public String getTimeAt(int position){
        return mTatCaThongBao.getValue().getListThongBao().get(position).getTime();
    }
}
