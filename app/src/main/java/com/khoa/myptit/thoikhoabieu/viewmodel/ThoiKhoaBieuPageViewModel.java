package com.khoa.myptit.thoikhoabieu.viewmodel;

/*
 * Created at 10/13/19 11:03 PM by Khoa
 */

import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.thoikhoabieu.model.Tuan;

public class ThoiKhoaBieuPageViewModel extends ViewModel {

    public ObservableInt showLoading;
    public ObservableInt showTKB;
    public Tuan mTuan;

    public void init(){
        showLoading = new ObservableInt(View.VISIBLE);
        showTKB = new ObservableInt(View.GONE);
    }

}
