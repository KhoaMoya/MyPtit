package com.khoa.myptit.thongbao.viewmodel;

/*
 * Created at 9/26/19 3:11 PM by Khoa
 */

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.net.DocumentGetter;
import com.khoa.myptit.login.net.URL;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.thongbao.adapter.ThongBaoRecycleViewAdapter;
import com.khoa.myptit.thongbao.model.ThongBao;
import com.khoa.myptit.thongbao.util.ParseResponse;
import com.khoa.myptit.thongbao.view.DialogChiTietThongBao;

import java.util.ArrayList;

public class ThongBaoViewModel extends ViewModel {

    public MutableLiveData<ArrayList<ThongBao>> mListThongBao;
    public ThongBaoRecycleViewAdapter mAdapter;
    private User mUser;
    private Context mContext;

    public void init(Context context){
        mContext = context;
        mListThongBao = new MutableLiveData<>(new ArrayList<ThongBao>());
        mAdapter = new ThongBaoRecycleViewAdapter(this);
        mUser = new BaseRepository<User>().read(mContext, User.mFileName);
    }

    public void loadListThongBao(DocumentGetter documentGetter){
        ArrayList<ThongBao> thongBaos = ParseResponse.parseDocument(mContext, documentGetter);
        mListThongBao.postValue(thongBaos);
    }

    public void loadListFromFile(){
        ArrayList<ThongBao> list = new BaseRepository<ArrayList<ThongBao>>().read(mContext, ThongBao.mFileName);
        if(list!=null) mListThongBao.setValue(list);
        else refreshListThongBao();
    }

    public void onClickItem(int position){
        new DialogChiTietThongBao(mListThongBao.getValue().get(position), mContext).show();
    }

    public void refreshListThongBao(){
        new DocumentGetter(URL.URL_THONG_BAO, mUser).start();
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
