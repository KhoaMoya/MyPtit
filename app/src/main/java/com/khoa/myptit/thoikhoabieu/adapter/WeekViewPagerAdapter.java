package com.khoa.myptit.thoikhoabieu.adapter;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.khoa.myptit.thoikhoabieu.model.Subject;
import com.khoa.myptit.thoikhoabieu.model.Week;
import com.khoa.myptit.thoikhoabieu.view.ThoiKhoaBieuPageFragment;

import java.util.ArrayList;

/*
 * Created at 10/3/19 9:34 AM by Khoa
 */

public class WeekViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Week> listWeek;

    public WeekViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void setWeeks(ArrayList<Week> listWeek) {
        this.listWeek = listWeek;
        this.notifyDataSetChanged();
    }

    public void updateWeek(int position, Week week){
        listWeek.set(position, week);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ThoiKhoaBieuPageFragment.newIntance(listWeek.get(position));
    }

    @Override
    public int getCount() {
        return listWeek == null ? 0 : listWeek.size();
    }
}
