package com.khoa.myptit.login.viewmodel;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.base.model.User;
import com.khoa.myptit.base.net.LoginPoster;
import com.khoa.myptit.base.repository.BaseRepository;
import com.khoa.myptit.base.util.ParseResponse;
import com.khoa.myptit.login.LoginStatus;

/*
 * Created at 9/23/19 12:15 PM by Khoa
 */
public class LoginViewModel extends ViewModel {

    public static final String TAG = LoginViewModel.class.getSimpleName();
    private Context mContext;
    public MutableLiveData<LoginStatus> mLoginStatus;
    private User mUser;
    private boolean showPassword;
    public MutableLiveData<Exception> mException;

    public void init(Context context) {
        mContext = context;
        mLoginStatus = new MutableLiveData<>();
        mUser = new BaseRepository<User>().read(mContext, User.mFileName);
        showPassword = false;
        mException = new MutableLiveData<>();
    }

    public void downloadDocumentLogin(String maSV, String password) {
        mUser = new User();
        mUser.setMaSV(maSV);
        mUser.setMaKhau(password);

        mLoginStatus.setValue(LoginStatus.LOGGING_IN);
        LoginPoster mLoginPoster = new LoginPoster(TAG, mUser);
        mLoginPoster.start();
    }

    public String getMaSV() {
        if (mUser == null) return "";
        return mUser.getMaSV() == null ? "" : mUser.getMaSV();
    }

    public void checkLogin(LoginPoster loginPoster) {
        if (ParseResponse.checkLoginWithPost(mContext, loginPoster)) {
            mLoginStatus.postValue(LoginStatus.SUCCESS);
        }
        else {
            mLoginStatus.postValue(LoginStatus.FAIL);
        }
    }

    public boolean isShowPassword() {
        return showPassword;
    }

    public void setShowPassword(boolean showPassword) {
        this.showPassword = showPassword;
    }
}
