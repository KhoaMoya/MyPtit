package com.khoa.myptit.thongbao.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.khoa.myptit.R;
import com.khoa.myptit.databinding.FragmentThongbaoBinding;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.main.MainActivity;
import com.khoa.myptit.thongbao.adapter.ItemClickListener;
import com.khoa.myptit.thongbao.model.TatCaThongBao;
import com.khoa.myptit.thongbao.model.ThongBao;
import com.khoa.myptit.thongbao.viewmodel.ThongBaoViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/*
 * Created at 9/26/19 3:11 PM by Khoa
 */


public class ThongBaoFragment extends Fragment implements ItemClickListener {

    private ThongBaoViewModel mThongBaoViewModel;
    private FragmentThongbaoBinding mFragmentThongbaoBinding;

    static String KEY_ITEM = "keyItem";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mFragmentThongbaoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_thongbao, container, false);

        return mFragmentThongbaoBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupBindings(savedInstanceState);

        setupThongBaoChangeListener();

        loadFirst();

    }

    private void loadFirst(){
        mThongBaoViewModel.loadThongBao();
    }

    private void setupThongBaoChangeListener(){
        mThongBaoViewModel.mTatCaThongBao.observe(this, new Observer<TatCaThongBao>() {
            @Override
            public void onChanged(TatCaThongBao tatCaThongBao) {
                mThongBaoViewModel.updateThongBao(tatCaThongBao);
            }
        });
    }

    private void setupBindings(Bundle savedInstanceState){
        mThongBaoViewModel = ViewModelProviders.of(this).get(ThongBaoViewModel.class);
        if(mThongBaoViewModel.mTatCaThongBao == null) mThongBaoViewModel.init(this);
        mFragmentThongbaoBinding.setViewmodel(mThongBaoViewModel);
    }

    @Subscribe
    public void onEventParseListThongBaoDone(TatCaThongBao tatCaThongBao){
        new BaseRepository<TatCaThongBao>().write(getContext(), ThongBao.mFileName, tatCaThongBao);
        mThongBaoViewModel.mTatCaThongBao.postValue(tatCaThongBao);
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
