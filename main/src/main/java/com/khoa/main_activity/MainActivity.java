package com.khoa.main_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.khoa.main_activity.databinding.ActivityMainBinding;
import com.khoa.main_activity.viewmodel.MainViewModel;
import com.khoa.thongbao.view.ThongBaoFragment;
/*
 * Created at 9/25/19 12:04 PM by Khoa
 */

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mActivityMainBinding;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBindings(savedInstanceState);

        ThongBaoFragment thongBaoFragment = new ThongBaoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, thongBaoFragment)
                .commit();
    }

    public void setupBindings(Bundle savedInstanceState){
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if(savedInstanceState == null){
            mMainViewModel = new MainViewModel();
        }

    }
}
