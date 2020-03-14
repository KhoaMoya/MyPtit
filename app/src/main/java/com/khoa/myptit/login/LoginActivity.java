package com.khoa.myptit.login;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.khoa.myptit.R;
import com.khoa.myptit.base.dialog.MyDialog;
import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.base.net.LoginPoster;
import com.khoa.myptit.base.util.Permission;
import com.khoa.myptit.databinding.ActivityLoginBinding;
import com.khoa.myptit.login.viewmodel.LoginViewModel;
import com.khoa.myptit.main.MainActivity;
import com.khoa.myptit.xemhocphi.view.XemHocPhiActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
/*
 * Created at 9/26/19 3:07 PM by Khoa
 */
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel mLoginViewModel;
    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        if (savedInstanceState == null) {
            mLoginViewModel.init(this);
        }

        mLoginViewModel.mException.observe(this, new Observer<Exception>() {
            @Override
            public void onChanged(Exception exception) {
                new MyDialog(LoginActivity.this).showNotificationDialog("Exception", exception.getCause() + "\n" + exception.getMessage());
            }
        });

        initPermission();

        setupShowPasswordClick();

        setupEditerActionListener();

        setupLogin();
    }

    public void setupShowPasswordClick() {
        mBinding.txtEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLoginViewModel.isShowPassword()){
                    mLoginViewModel.setShowPassword(false);
                    mBinding.txtEye.setText(R.string.fa_eye);
                    mBinding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    mLoginViewModel.setShowPassword(true);
                    mBinding.txtEye.setText(R.string.fa_eye_slash);
                    mBinding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                String password = mBinding.edtPassword.getText().toString();
                mBinding.edtPassword.setText("");
                mBinding.edtPassword.append(password);
            }
        });
    }

    public void setupLogin() {
        mLoginViewModel.mLoginStatus.observe(this, new Observer<LoginStatus>() {
            @Override
            public void onChanged(LoginStatus loginStatus) {
                if (loginStatus == LoginStatus.LOGGING_IN) {
                    onLoggingIn();
                } else if (loginStatus == LoginStatus.SUCCESS) {
                    onLoginSuccess();
                } else {
                    onLoginFail();
                }
            }
        });
        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogin();
            }
        });
    }

    private void onLoggingIn(){
        mBinding.btnLogin.setText("Đang đăng nhập");
        mBinding.progressLogin.setVisibility(View.VISIBLE);
    }

    private void onLoginFail(){
        mBinding.btnLogin.setText("Đăng nhập");
        mBinding.progressLogin.setVisibility(View.GONE);
        new MyDialog(LoginActivity.this).showNotificationDialog("Đăng nhập thất bại", "Tên tài khoản hoặc mặt khẩu không chính xác");

    }

    private void onLoginSuccess(){
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    public void setupEditerActionListener() {
        mBinding.edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_NULL) {
                    // hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    onClickLogin();
                    return true;
                }
                return false;
            }
        });
    }

    public void onClickLogin() {
        // get input
        String maSV = mBinding.edtUsername.getText().toString();
        String passWord = mBinding.edtPassword.getText().toString();

        if (maSV.equals("") || passWord.equals("")) {
            Toast.makeText(this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            mLoginViewModel.downloadDocumentLogin(maSV, passWord);
        }
    }

    @Subscribe
    public void onReceiveEventMessage(EventMessager eventMessager) {
        if (eventMessager.getTag().equals(LoginViewModel.TAG)) {
            if (eventMessager.getEvent() == EventMessager.EVENT.LOGIN_FINNISH) {
                mLoginViewModel.checkLogin((LoginPoster) eventMessager.getData());
            }
        }else if(eventMessager.getEvent() == EventMessager.EVENT.EXCEPTION){
            String tag = eventMessager.getTag();
            Exception exception = (Exception) eventMessager.getData();
            mLoginViewModel.mException.postValue(exception);
        }
    }

    public void initPermission() {
        new Permission(this).initPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == Permission.PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("Loi", "Permission Granted, Now you can use local drive .");
            } else {
                Log.e("Loi", "Permission Denied, You cannot use local drive .");
            }
        }
    }

    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
