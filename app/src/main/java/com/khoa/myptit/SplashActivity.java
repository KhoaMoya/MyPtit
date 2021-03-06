package com.khoa.myptit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.khoa.myptit.login.LoginActivity;
import com.khoa.myptit.base.model.User;
import com.khoa.myptit.base.net.HtmlGetter;
import com.khoa.myptit.base.repository.BaseRepository;
import com.khoa.myptit.main.MainActivity;
import com.khoa.myptit.base.util.ParseResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/*
 * Created at 9/26/19 3:07 PM by Khoa
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imgLogo = findViewById(R.id.img_logo);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash);
        imgLogo.startAnimation(animation);

        User user = new BaseRepository<User>().read(this, User.mFileName);
        if(user == null) {
            user = new User();
            new BaseRepository<User>().write(this, User.mFileName, user);
        }
        if (user.getCookie() == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intentLogin = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                    finish();
                }
            }, 1000);
        } else {
            //new HtmlGetter(URL.URL_THONG_BAO, user).start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 1000);
        }

    }
}
