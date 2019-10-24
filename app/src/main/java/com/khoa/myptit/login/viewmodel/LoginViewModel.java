package com.khoa.myptit.login.viewmodel;



import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.net.LoginResponseGetter;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.login.util.ParseResponse;

/*
 * Created at 9/23/19 12:15 PM by Khoa
 */
public class LoginViewModel extends ViewModel {

    public enum LoginStatus {
        LOGINNING, FAIL, SUCCESS
    }
    public static final String TAG = "login";
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
        LoginResponseGetter mLoginResponseGetter = new LoginResponseGetter(TAG, mUser);
        mLoginResponseGetter.start();
    }

    public String getMaSV(){
        if(mUser==null) return "";
        return mUser.getMaSV()==null ? "" : mUser.getMaSV();
    }

    public void checkLogin(LoginResponseGetter loginResponseGetter) {
        if(ParseResponse.checkLogin(mContext, loginResponseGetter))
            mLoginStatus.postValue(LoginStatus.SUCCESS);
        else mLoginStatus.postValue(LoginStatus.FAIL);
    }

}
