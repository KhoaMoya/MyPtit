package com.khoa.myptit.xemdiem.viewmodel;

/*
 * Created at 10/21/19 9:12 PM by Khoa
 */

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.net.Downloader;
import com.khoa.myptit.login.net.LoginResponseGetter;
import com.khoa.myptit.login.net.ResponseGetter;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.login.util.ParseResponse;
import com.khoa.myptit.xemdiem.model.DiemHocKy;
import com.khoa.myptit.xemdiem.model.DiemMonHoc;
import com.khoa.myptit.xemdiem.net.DownloadDocumentAllDiem;
import com.khoa.myptit.xemdiem.util.ParseDiem;

import java.util.ArrayList;

public class XemDiemViewModel extends ViewModel {

    public final static String GetTag = "get_xemdiem";
    public final static String LoginTag = "login_xemdiem";
    public final static String PostTag = "post_xemdiem";
    public final String URLXemDiem = "http://qldt.ptit.edu.vn/Default.aspx?page=xemdiemthi";

    private Context mContext;
    public MutableLiveData<ArrayList<DiemHocKy>> mLisDiemHocKy;
    public ObservableInt showLoading;
    public ObservableInt showDiem;


    public void init(Context context){
        mContext = context;
        mLisDiemHocKy = new MutableLiveData<>(new ArrayList<DiemHocKy>());
        showLoading = new ObservableInt(View.VISIBLE);
        showDiem = new ObservableInt(View.GONE);
    }

    public void refreshDiem(){
        showLoading.set(View.VISIBLE);
        showDiem.set(View.GONE);
        new ResponseGetter(GetTag, URLXemDiem, new BaseRepository<User>().read(mContext, User.mFileName)).start();
    }

    public void loadAllDiem(){
        ArrayList<DiemHocKy> list = new BaseRepository<ArrayList<DiemHocKy>>().read(mContext, DiemMonHoc.mFileName);
        if(list==null) {
            refreshDiem();
        }else{
            showLoading.set(View.GONE);
            showDiem.set(View.VISIBLE);
            mLisDiemHocKy.postValue(list);
        }
    }

    public void getXemDiem(Downloader downloader){
        if(ParseResponse.checkLogin(mContext, downloader)){
            new DownloadDocumentAllDiem(PostTag, URLXemDiem, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }else{
            new LoginResponseGetter(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public void postXemDiem(Downloader downloader){
        if(ParseResponse.checkLogin(mContext, downloader)){
            ArrayList<DiemHocKy> list = ParseDiem.convertToListHocKy(mContext, downloader);
            mLisDiemHocKy.postValue(list);
            showLoading.set(View.GONE);
            showDiem.set(View.VISIBLE);
        } else {
            new LoginResponseGetter(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public void loginXemDiem(Downloader downloader){
        if(ParseResponse.checkLogin(mContext, downloader)){
            new ResponseGetter(GetTag, URLXemDiem, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        } else {
            new LoginResponseGetter(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

}
