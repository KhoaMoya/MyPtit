package com.khoa.myptit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.khoa.main_activity.MainActivity;
import com.khoa.myptit.baseModel.User;
import com.khoa.myptit.baseRepository.BaseRepository;
import com.khoa.myptit.net.DocumentGetter;
import com.khoa.myptit.net.url.URL;
import com.khoa.myptit.util.ParseRespone;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/*
 * Created at 9/24/19 8:35 PM by Khoa
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
            //new DocumentGetter(URL.URL_THONG_BAO, user).start();
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

    @Subscribe
    public void onGetDocument(DocumentGetter documentGetter){
        if(ParseRespone.checkLogin(this, documentGetter)){
            Log.e("Loi", "Login thành công");
        } else {
            Log.e("Loi", "Login thất bại");
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
