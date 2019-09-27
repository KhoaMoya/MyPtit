package com.khoa.myptit.login.viewmodel;



import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.net.LoginDocumentGetter;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.login.util.ParseRespone;

/*
 * Created at 9/23/19 12:15 PM by Khoa
 */
public class LoginViewModel extends ViewModel {

    public enum LoginStatus {
        LOGINNING, FAIL, SUCCESS
    }

    private Context mContext;
    public MutableLiveData<Boolean> mShowPassword;
    public MutableLiveData<LoginStatus> mLoginStatus;
    private User mUser;

    public void init(Context context) {
        mContext = context;
        mShowPassword = new MutableLiveData<>(true);
        mLoginStatus = new MutableLiveData<>();
        mUser = new BaseRepository<User>().read(mContext, User.mFileName);
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

    public String getMaSV(){
        return mUser.getMaSV()==null ? "" : mUser.getMaSV();
    }

    public void checkLogin(LoginDocumentGetter loginDocumentGetter) {
        if(ParseRespone.checkLogin(mContext, loginDocumentGetter))
            mLoginStatus.postValue(LoginStatus.SUCCESS);
        else mLoginStatus.postValue(LoginStatus.FAIL);
    }

}
