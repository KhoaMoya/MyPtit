package com.khoa.myptit.thongbao.viewmodel;

/*
 * Created at 9/26/19 3:11 PM by Khoa
 */

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.baseNet.DocumentGetter;
import com.khoa.myptit.thongbao.adapter.ThongBaoRecycleViewAdapter;
import com.khoa.myptit.thongbao.model.ThongBao;
import com.khoa.myptit.thongbao.util.ParseResponse;

import java.util.ArrayList;

public class ThongBaoViewModel extends ViewModel {

    public MutableLiveData<ArrayList<ThongBao>> mListThongBao;
    public ThongBaoRecycleViewAdapter mAdapter;

    public void init(){
        mListThongBao = new MutableLiveData<>(new ArrayList<ThongBao>());
        mAdapter = new ThongBaoRecycleViewAdapter(this);
    }

    public void loadListThongBao(Context context, DocumentGetter documentGetter){
        ArrayList<ThongBao> thongBaos = ParseResponse.parseDocument(context, documentGetter);
        mListThongBao.postValue(thongBaos);
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
