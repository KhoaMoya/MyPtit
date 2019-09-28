package com.khoa.myptit.thongbao.viewmodel;

/*
 * Created at 9/26/19 3:11 PM by Khoa
 */

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.net.DocumentGetter;
import com.khoa.myptit.login.net.URL;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.thongbao.adapter.ThongBaoRecycleViewAdapter;
import com.khoa.myptit.thongbao.model.ThongBao;
import com.khoa.myptit.thongbao.net.ListThongBaoDownloader;
import com.khoa.myptit.thongbao.view.FragmentThongBao;

import java.util.ArrayList;

public class ThongBaoViewModel extends ViewModel {

    public MutableLiveData<ArrayList<ThongBao>> mListThongBao;
    public ThongBaoRecycleViewAdapter mAdapter;
    private User mUser;
    private FragmentThongBao mFragmentThongBao;

    public void init(FragmentThongBao fragmentThongBao){
        mFragmentThongBao = fragmentThongBao;
        mListThongBao = new MutableLiveData<>(new ArrayList<ThongBao>());
        mAdapter = new ThongBaoRecycleViewAdapter(this);
        mUser = new BaseRepository<User>().read(mFragmentThongBao.getContext(), User.mFileName);
    }

    public void loadListThongBao(ArrayList<ThongBao> list){
        mListThongBao.postValue(list);
    }

    public void loadListFromFile(){
        ArrayList<ThongBao> list = new BaseRepository<ArrayList<ThongBao>>().read(mFragmentThongBao.getContext(), ThongBao.mFileName);
        if(list!=null) mListThongBao.setValue(list);
        else refreshListThongBao();
    }


    public FragmentThongBao getFragment(){
        return mFragmentThongBao;
    }

    public void refreshListThongBao(){
        new ListThongBaoDownloader(mFragmentThongBao.getContext()).start();
    }

    public String getMaSinhVien(){
        return mUser.getMaSV().toUpperCase();
    }

    public User getUser(){
        return mUser;
    }

    public ThongBaoRecycleViewAdapter getAdapter(){
        return mAdapter;
    }

    public ArrayList<ThongBao> getListThongBao(){
        return mListThongBao.getValue();
    }

    public String getTitleAt(int position){
        return mListThongBao.getValue().get(position).getTitle();
    }

    public String getTimeAt(int position){
        return mListThongBao.getValue().get(position).getTime();
    }
}
