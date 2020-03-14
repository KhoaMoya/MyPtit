package com.khoa.myptit.canhan.view;

/*
 * Created at 10/18/19 8:02 PM by Khoa
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.khoa.myptit.R;
import com.khoa.myptit.base.dialog.MyDialog;
import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.canhan.model.ThongTin;
import com.khoa.myptit.canhan.viewmodel.CaNhanViewModel;
import com.khoa.myptit.databinding.FragmentCanhanBinding;
import com.khoa.myptit.base.net.Downloader;
import com.khoa.myptit.xemdiem.view.XemDiemActivity;
import com.khoa.myptit.xemhocphi.view.XemHocPhiActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class CaNhanFragment extends Fragment {

    public static final String TAG = "canhan_fragment";
    private FragmentCanhanBinding mBinding;
    private CaNhanViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCanhanBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupBinding(savedInstanceState);

        setupThongTinListener();

        loadInfo();
    }

    private void loadInfo(){
        mBinding.progressLoading.setVisibility(View.VISIBLE);
        mViewModel.loadThongTin();
    }

    private void setupBinding(Bundle savedInstanceState){

        mViewModel = new ViewModelProvider(this).get(CaNhanViewModel.class);
        if(savedInstanceState == null) mViewModel.init(getContext());

        mBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LogoutDialog(getActivity(), mViewModel).show();
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

        mViewModel.loginError.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginError) {
                if(loginError){
                    mBinding.progressLoading.setVisibility(View.GONE);
                    new MyDialog(getContext()).showNotificationDialog("Đăng nhập thất bại", "Không thể đăng nhập vào QLDT. Vui lòng đăng nhập nhập lại");
                }
            }
        });

    }

    private void setupThongTinListener(){
        mViewModel.mThongTin.observe(getViewLifecycleOwner(), new Observer<ThongTin>() {
            @Override
            public void onChanged(ThongTin thongTin) {
                mBinding.progressLoading.setVisibility(View.GONE);

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
    public void onEventMessage(EventMessager eventMessager){
        if(eventMessager.getEvent() == EventMessager.EVENT.DOWNLOAD_FINNISH_PROFILE){
            if(eventMessager.getTag().equals(CaNhanViewModel.GetTag)){
                mViewModel.getCaNhan((Downloader) eventMessager.getData());
            }
        } else if(eventMessager.getEvent() == EventMessager.EVENT.LOGIN_FINNISH){
            if(eventMessager.getTag().equals(CaNhanViewModel.LoginTag)){
                mViewModel.loginCaNhan((Downloader) eventMessager.getData());
            }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
