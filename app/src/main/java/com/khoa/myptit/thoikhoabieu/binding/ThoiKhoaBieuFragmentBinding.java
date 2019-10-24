package com.khoa.myptit.thoikhoabieu.binding;

/*
 * Created at 10/3/19 2:33 PM by Khoa
 */

import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

public class ThoiKhoaBieuFragmentBinding {
    @BindingAdapter("setCurrentItem")
    public static void setCurrentItem(ViewPager viewPager, int position){
        viewPager.setCurrentItem(position, true);
    }
}
