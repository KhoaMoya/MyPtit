package com.khoa.myptit.thoikhoabieu.view;

/*
 * Created at 10/3/19 9:49 PM by Khoa
 */


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.khoa.myptit.R;
import com.khoa.myptit.databinding.DialogMonhocBinding;
import com.khoa.myptit.thoikhoabieu.model.MonHoc;
import com.khoa.myptit.thoikhoabieu.viewmodel.ChiTietMonHocViewModel;

public class ChiTietMonHocDialogFragment extends DialogFragment {

    private MonHoc mMonHoc;
    private Context mContext;
    private ChiTietMonHocViewModel mViewModel;
    private DialogMonhocBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_monhoc, null, false);

        setupBinding();

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customWindow();
    }

    public ChiTietMonHocDialogFragment(Context mContext, MonHoc mMonHoc) {
        this.mMonHoc = mMonHoc;
        this.mContext = mContext;
    }

    private void setupBinding(){
        mViewModel = ViewModelProviders.of(this).get(ChiTietMonHocViewModel.class);
        mViewModel.init(mMonHoc);
        mBinding.setViewModel(mViewModel);
    }

    private void customWindow(){
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().getAttributes().windowAnimations = R.style.MyDialogMonHoc;
    }
}
