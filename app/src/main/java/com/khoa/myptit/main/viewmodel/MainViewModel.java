package com.khoa.myptit.main.viewmodel;

/*
 * Created at 9/26/19 3:18 PM by Khoa
 */


import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public String mCurrentTag;
    public Fragment mCurrentFragment;
    public MutableLiveData<Exception> mException;

    public void init(){
        mCurrentTag = "";
        mCurrentFragment = new Fragment();
        mException = new MutableLiveData<>();
    }

}
