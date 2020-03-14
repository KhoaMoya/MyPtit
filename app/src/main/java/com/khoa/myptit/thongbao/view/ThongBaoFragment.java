package com.khoa.myptit.thongbao.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.databinding.FragmentThongbaoBinding;
import com.khoa.myptit.base.repository.BaseRepository;
import com.khoa.myptit.main.MainActivity;
import com.khoa.myptit.thongbao.adapter.ItemClickListener;
import com.khoa.myptit.thongbao.model.TatCaThongBao;
import com.khoa.myptit.thongbao.model.ThongBao;
import com.khoa.myptit.thongbao.viewmodel.ThongBaoViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/*
 * Created at 9/26/19 3:11 PM by Khoa
 */


public class ThongBaoFragment extends Fragment implements ItemClickListener {

    public static final String TAG = "thongbao_fragment";
    private ThongBaoViewModel mThongBaoViewModel;
    private FragmentThongbaoBinding mBinding;

    static String KEY_ITEM = "keyItem";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding =  FragmentThongbaoBinding.inflate(inflater, container, false);

        mThongBaoViewModel = new ViewModelProvider(this).get(ThongBaoViewModel.class);
        if(mThongBaoViewModel.mTatCaThongBao == null) mThongBaoViewModel.init(getContext());

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupBindings();

        setupThongBaoChangeListener();

        loadFirst();
    }

    private void loadFirst(){
        mBinding.progressLoading.setVisibility(View.VISIBLE);
        mBinding.recyclerView.setVisibility(View.INVISIBLE);
        mThongBaoViewModel.loadThongBao();
    }

    private void setupThongBaoChangeListener(){
        mThongBaoViewModel.mTatCaThongBao.observe(getViewLifecycleOwner(), new Observer<TatCaThongBao>() {
            @Override
            public void onChanged(TatCaThongBao tatCaThongBao) {
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mBinding.progressLoading.setVisibility(View.GONE);
                mBinding.txtLastUpdate.setText("Cập nhật lần cuối: " + tatCaThongBao.getLastUpdate());
                mThongBaoViewModel.updateThongBao(tatCaThongBao);
            }
        });
    }

    private void setupBindings(){
        mBinding.btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
        mThongBaoViewModel.mAdapter.setItemClickListener(this);
        mBinding.recyclerView.setAdapter(mThongBaoViewModel.mAdapter);
    }

    private void refresh(){
        mBinding.progressLoading.setVisibility(View.VISIBLE);
        mBinding.recyclerView.setVisibility(View.INVISIBLE);
        mThongBaoViewModel.refreshThongBao();
    }

    @Subscribe
    public void onEventMessage(EventMessager eventMessager){
        if(eventMessager.getEvent() == EventMessager.EVENT.DOWNLOAD_FINNISH_NOTIFICATION) {
            TatCaThongBao tatCaThongBao = (TatCaThongBao) eventMessager.getData();
            new BaseRepository<TatCaThongBao>().write(getContext(), ThongBao.mFileName, tatCaThongBao);
            mThongBaoViewModel.mTatCaThongBao.postValue(tatCaThongBao);
        }
    }

    @Override
    public void onClickItem(int position) {
        Intent intent = new Intent(getActivity(), ChiTietThongBaoActivity.class);
        intent.putExtra(KEY_ITEM, mThongBaoViewModel.mTatCaThongBao.getValue().getListThongBao().get(position));
        startActivity(intent);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
