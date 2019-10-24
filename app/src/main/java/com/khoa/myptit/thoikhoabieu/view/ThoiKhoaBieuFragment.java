package com.khoa.myptit.thoikhoabieu.view;

/*
 * Created at 9/28/19 11:36 PM by Khoa
 */

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.khoa.myptit.R;
import com.khoa.myptit.databinding.FragmentThoikhoabieuBinding;
import com.khoa.myptit.login.net.Downloader;
import com.khoa.myptit.login.viewmodel.LoginViewModel;
import com.khoa.myptit.thoikhoabieu.model.HocKy;
import com.khoa.myptit.thoikhoabieu.model.ThoiKhoaBieu;
import com.khoa.myptit.thoikhoabieu.viewmodel.ThoiKhoaBieuViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/*
 * Created at 10/3/19 9:36 AM by Khoa
 */

public class ThoiKhoaBieuFragment extends Fragment {

    private ThoiKhoaBieuViewModel mViewModel;
    private FragmentThoikhoabieuBinding mBinding;


    public ThoiKhoaBieuFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_thoikhoabieu, container, false);
        setupBindings();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupAdapterChange();

        setupPageChange();

        setupClickTuan();

        mViewModel.loadThoiKhoaBieu();
    }

    private void setupClickTuan() {
        mBinding.selectTuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SelectTuanDialog(getContext(), mViewModel, mBinding.viewpager.getCurrentItem()).show();
            }
        });

        mBinding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.viewpager.arrowScroll(View.FOCUS_RIGHT);
            }
        });
        mBinding.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.viewpager.arrowScroll(View.FOCUS_LEFT);
            }
        });
    }

    private void setupAdapterChange() {
        mViewModel.mHocKy.observe(this, new Observer<HocKy>() {
            @Override
            public void onChanged(HocKy hocKy) {
                mViewModel.onHocKyChanged(hocKy);
            }
        });
    }

    private void setupPageChange() {
        mBinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewModel.onTuanChanged(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupBindings() {
        mViewModel = ViewModelProviders.of(this).get(ThoiKhoaBieuViewModel.class);
        if(mViewModel.mHocKy == null ) mViewModel.init(getContext(), getChildFragmentManager());
        mBinding.setViewmodel(mViewModel);
    }

    @Subscribe
    public void onEventDownloadDone(Downloader downloader) {
        if (downloader.getTag().equals(ThoiKhoaBieuViewModel.TAG_GET)) {
            mViewModel.checkLoginGetTKB(downloader);
        } else if (downloader.getTag().equals(ThoiKhoaBieuViewModel.TAG_POST)) {
            mViewModel.checkLoginPostTKB(downloader);
        } else if (downloader.getTag().equals(ThoiKhoaBieuViewModel.TAG_LOGIN)) {
            mViewModel.checkLogin(downloader);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }


}
