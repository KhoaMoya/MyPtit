package com.khoa.myptit.thoikhoabieu.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.khoa.myptit.thoikhoabieu.model.HocKy;
import com.khoa.myptit.thoikhoabieu.view.ThoiKhoaBieuPageFragment;

/*
 * Created at 10/3/19 9:34 AM by Khoa
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private HocKy mHocKy;

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setHocKy(HocKy hocKy){
        this.mHocKy = hocKy;
    }

    @Override
    public Fragment getItem(int position) {
        return new ThoiKhoaBieuPageFragment(mHocKy.getListTuan().get(position));
    }

    @Override
    public int getCount() {
        return mHocKy.getListTuan().size();
    }
}
