package com.khoa.myptit.main;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khoa.myptit.R;
import com.khoa.myptit.databinding.ActivityMainBinding;
import com.khoa.myptit.main.viewmodel.MainViewModel;
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

        setFragment(ThongBaoFragment.getInstance());
    }

    public void setupBindings(Bundle savedInstanceState){
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if(savedInstanceState == null){
            mMainViewModel = new MainViewModel();
        }

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
            case R.id.page_dang_ki:

                break;
            case R.id.page_tin_nhan:

                break;
            case R.id.page_thoi_khoa_bieu:
                mFragment = ThoiKhoaBieuFragment.getInstance();
                mActivityMainBinding.fragTitle.setText("Thời khóa biểu");
                break;
            case R.id.page_ca_nhan:

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
