package com.khoa.myptit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;


import com.khoa.myptit.baseViewModel.LoginViewModel;
import com.khoa.myptit.databinding.ActivityLoginBinding;
import com.khoa.myptit.baseNet.LoginDocumentGetter;
import com.khoa.myptit.main.MainActivity;
import com.khoa.myptit.util.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;



/*
 * Created at 9/26/19 3:07 PM by Khoa
 */

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel mLoginViewModel;
    private ActivityLoginBinding mActivityLoginBinding;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        initPermission();

        setupBindings(savedInstanceState);

        setupShowPasswordClick();

        setupEditerActionListener();

        setupLoginClick();

    }

    private void setupBindings(Bundle savedInstanceState) {
         mActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        if(savedInstanceState == null){
            mLoginViewModel.init(this);
        }
        mActivityLoginBinding.setLoginViewModel(mLoginViewModel);
        mActivityLoginBinding.edtUsername.requestFocus();
    }

    public void setupShowPasswordClick(){
        mLoginViewModel.mShowPassword.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean showPassword) {
                if(showPassword){
                    mActivityLoginBinding.txtEye.setText(R.string.fa_eye);
                    mActivityLoginBinding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    mActivityLoginBinding.txtEye.setText(R.string.fa_eye_slash);
                    mActivityLoginBinding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    public void setupLoginClick(){
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Đang đăng nhập");
        mLoginViewModel.mLoginStatus.observe(this, new Observer<LoginViewModel.LoginStatus>() {
            @Override
            public void onChanged(LoginViewModel.LoginStatus loginStatus) {
                if(loginStatus == LoginViewModel.LoginStatus.LOGINNING){
                    mProgressDialog.show();
                } else if(loginStatus == LoginViewModel.LoginStatus.SUCCESS) {
                    mProgressDialog.dismiss();
                    Log.e("Loi", "Login success");
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    mProgressDialog.dismiss();
                    Log.e("Loi", "Login fail");
                    Toast.makeText(getBaseContext(), "Login fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mActivityLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogin();
            }
        });
    }

    public void setupEditerActionListener(){
        mActivityLoginBinding.edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_NULL) {
                    onClickLogin();
                    return true;
                }
                return false;
            }
        });
    }

    public void onClickLogin(){
        String maSV = mActivityLoginBinding.edtUsername.getText().toString();
        String passWord = mActivityLoginBinding.edtPassword.getText().toString();
        if(maSV.equals("") || passWord.equals("")){
            Toast.makeText(this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else mLoginViewModel.downloadDocumentLogin(maSV, passWord);
    }

    @Subscribe
    public void onEventDownloadDocumentDone(LoginDocumentGetter loginDocumentGetter){
        mLoginViewModel.checkLogin(loginDocumentGetter);
    }

    public void initPermission(){
        new Permission(this).initPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Permission.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Loi", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("Loi", "Permission Denied, You cannot use local drive .");
                }
                break;
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
