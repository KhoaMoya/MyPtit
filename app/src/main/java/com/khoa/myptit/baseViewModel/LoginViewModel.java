package com.khoa.myptit.baseViewModel;

/*
 * Created at 9/23/19 12:15 PM by Khoa
 */

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.baseModel.User;
import com.khoa.myptit.baseNet.LoginDocumentGetter;
import com.khoa.myptit.util.ParseRespone;

public class LoginViewModel extends ViewModel {

    public enum LoginStatus {
        LOGINNING, FAIL, SUCCESS
    }

    public Context mContext;
    public MutableLiveData<Boolean> mShowPassword;
    public MutableLiveData<LoginStatus> mLoginStatus;
    public User mUser;

    public void init(Context context) {
        mContext = context;
        mShowPassword = new MutableLiveData<>(true);
        mLoginStatus = new MutableLiveData<>();
    }

    public void onClickShowPassword() {
        mShowPassword.setValue(!mShowPassword.getValue());
    }

    public void downloadDocumentLogin(String maSV, String password) {

        mUser = new User();
        mUser.setMaSV(maSV);
        mUser.setMaKhau(password);

        mLoginStatus.setValue(LoginStatus.LOGINNING);
        LoginDocumentGetter mLoginDocumentGetter = new LoginDocumentGetter(mUser);
        mLoginDocumentGetter.start();
    }

    public void checkLogin(LoginDocumentGetter loginDocumentGetter) {
        if(ParseRespone.checkLogin(mContext, loginDocumentGetter))
            mLoginStatus.postValue(LoginStatus.SUCCESS);
        else mLoginStatus.postValue(LoginStatus.FAIL);
    }

}
