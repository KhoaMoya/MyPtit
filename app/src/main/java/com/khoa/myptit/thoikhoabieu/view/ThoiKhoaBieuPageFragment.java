package com.khoa.myptit.thoikhoabieu.view;

/*
 * Created at 10/3/19 9:37 AM by Khoa
 */

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.khoa.myptit.R;
import com.khoa.myptit.databinding.FragmentViewpageTkbBinding;
import com.khoa.myptit.thoikhoabieu.model.Subject;
import com.khoa.myptit.thoikhoabieu.model.Week;
import com.khoa.scheduleview.OnScheduleClickListener;
import com.khoa.scheduleview.SimpleSubject;

import java.util.ArrayList;

public class ThoiKhoaBieuPageFragment extends Fragment {

    private Week mWeek;
    private FragmentViewpageTkbBinding mBinding;

    public ThoiKhoaBieuPageFragment() {
    }

    private ThoiKhoaBieuPageFragment(Week week) {
        mWeek = week;
    }

    public static ThoiKhoaBieuPageFragment newIntance(Week week){
        return new ThoiKhoaBieuPageFragment(week);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentViewpageTkbBinding.inflate(inflater,container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState!=null) mWeek = (Week) savedInstanceState.getSerializable("week_data");

        showSchedule(mWeek.getSubjects());
    }

    private void showSchedule(final ArrayList<Subject> subjects){
        SimpleSubject[] subArr = new SimpleSubject[subjects.size()];
        for(int i = 0; i<subjects.size(); i++){
            Subject sub = subjects.get(i);
            subArr[i] = new SimpleSubject(sub.day, sub.startLesson, sub.durationLesson, sub.subjectName, sub.roomName);
        }

        mBinding.scheculeView.setSubjects(subArr);

        mBinding.scheculeView.setOnScheduleClickListener(new OnScheduleClickListener() {
            @Override
            public void onClickSubject(int subjectIndex) {
                new DetailSubjectDialog(getContext()).showDetailSubject(subjects.get(subjectIndex));
            }

            @Override
            public void onLongClickSubject(int subjectIndex) {
            }

            @Override
            public void onClickAddEvent(int day, int startLesson) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("week_data", mWeek);
    }
}