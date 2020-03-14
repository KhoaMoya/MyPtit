package com.khoa.myptit.thoikhoabieu.viewmodel;

/*
 * Created at 9/28/19 11:36 PM by Khoa
 */

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.base.model.User;
import com.khoa.myptit.base.net.Downloader;
import com.khoa.myptit.base.net.HtmlGetter;
import com.khoa.myptit.base.repository.BaseRepository;
import com.khoa.myptit.thoikhoabieu.adapter.WeekViewPagerAdapter;
import com.khoa.myptit.thoikhoabieu.model.Semester;
import com.khoa.myptit.thoikhoabieu.model.Subject;
import com.khoa.myptit.thoikhoabieu.model.Week;
import com.khoa.myptit.thoikhoabieu.net.TKBByWeekPoster;
import com.khoa.myptit.thoikhoabieu.util.ParseResponseTKB;

import java.util.ArrayList;

public class ThoiKhoaBieuViewModel extends ViewModel {

    public enum STATUS_TKB{
        ERROR,
        LOADING,
        FINNISH_FROM_FILE,
        FINNISH_FROM_WEB
    }

    public static String GET_TKB_FIRST_PAGE = "get_tkb_first_page";
    public static String GET_TKB_BY_WEEK = "thoikhoabieu_tuan";
    private String mURL = "http://qldt.ptit.edu.vn/default.aspx?page=thoikhoabieu&sta=0&id=";

    public Context mContext;
    public MutableLiveData<Semester> mCurrentSemester;
    public MutableLiveData<Week> mCurrentWeek;
    private Semester tempSemester;

    private int mIndex;
    public MutableLiveData<Integer> mUpdateWeekIndex;
    public WeekViewPagerAdapter mPagerAdapter;
    public MutableLiveData<String> mMessage;
    public MutableLiveData<STATUS_TKB> mStatus;
    public int currentWeekIndex = -1;

    public void init(Context context) {
        this.mContext = context;
        mCurrentSemester = new MutableLiveData<>();
        mCurrentWeek = new MutableLiveData<>();
        mMessage = new MutableLiveData<>();
        mStatus = new MutableLiveData<>();
        mUpdateWeekIndex = new MutableLiveData<>();

        mURL += new BaseRepository<User>().read(context, User.mFileName).getMaSV();
    }


    public void loadThoiKhoaBieu() {
        mStatus.setValue(STATUS_TKB.LOADING);
        mMessage.setValue("Đang tải dữ liệu");

        Semester semester = new BaseRepository<Semester>().read(mContext, Semester.mFileName);
        if (semester != null) {
            showSemesterFromFile(semester);
        } else {
            mIndex = 0;
            new HtmlGetter(GET_TKB_FIRST_PAGE, mURL, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    private void showSemesterFromFile(Semester semester){
        mStatus.setValue(STATUS_TKB.FINNISH_FROM_FILE);
        mCurrentSemester.postValue(semester);
        mPagerAdapter.setWeeks(semester.getListTuan());
        if(currentWeekIndex==-1) {
            currentWeekIndex = ParseResponseTKB.getCurrentWeekIndex(semester.getListTuan());
        }
        mCurrentWeek.setValue(semester.getListTuan().get(currentWeekIndex));
    }

    public void refreshThoiKhoaBieu() {
        mStatus.setValue(STATUS_TKB.LOADING);
        mMessage.setValue("Đang tải dữ liệu");

        mIndex = 0;
        new HtmlGetter(GET_TKB_FIRST_PAGE, mURL, new BaseRepository<User>().read(mContext, User.mFileName)).start();
    }

    public void handlefirstPageTKB(Downloader downloader) {
        ParseResponseTKB.updateCookieAndViewState(mContext, downloader);

        tempSemester = ParseResponseTKB.getHocKy(mContext, downloader);
        if (tempSemester.getMaHocKy().isEmpty()) {
            mMessage.postValue("Học kỳ không xác định");
            mStatus.postValue(STATUS_TKB.ERROR);
            return;
        }

        ArrayList<Week> mListWeek = ParseResponseTKB.getListTuan(mContext, downloader);
        if (!mListWeek.isEmpty()) {
            tempSemester.setListWeek(mListWeek);
            new BaseRepository<Semester>().write(mContext, Semester.mFileName, tempSemester);
            mCurrentSemester.postValue(tempSemester);

            mPagerAdapter.setWeeks(mListWeek);
            if(currentWeekIndex==-1) {
                currentWeekIndex = ParseResponseTKB.getCurrentWeekIndex(mListWeek);
                mCurrentWeek.postValue(mCurrentSemester.getValue().getListTuan().get(currentWeekIndex));
            }

            postNextWeek();
        } else {
            mMessage.postValue("Danh sách tuần trong học kỳ trống");
            mStatus.postValue(STATUS_TKB.ERROR);
        }
    }

    public void handleNextPageTKB(Downloader downloader) {
        ParseResponseTKB.updateCookieAndViewState(mContext, downloader);

        ArrayList<Subject> subjects = ParseResponseTKB.parseDocumentTKB(mContext, downloader);
        tempSemester.getListTuan().get(mIndex).setSubjects(subjects);

        // update week
        mPagerAdapter.updateWeek(mIndex, tempSemester.getListTuan().get(mIndex));
        new BaseRepository<Semester>().write(mContext, Semester.mFileName, tempSemester);

        if (mIndex < tempSemester.getListTuan().size()-1) {
            mIndex++;
            postNextWeek();
        } else {
            mMessage.postValue("Hoàn tất tải dữ liệu");
            mStatus.postValue(STATUS_TKB.FINNISH_FROM_WEB);
        }
    }

    private void postNextWeek() {
        mStatus.postValue(STATUS_TKB.LOADING);
        mMessage.postValue("Đang tải dữ liệu: " + tempSemester.getListTuan().get(mIndex).getTenTuan());

        Log.e("Loi", "Đang tải dữ liệu: " + tempSemester.getListTuan().get(mIndex).getTenTuan());
        new TKBByWeekPoster(GET_TKB_BY_WEEK, mURL, new BaseRepository<User>().read(mContext, User.mFileName)
                , tempSemester.getMaHocKy(), tempSemester.getListTuan().get(mIndex).getValue()).start();
    }

    public void setCurrentweek(int currentWeekIndex){
        this.currentWeekIndex = currentWeekIndex;
        this.mCurrentWeek.setValue(mCurrentSemester.getValue().getListTuan().get(currentWeekIndex));
    }

}
