package com.khoa.myptit.thoikhoabieu.viewmodel;

/*
 * Created at 9/28/19 11:36 PM by Khoa
 */

import android.content.Context;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.net.Downloader;
import com.khoa.myptit.login.net.LoginResponseGetter;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.login.util.ParseRespone;
import com.khoa.myptit.thoikhoabieu.adapter.ScreenSlidePagerAdapter;
import com.khoa.myptit.thoikhoabieu.model.HocKy;
import com.khoa.myptit.thoikhoabieu.model.Tuan;
import com.khoa.myptit.thoikhoabieu.net.TKBTheoTuanPoster;
import com.khoa.myptit.thoikhoabieu.util.ParseResponse;

import java.util.ArrayList;

public class ThoiKhoaBieuViewModel extends ViewModel {

    public static String TAG_GET = "thoikhoabieu";
    public static String TAG_POST = "thoikhoabieu_tuan";
    private final String mURL = "http://qldt.ptit.edu.vn/default.aspx?page=thoikhoabieu&sta=0";
    public Context mContext;
    public MutableLiveData<HocKy> mHocKy;
    private HocKy tempHocKy;
    private int index;
    public ScreenSlidePagerAdapter mPagerAdapter;
    public ObservableField<String> mTenHocKy;
    public ObservableField<String> mTenTuan;
    public ObservableField<String> mThoiGian;
    public ObservableInt mPosition;
    public ObservableInt showLoading;
    public ObservableInt showTKB;

    public void init(Context context, FragmentManager fm) {
        this.mContext = context;
        mHocKy = new MutableLiveData<>(new HocKy());
        mPagerAdapter = new ScreenSlidePagerAdapter(fm);
        mPagerAdapter.setHocKy(new HocKy());
        mTenHocKy = new ObservableField<>();
        mTenTuan = new ObservableField<>();
        mThoiGian = new ObservableField<>();
        mPosition = new ObservableInt();
        showLoading = new ObservableInt(View.VISIBLE);
        showTKB = new ObservableInt(View.VISIBLE);
    }

    public void responseGetterThoiKhoaBieu() {
        showTKB.set(View.VISIBLE);
        showLoading.set(View.GONE);

        mHocKy.postValue(new BaseRepository<HocKy>().read(mContext, HocKy.mFileName));

//        index = 0;
//        new ResponseGetter(TAG_GET, mURL, new BaseRepository<User>().read(mContext, User.mFileName)).start();
    }

    public void checkLoginGetTKB(Downloader downloader) {
        if (ParseRespone.checkLogin(mContext, downloader)) {
            tempHocKy = ParseResponse.getHocKy(downloader);
            ArrayList<Tuan> mListTuan = ParseResponse.getListTuan(downloader);
            if (!mListTuan.isEmpty()) {
                tempHocKy.setListTuan(mListTuan);
                postNextTuan();
            }
        } else {
            new LoginResponseGetter(new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public void checkLoginPostTKB(Downloader downloader) {
        if (ParseRespone.checkLogin(mContext, downloader)) {
            tempHocKy.getListTuan().get(index).setMonHocs(ParseResponse.parseDocumentTKB(mContext, downloader));
            index++;
            if (index != tempHocKy.getListTuan().size()) {
                postNextTuan();
            } else {
                showLoading.set(View.GONE);
                showTKB.set(View.VISIBLE);
                mHocKy.postValue(tempHocKy);
                new BaseRepository<HocKy>().write(mContext, HocKy.mFileName, tempHocKy);
            }

        } else {
            new LoginResponseGetter(new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public void checkLogin(Downloader downloader) {
        if (ParseRespone.checkLogin(mContext, downloader)) {
            responseGetterThoiKhoaBieu();
        } else {
            new LoginResponseGetter(new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    private void postNextTuan() {
        new TKBTheoTuanPoster(TAG_POST, mURL, new BaseRepository<User>().read(mContext, User.mFileName)
                , tempHocKy.getMaHocKy(), tempHocKy.getListTuan().get(index).getValue()).start();
    }

    public void onHocKyChanged(HocKy hocKy) {
        if (hocKy != null) {
            mTenHocKy.set(hocKy.getTenHocKy());
            mPagerAdapter.setHocKy(hocKy);
            int position = ParseResponse.getCurrentTuan(hocKy.getListTuan());
            mPosition.set(position);
            mPagerAdapter.notifyDataSetChanged();
        }
    }

    public void onTuanChanged(int position) {
        Tuan tuan = mHocKy.getValue().getListTuan().get(position);
        mTenTuan.set(tuan.getTenTuan());
        mThoiGian.set(tuan.getNgayBatDau() + " - " + tuan.getNgayKetThuc());
    }
}
