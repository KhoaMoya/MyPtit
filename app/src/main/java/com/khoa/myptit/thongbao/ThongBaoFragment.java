package com.khoa.myptit.thongbao;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.khoa.myptit.R;
import com.khoa.myptit.baseModel.User;
import com.khoa.myptit.baseNet.DocumentGetter;
import com.khoa.myptit.baseNet.url.URL;
import com.khoa.myptit.baseRepository.BaseRepository;
import com.khoa.myptit.databinding.FragmentThongbaoBinding;
import com.khoa.myptit.thongbao.model.ThongBao;
import com.khoa.myptit.thongbao.util.ParseResponse;
import com.khoa.myptit.thongbao.viewmodel.ThongBaoViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/*
 * Created at 9/26/19 3:11 PM by Khoa
 */

public class ThongBaoFragment extends Fragment {

    private ThongBaoViewModel mThongBaoViewModel;
    private static ThongBaoFragment mThongBaoFragment;
    private FragmentThongbaoBinding mFragmentThongbaoBinding;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentThongbaoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_thongbao, container, false);
        mThongBaoViewModel = ViewModelProviders.of(this).get(ThongBaoViewModel.class);
        mThongBaoViewModel.init();
        mFragmentThongbaoBinding.setViewmodel(mThongBaoViewModel);

        mFragmentThongbaoBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                User user = new BaseRepository<User>().read(getContext(), User.mFileName);
                DocumentGetter documentGetter = new DocumentGetter(URL.URL_THONG_BAO, user);
                documentGetter.start();
            }
        });

        mThongBaoViewModel.mListThongBao.observe(this, new Observer<ArrayList<ThongBao>>() {
            @Override
            public void onChanged(ArrayList<ThongBao> thongBaos) {
                mThongBaoViewModel.mAdapter.notifyDataSetChanged();
                mFragmentThongbaoBinding.swipeRefresh.setRefreshing(false);
            }
        });

        return mFragmentThongbaoBinding.getRoot();
    }

    public static ThongBaoFragment getInstance(){
        if(mThongBaoFragment ==null){
            mThongBaoFragment = new ThongBaoFragment();
        }
        return mThongBaoFragment;
    }

    @Subscribe
    public void onEventDownloadDocumentDone(DocumentGetter documentGetter){
        mThongBaoViewModel.loadListThongBao(getContext(), documentGetter);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}
