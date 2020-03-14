package com.khoa.myptit.xemhocphi.viewmodel;

/*
 * Created at 10/20/19 7:23 PM by Khoa
 */

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.base.model.User;
import com.khoa.myptit.base.net.Downloader;
import com.khoa.myptit.base.net.LoginPoster;
import com.khoa.myptit.base.net.HtmlGetter;
import com.khoa.myptit.base.repository.BaseRepository;
import com.khoa.myptit.base.util.ParseResponse;
import com.khoa.myptit.xemhocphi.model.HocPhi;
import com.khoa.myptit.xemhocphi.util.ParseHocPhi;

public class HocPhiViewModel extends ViewModel {

    private final String URLHocPhi = "http://qldt.ptit.edu.vn/Default.aspx?page=xemhocphi";
    public final static String GetTag = "get_hocphi";
    public final static String LoginTag = "login_hocphi";

    public MutableLiveData<HocPhi> mHocPhi;
    public Context mContext;
    public MutableLiveData<Boolean> loginError;
    public MutableLiveData<Exception> mException;

    public void init(Context context){
        mHocPhi = new MutableLiveData<>(new HocPhi());
        this.mContext = context;
        loginError = new MutableLiveData<>();
        mException = new MutableLiveData<>();
    }

    public void refreshHocPhi(){
        new HtmlGetter(GetTag, URLHocPhi, new BaseRepository<User>().read(mContext, User.mFileName)).start();
    }

    public void loadHocPhi(){
        HocPhi hocPhi = new BaseRepository<HocPhi>().read(mContext, HocPhi.mFileName);
        if(hocPhi==null){
            refreshHocPhi();
        }else{
            mHocPhi.setValue(hocPhi);
        }
    }

    public void getHocPhi(Downloader downloader){
        if(ParseResponse.checkLogin(mContext, downloader)){
            HocPhi hocPhi = ParseHocPhi.convertToHocPhi(mContext, downloader);
            new BaseRepository<HocPhi>().write(mContext, HocPhi.mFileName, hocPhi);
            mHocPhi.postValue(hocPhi);
        } else {
            new LoginPoster(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public void loginHocPhi(Downloader downloader){
        if(ParseResponse.checkLoginWithPost(mContext, downloader)){
            new HtmlGetter(GetTag, URLHocPhi, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }else{
            loginError.postValue(true);
//            new LoginPoster(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

}
