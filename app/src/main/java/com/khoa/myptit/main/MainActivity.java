package com.khoa.myptit.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.khoa.myptit.R;
import com.khoa.myptit.databinding.ActivityMainBinding;
import com.khoa.myptit.main.viewmodel.MainViewModel;
import com.khoa.myptit.thongbao.view.FragmentThongBao;

/*
 * Created at 9/26/19 3:07 PM by Khoa
 */

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mActivityMainBinding;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBindings(savedInstanceState);

        FragmentThongBao fragmentThongBao = FragmentThongBao.getInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragmentThongBao)
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
