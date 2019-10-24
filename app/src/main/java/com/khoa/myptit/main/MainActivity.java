package com.khoa.myptit.main;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khoa.myptit.R;
import com.khoa.myptit.canhan.view.CaNhanFragment;
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
    }

    public void setupBindings(Bundle saveInstanceState) {
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mActivityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if (saveInstanceState == null) {
            mMainViewModel.init();
            updateFragment(new ThoiKhoaBieuFragment(), TAG.ThoiKhoaBieu);
        }
//        else updateFragment(getSupportFragmentManager().findFragmentByTag(mMainViewModel.mCurrentTag.get()), mMainViewModel.mCurrentTag.get());

        mActivityMainBinding.setViewmodel(mMainViewModel);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        swapFragment(menuItem.getItemId());
        return true;
    }

    public void swapFragment(int itemId) {
        Fragment newFragment = null;
        String newTag = "";
        switch (itemId) {
            case R.id.page_thongbao:
                newFragment = getSupportFragmentManager().findFragmentByTag(TAG.ThongBao);
                if (newFragment == null) newFragment = new ThongBaoFragment();
                newTag = TAG.ThongBao;
                break;
            case R.id.page_ghi_chu:
                newTag = TAG.GhiChu;
                break;
            case R.id.page_tin_nhan:
                newTag = TAG.TinNhan;
                break;
            case R.id.page_thoi_khoa_bieu:
                newFragment = getSupportFragmentManager().findFragmentByTag(TAG.ThoiKhoaBieu);
                if (newFragment == null) newFragment = new ThoiKhoaBieuFragment();
                newTag = TAG.ThoiKhoaBieu;
                break;
            case R.id.page_ca_nhan:
                newFragment = getSupportFragmentManager().findFragmentByTag(TAG.CaNhan);
                if(newFragment == null) newFragment = new CaNhanFragment();
                newTag = TAG.CaNhan;
                break;
        }
        if (newFragment != null) updateFragment(newFragment, newTag);
    }

    public void updateFragment(Fragment newFragment, String newTag) {

        if(mMainViewModel.mCurrentTag.get().equals(newTag)) {
            return;
        }

        if (mMainViewModel.mCurrentTag.get().isEmpty()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_layout, newFragment, newTag)
                    .commit();
//            Log.e("Loi", "1");
        } else {
            Fragment prevfragment = getSupportFragmentManager().findFragmentByTag(newTag);
            if (prevfragment == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mMainViewModel.mCurrentFragment)
                        .add(R.id.frame_layout, newFragment, newTag)
                        .commit();
//                Log.e("Loi", "2");
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mMainViewModel.mCurrentFragment)
                        .show(newFragment)
                        .commit();
//                Log.e("Loi", "3");
            }
        }

        mMainViewModel.mCurrentFragment = newFragment;
        mMainViewModel.mCurrentTag.set(newTag);
    }

    @Override
    public void onBackPressed() {
        FragmentManager childFragmentManager = mMainViewModel.mCurrentFragment.getChildFragmentManager();
        if (childFragmentManager.getBackStackEntryCount() == 0) {
            if (mMainViewModel.mCurrentTag.get().equals(TAG.ThoiKhoaBieu)) {
                super.onBackPressed();
            } else {
                mActivityMainBinding.bottomNavigation.setSelectedItemId(R.id.page_thoi_khoa_bieu);
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG.ThoiKhoaBieu);
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .hide(mMainViewModel.mCurrentFragment)
                            .show(fragment)
                            .commit();
                    mMainViewModel.mCurrentFragment = fragment;
                    mMainViewModel.mCurrentTag.set(TAG.ThoiKhoaBieu);
                }
            }
        } else {
            childFragmentManager.popBackStackImmediate();
        }
    }
}