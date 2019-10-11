package com.khoa.myptit.main;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khoa.myptit.R;
import com.khoa.myptit.databinding.ActivityMainBinding;
import com.khoa.myptit.main.viewmodel.MainViewModel;
import com.khoa.myptit.thoikhoabieu.model.ThoiKhoaBieu;
import com.khoa.myptit.thoikhoabieu.view.ThoiKhoaBieuFragment;
import com.khoa.myptit.thongbao.view.ThongBaoFragment;

/*
 * Created at 9/26/19 3:07 PM by Khoa
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding mActivityMainBinding;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBindings(savedInstanceState);

        setFragment(new ThoiKhoaBieuFragment());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public void setupBindings(Bundle savedInstanceState){
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

//        if(savedInstanceState == null){
//            Log.e("Loi", "savedInstance = null");
//            mMainViewModel = new MainViewModel(10);
//        }

        mActivityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment mFragment = null;
        switch (menuItem.getItemId()){
            case R.id.page_home:
                mFragment = ThongBaoFragment.getInstance();
                mActivityMainBinding.fragTitle.setText("Thông báo");
                break;
            case R.id.page_ghi_chu:
                mActivityMainBinding.fragTitle.setText("Ghi chú");
                break;
            case R.id.page_tin_nhan:
                mActivityMainBinding.fragTitle.setText("Tin nhắn");
                break;
            case R.id.page_thoi_khoa_bieu:
                mFragment = new ThoiKhoaBieuFragment();
                mActivityMainBinding.fragTitle.setText("Thời khóa biểu");
                break;
            case R.id.page_ca_nhan:
                mActivityMainBinding.fragTitle.setText("Cá nhân");
                break;
        }

        if(mFragment!=null) {
            setFragment(mFragment);
            return true;
        }
        return false;
    }

    public void setFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}
