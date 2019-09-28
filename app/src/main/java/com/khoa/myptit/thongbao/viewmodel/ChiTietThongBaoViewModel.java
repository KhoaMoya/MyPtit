package com.khoa.myptit.thongbao.viewmodel;

/*
 * Created at 9/28/19 5:16 PM by Khoa
 */

import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.thongbao.model.ThongBao;

public class ChiTietThongBaoViewModel extends ViewModel {

    public ThongBao mThongBao;
    public ObservableInt showLoading;
    public ObservableInt showContent;
    public MutableLiveData<String> mContent;

    public void init(ThongBao thongBao){
        mThongBao = thongBao;
        showLoading = new ObservableInt(View.VISIBLE);
        showContent = new ObservableInt(View.GONE);
        mContent = new MutableLiveData<>();
    }

    public String getTime(){
        return mThongBao.getTime();
    }

    public String getTitle(){
        return mThongBao.getTitle();
    }

    public String getLink(){
        return mThongBao.getLink();
    }

    public void updateContent( String content){
        mContent.postValue(content);
    }

}
