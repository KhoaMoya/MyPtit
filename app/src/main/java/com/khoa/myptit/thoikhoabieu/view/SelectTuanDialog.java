package com.khoa.myptit.thoikhoabieu.view;

/*
 * Created at 10/4/19 8:46 AM by Khoa
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.khoa.myptit.R;
import com.khoa.myptit.thoikhoabieu.model.Tuan;
import com.khoa.myptit.thoikhoabieu.util.ParseResponse;
import com.khoa.myptit.thoikhoabieu.viewmodel.ThoiKhoaBieuViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class SelectTuanDialog {

    private ArrayList<Tuan> mListTuan;
    private Dialog mDialog;
    private RadioGroup mRadioGroup;
    private TextView txtTuanHienTai;
    private Context mContext;
    private ThoiKhoaBieuViewModel mViewModel;
    private int mCurrentIndex;

    public SelectTuanDialog(Context context, ThoiKhoaBieuViewModel viewModel, int currentIndex) {
        this.mContext = context;
        this.mViewModel = viewModel;
        this.mCurrentIndex = currentIndex;
        this.mListTuan = viewModel.mHocKy.getValue().getListTuan();
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.dialog_select_tuan);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.getWindow().getAttributes().windowAnimations = R.style.MyDialogTuan;
        mRadioGroup = mDialog.findViewById(R.id.group);
        txtTuanHienTai = mDialog.findViewById(R.id.tuan_hientai);
    }


    public void show(){
        int tuanHienTai = ParseResponse.getCurrentTuan(mListTuan);
        txtTuanHienTai.setText("Tuần hiện tại: " + mViewModel.mHocKy.getValue().getListTuan().get(tuanHienTai).getTenTuan());
        for(int i=0; i<mListTuan.size(); i++){
            Tuan tuan = mListTuan.get(i);
            mRadioGroup.addView(createItem(tuan.getTenTuan() + " : " + tuan.getNgayBatDau() + " - " + tuan.getNgayKetThuc()), i);
        }
        RadioButton radioButton = (RadioButton) mRadioGroup.getChildAt(mCurrentIndex);
        radioButton.setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                int position = (id-1) % mListTuan.size();
                mViewModel.mPosition.set(position);
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private RadioButton createItem(String title) {
        RadioButton radioButton = new RadioButton(mContext);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 0, 0);
        radioButton.setLayoutParams(params);
        radioButton.setText(title);
        radioButton.setBackground(mContext.getResources().getDrawable(R.drawable.background_clickable));
        return radioButton;
    }
}
