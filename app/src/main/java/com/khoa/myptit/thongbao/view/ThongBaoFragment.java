package com.khoa.myptit.thongbao.view;

import android.content.Intent;
import android.os.Bundle;
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
import com.khoa.myptit.databinding.FragmentThongbaoBinding;
import com.khoa.myptit.thongbao.adapter.ItemClickListener;
import com.khoa.myptit.thongbao.model.ThongBao;
import com.khoa.myptit.thongbao.viewmodel.ThongBaoViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/*
 * Created at 9/26/19 3:11 PM by Khoa
 */


public class ThongBaoFragment extends Fragment implements ItemClickListener {

    private static ThongBaoViewModel mThongBaoViewModel;
    private static ThongBaoFragment mThongBaoFragment;
    private FragmentThongbaoBinding mFragmentThongbaoBinding;

    static String KEY_ITEM = "keyItem";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mFragmentThongbaoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_thongbao, container, false);

        setupBindings();

        setupRefreshListener();

        setupListThongBaoChangeListener();

        loadFirst();

        return mFragmentThongbaoBinding.getRoot();
    }

    public static ThongBaoFragment getInstance(){
        if(mThongBaoFragment ==null){
            mThongBaoFragment = new ThongBaoFragment();
        }
        return mThongBaoFragment;
    }

    private void loadFirst(){
        mFragmentThongbaoBinding.swipeRefresh.setRefreshing(true);
        mThongBaoViewModel.loadListFromFile();
    }

    private void setupListThongBaoChangeListener(){
        mThongBaoViewModel.mListThongBao.observe(this, new Observer<ArrayList<ThongBao>>() {
            @Override
            public void onChanged(ArrayList<ThongBao> thongBaos) {
                mThongBaoViewModel.mAdapter.notifyDataSetChanged();
                mFragmentThongbaoBinding.swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void setupRefreshListener(){
        mFragmentThongbaoBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               mThongBaoViewModel.refreshListThongBao();
            }
        });
    }

    private void setupBindings(){
        mThongBaoViewModel = ViewModelProviders.of(this).get(ThongBaoViewModel.class);
        mThongBaoViewModel.init(this);
        mFragmentThongbaoBinding.setViewmodel(mThongBaoViewModel);
    }

    @Subscribe
    public void onEventParseListThongBaoDone(ArrayList<ThongBao> list){
        mThongBaoViewModel.loadListThongBao(list);
    }

    @Override
    public void onClickItem(int position) {
        Intent intent = new Intent(getActivity(), ChiTietThongBaoActivity.class);
        intent.putExtra(KEY_ITEM, mThongBaoViewModel.getListThongBao().get(position));
        startActivity(intent);
        onStop();
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
