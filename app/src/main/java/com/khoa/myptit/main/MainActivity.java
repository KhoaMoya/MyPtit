package com.khoa.myptit.main;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khoa.myptit.R;
import com.khoa.myptit.base.dialog.MyDialog;
import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.canhan.view.CaNhanFragment;
import com.khoa.myptit.databinding.ActivityMainBinding;
import com.khoa.myptit.ghichu.view.GhiChuFragment;
import com.khoa.myptit.main.viewmodel.MainViewModel;
import com.khoa.myptit.thoikhoabieu.view.ThoiKhoaBieuFragment;
import com.khoa.myptit.thongbao.view.ThongBaoFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/*
 * Created at 9/26/19 3:07 PM by Khoa
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding mBinding;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if (savedInstanceState == null) {
            mMainViewModel.init();
            loadFragment(new ThoiKhoaBieuFragment(), ThoiKhoaBieuFragment.TAG);
        }

        mMainViewModel.mException.observe(this, new Observer<Exception>() {
            @Override
            public void onChanged(Exception exception) {
                new MyDialog(MainActivity.this).showNotificationDialog("Exception", exception.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        swapFragment(menuItem.getItemId());
        return true;
    }

    public void swapFragment(int newItemId) {
        Fragment newFragment = null;
        String newTag = "";
        switch (newItemId) {
            case R.id.page_thongbao:
                newFragment = getSupportFragmentManager().findFragmentByTag(ThongBaoFragment.TAG);
                if (newFragment == null) newFragment = new ThongBaoFragment();
                newTag = ThongBaoFragment.TAG;
                break;
            case R.id.page_ghi_chu:
                newFragment = getSupportFragmentManager().findFragmentByTag(GhiChuFragment.TAG);
                if (newFragment == null) newFragment = new GhiChuFragment();
                newTag = GhiChuFragment.TAG;
                break;
//            case R.id.page_tin_nhan:
//                newTag = TAG.TinNhan;
//                break;
            case R.id.page_thoi_khoa_bieu:
                newFragment = getSupportFragmentManager().findFragmentByTag(ThoiKhoaBieuFragment.TAG);
                if (newFragment == null) newFragment = new ThoiKhoaBieuFragment();
                newTag = ThoiKhoaBieuFragment.TAG;
                break;
            case R.id.page_ca_nhan:
                newFragment = getSupportFragmentManager().findFragmentByTag(CaNhanFragment.TAG);
                if (newFragment == null) newFragment = new CaNhanFragment();
                newTag = CaNhanFragment.TAG;
                break;
        }
        if (newFragment != null) loadFragment(newFragment, newTag);
    }

    public void loadFragment(Fragment newFragment, String tag) {

        if (mMainViewModel.mCurrentTag.equals(tag)) {
            return;
        }

        if (mMainViewModel.mCurrentTag.isEmpty()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_layout, newFragment, tag)
                    .commit();
        } else {
            Fragment prevfragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (prevfragment == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mMainViewModel.mCurrentFragment)
                        .add(R.id.frame_layout, newFragment, tag)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mMainViewModel.mCurrentFragment)
                        .show(newFragment)
                        .commit();
            }
        }

        mMainViewModel.mCurrentFragment = newFragment;
        mMainViewModel.mCurrentTag = tag;
    }

    @Override
    public void onBackPressed() {
        FragmentManager childFragmentManager = mMainViewModel.mCurrentFragment.getChildFragmentManager();
        if (childFragmentManager.getBackStackEntryCount() == 0) {
            if (mMainViewModel.mCurrentTag.equals(ThoiKhoaBieuFragment.TAG)) {
                super.onBackPressed();
            } else {
                mBinding.bottomNavigation.setSelectedItemId(R.id.page_thoi_khoa_bieu);
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(ThoiKhoaBieuFragment.TAG);
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .hide(mMainViewModel.mCurrentFragment)
                            .show(fragment)
                            .commit();
                    mMainViewModel.mCurrentFragment = fragment;
                    mMainViewModel.mCurrentTag = ThoiKhoaBieuFragment.TAG;
                }
            }
        } else {
            childFragmentManager.popBackStackImmediate();
        }
    }

    @Subscribe
    public void onEventMessage(EventMessager eventMessager){
        if(eventMessager.getEvent() == EventMessager.EVENT.EXCEPTION){
            Log.e("Loi", "event exception");
            String tag = eventMessager.getTag();
            Exception exception = (Exception) eventMessager.getData();
            mMainViewModel.mException.postValue(exception);
            }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}