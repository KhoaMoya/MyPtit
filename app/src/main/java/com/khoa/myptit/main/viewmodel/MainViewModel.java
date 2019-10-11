package com.khoa.myptit.main.viewmodel;

/*
 * Created at 9/26/19 3:18 PM by Khoa
 */

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private int number;

    public MainViewModel(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
