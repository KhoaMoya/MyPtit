package com.khoa.myptit.thoikhoabieu.view;

/*
 * Created at 9/28/19 11:36 PM by Khoa
 */

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.khoa.myptit.R;
import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.base.net.Downloader;
import com.khoa.myptit.databinding.FragmentThoikhoabieuBinding;
import com.khoa.myptit.thoikhoabieu.adapter.WeekViewPagerAdapter;
import com.khoa.myptit.thoikhoabieu.model.Semester;
import com.khoa.myptit.thoikhoabieu.model.Week;
import com.khoa.myptit.thoikhoabieu.viewmodel.ThoiKhoaBieuViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/*
 * Created at 10/3/19 9:36 AM by Khoa
 */

public class ThoiKhoaBieuFragment extends Fragment {

    public static final String TAG = "thoikhoabieu_fragment";
    private ThoiKhoaBieuViewModel mViewModel;
    private FragmentThoikhoabieuBinding mBinding;

    public ThoiKhoaBieuFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentThoikhoabieuBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(ThoiKhoaBieuViewModel.class);
        if (savedInstanceState == null) mViewModel.init(getContext());

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupBindings();

        setupViewPagerWeek();

        setupClickTuan();

        mViewModel.loadThoiKhoaBieu();
    }

    private void setupClickTuan() {
        mBinding.selectTuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectWeekDialog dialog = new SelectWeekDialog(getContext(), mViewModel.currentWeekIndex, mViewModel.mCurrentSemester.getValue().getListTuan());
                dialog.show(new SelectWeekDialog.OnCheckWeekRadioButtonListener() {
                    @Override
                    public void onWeekSelected(int index) {
                        mViewModel.setCurrentweek(index);
                    }
                });
            }
        });

        mBinding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.viewpager.arrowScroll(View.FOCUS_RIGHT);
            }
        });
        mBinding.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.viewpager.arrowScroll(View.FOCUS_LEFT);
            }
        });

        mBinding.txtRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.refreshThoiKhoaBieu();
            }
        });
    }

    private void setupViewPagerWeek() {
        mViewModel.mPagerAdapter = new WeekViewPagerAdapter(this.getChildFragmentManager());
        mBinding.viewpager.setAdapter(mViewModel.mPagerAdapter);

        mBinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(mViewModel.currentWeekIndex!= position) {
                    mViewModel.setCurrentweek(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupBindings() {
        mViewModel.mCurrentSemester.observe(getViewLifecycleOwner(), new Observer<Semester>() {
            @Override
            public void onChanged(Semester semester) {
                mBinding.txtHocky.setText(semester.getTenHocKy());
                mBinding.txtLastUpdate.setText("Cập nhật lần cuối:" + semester.getLastUpdate());
//                mViewModel.updateAdapterViewPagerTKB(semester.getListTuan());
            }
        });

        mViewModel.mCurrentWeek.observe(getViewLifecycleOwner(), new Observer<Week>() {
            @Override
            public void onChanged(Week week) {
                mBinding.txtTuan.setText(week.getTenTuan());
                mBinding.txtTime.setText(week.getNgayBatDau() + " - " + week.getNgayKetThuc());

                if(mBinding.viewpager.getCurrentItem() != mViewModel.currentWeekIndex){
                    mBinding.viewpager.setCurrentItem(mViewModel.currentWeekIndex);
                }
            }
        });

        mViewModel.mStatus.observe(getViewLifecycleOwner(), new Observer<ThoiKhoaBieuViewModel.STATUS_TKB>() {
            @Override
            public void onChanged(ThoiKhoaBieuViewModel.STATUS_TKB status_tkb) {
                onStatusChange(status_tkb);
            }
        });

        mViewModel.mMessage.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.txtStatus.setText(s);
            }
        });
    }

    @Subscribe
    public void onEventMessage(EventMessager eventMessager) {
        if (eventMessager.getEvent() == EventMessager.EVENT.DOWNLOAD_FINNISH) {
            if (eventMessager.getTag().equals(ThoiKhoaBieuViewModel.GET_TKB_FIRST_PAGE)) {
                mViewModel.handlefirstPageTKB((Downloader) eventMessager.getData());
            } else if (eventMessager.getTag().equals(ThoiKhoaBieuViewModel.GET_TKB_BY_WEEK)) {
                mViewModel.handleNextPageTKB((Downloader) eventMessager.getData());
            }
        }
    }

    private void onStatusChange(ThoiKhoaBieuViewModel.STATUS_TKB statusTkb){
        switch (statusTkb){
            case FINNISH_FROM_FILE:
                mBinding.statusLayout.setVisibility(View.GONE);
                break;
            case ERROR:
                mBinding.progress.setVisibility(View.GONE);
                mBinding.imgStatus.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_error));
                mBinding.imgStatus.setVisibility(View.VISIBLE);
                mBinding.statusLayout.setVisibility(View.VISIBLE);
                break;
            case FINNISH_FROM_WEB:
                mBinding.progress.setVisibility(View.GONE);
                mBinding.imgStatus.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_success));
                mBinding.imgStatus.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.statusLayout.setVisibility(View.GONE);
                    }
                }, 1000);
                break;
            case LOADING:
                mBinding.statusLayout.setVisibility(View.VISIBLE);
                mBinding.progress.setVisibility(View.VISIBLE);
                mBinding.imgStatus.setVisibility(View.GONE);
                break;
            default: break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
