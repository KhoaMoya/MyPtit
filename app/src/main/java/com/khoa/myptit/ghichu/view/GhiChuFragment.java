package com.khoa.myptit.ghichu.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khoa.myptit.R;

/*
 * Created at 12/8/19 7:13 PM by Khoa
 */

public class GhiChuFragment extends Fragment {


    public GhiChuFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ghi_chu, container, false);
    }

}
