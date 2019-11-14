package com.khoa.myptit.main.viewmodel;

/*
 * Created at 9/26/19 3:18 PM by Khoa
 */


import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public ObservableField<String> mCurrentTag;
    public Fragment mCurrentFragment;

    public void init(){
        mCurrentTag = new ObservableField<>("");
        mCurrentFragment = new Fragment();
    }

}
