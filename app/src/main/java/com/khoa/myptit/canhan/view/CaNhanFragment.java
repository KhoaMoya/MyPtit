package com.khoa.myptit.canhan.view;

/*
 * Created at 10/18/19 8:02 PM by Khoa
 */

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

import com.khoa.myptit.R;
import com.khoa.myptit.canhan.model.ThongTin;
import com.khoa.myptit.canhan.viewmodel.CaNhanViewModel;
import com.khoa.myptit.databinding.FragmentCanhanBinding;
import com.khoa.myptit.login.LoginActivity;
import com.khoa.myptit.login.net.Downloader;
import com.khoa.myptit.xemdiem.view.XemDiemActivity;
import com.khoa.myptit.xemhocphi.view.XemHocPhiActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class CaNhanFragment extends Fragment {

    private FragmentCanhanBinding mBinding;
    private CaNhanViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_canhan, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupBinding(savedInstanceState);

        setupThongTinListener();

        mViewModel.loadThongTin();
    }

    public void setupBinding(Bundle savedInstanceState){

        mViewModel = ViewModelProviders.of(this).get(CaNhanViewModel.class);
        if(savedInstanceState == null) mViewModel.init(getContext());
        mBinding.setViewmodel(mViewModel);

        mBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.deleteData();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        mBinding.xemHocPhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentXemHP = new Intent(getActivity(), XemHocPhiActivity.class);
                getActivity().startActivity(intentXemHP);
            }
        });

        mBinding.xemDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenXemDiem = new Intent(getActivity(), XemDiemActivity.class);
                getActivity().startActivity(intenXemDiem);
            }
        });

    }

    private void setupThongTinListener(){
        mViewModel.mThongTin.observe(this, new Observer<ThongTin>() {
            @Override
            public void onChanged(ThongTin thongTin) {
                mBinding.ten.setText(thongTin.getHoTen());
                mBinding.masv.setText(thongTin.getMaSV());
                mBinding.noisinh.setText(thongTin.getNoiSinh());
                mBinding.ngaysinh.setText(thongTin.getNgaySinh());
                mBinding.lop.setText(thongTin.getLop());
                mBinding.nganh.setText(thongTin.getNganh());
                mBinding.khoa.setText(thongTin.getKhoa());
                mBinding.hedaotao.setText(thongTin.getHeDaoTao());
                mBinding.khoahoc.setText(thongTin.getKhoaHoc());
                mBinding.covanhoctap.setText(thongTin.getCoVanHocTap());
            }
        });
    }

    @Subscribe
    public void onEventDownloadedDocument(Downloader downloader){
        if(downloader.getTag().equals(CaNhanViewModel.GetTag)){
            mViewModel.getCaNhan(downloader);
        } else if(downloader.getTag().equals(CaNhanViewModel.LoginTag)){
            mViewModel.loginCaNhan(downloader);
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
