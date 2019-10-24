package com.khoa.myptit.xemhocphi.viewmodel;

/*
 * Created at 10/20/19 7:23 PM by Khoa
 */

import android.content.Context;
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
import com.khoa.myptit.xemhocphi.model.HocPhi;
import com.khoa.myptit.xemhocphi.util.ParseHocPhi;

public class HocPhiViewModel extends ViewModel {

    private final String URLHocPhi = "http://qldt.ptit.edu.vn/Default.aspx?page=xemhocphi";
    public final static String GetTag = "get_hocphi";
    public final static String LoginTag = "login_hocphi";

    public MutableLiveData<HocPhi> mHocPhi;
    public ObservableInt showLoading;
    public ObservableInt showHocPhi;
    public Context mContext;

    public void init(Context context){
        mHocPhi = new MutableLiveData<>(new HocPhi());
        showHocPhi = new ObservableInt(View.GONE);
        showLoading = new ObservableInt(View.VISIBLE);
        this.mContext = context;
    }

    public void refreshHocPhi(){
        showLoading.set(View.VISIBLE);
        showHocPhi.set(View.GONE);
        new ResponseGetter(GetTag, URLHocPhi, new BaseRepository<User>().read(mContext, User.mFileName)).start();
    }

    public void loadHocPhi(){
        HocPhi hocPhi = new BaseRepository<HocPhi>().read(mContext, HocPhi.mFileName);
        if(hocPhi==null){
            refreshHocPhi();
        }else{
            showHocPhi.set(View.VISIBLE);
            showLoading.set(View.GONE);
            mHocPhi.setValue(hocPhi);
        }
    }

    public void getHocPhi(Downloader downloader){
        if(ParseResponse.checkLogin(mContext, downloader)){
            HocPhi hocPhi = ParseHocPhi.convertToHocPhi(mContext, downloader);
            mHocPhi.postValue(hocPhi);
            showHocPhi.set(View.VISIBLE);
            showLoading.set(View.GONE);
        } else {
            new LoginResponseGetter(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public void loginHocPhi(Downloader downloader){
        if(ParseResponse.checkLogin(mContext, downloader)){
            new ResponseGetter(GetTag, URLHocPhi, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        } else {
            new LoginResponseGetter(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

}
