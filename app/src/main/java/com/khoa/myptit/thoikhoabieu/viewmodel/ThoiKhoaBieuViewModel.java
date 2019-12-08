package com.khoa.myptit.thoikhoabieu.viewmodel;

/*
 * Created at 9/28/19 11:36 PM by Khoa
 */

import android.content.Context;
import android.text.Html;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.net.Downloader;
import com.khoa.myptit.login.net.LoginResponseGetter;
import com.khoa.myptit.login.net.ResponseGetter;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.login.util.ParseResponse;
import com.khoa.myptit.main.Utils;
import com.khoa.myptit.thoikhoabieu.adapter.ScreenSlidePagerAdapter;
import com.khoa.myptit.thoikhoabieu.model.HocKy;
import com.khoa.myptit.thoikhoabieu.model.Tuan;
import com.khoa.myptit.thoikhoabieu.net.TKBTheoTuanPoster;

import java.util.ArrayList;

public class ThoiKhoaBieuViewModel extends ViewModel {

    public static String TAG_GET = "thoikhoabieu";
    public static String TAG_POST = "thoikhoabieu_tuan";
    public static String TAG_LOGIN = "login_thoikhoabieu";
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
    public ObservableField<String> mLastUpdate;
    public ObservableField<String> mStatus;

    public void init(Context context, FragmentManager fm) {
        this.mContext = context;
        mHocKy = new MutableLiveData<>();

        mTenHocKy = new ObservableField<>();
        mTenTuan = new ObservableField<>();
        mThoiGian = new ObservableField<>();
        mPosition = new ObservableInt();
        showLoading = new ObservableInt(View.VISIBLE);
        showTKB = new ObservableInt(View.GONE);
        mLastUpdate = new ObservableField<>();
        mStatus = new ObservableField<>();

        mPagerAdapter = new ScreenSlidePagerAdapter(fm);
        mPagerAdapter.setHocKy(new HocKy());
    }

    public void loadThoiKhoaBieu() {
        showTKB.set(View.GONE);
        showLoading.set(View.VISIBLE);

        HocKy hocKy = new BaseRepository<HocKy>().read(mContext, HocKy.mFileName);
        if (hocKy != null) mHocKy.postValue(hocKy);
        else {
            index = 0;
            new ResponseGetter(TAG_GET, mURL, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public void refreshThoiKhoaBieu() {
        showTKB.set(View.GONE);
        showLoading.set(View.VISIBLE);

        new ResponseGetter(TAG_GET, mURL, new BaseRepository<User>().read(mContext, User.mFileName)).start();
    }

    public void checkLoginGetTKB(Downloader downloader) {
        if (!downloader.getError().isEmpty()) {
            showErrorStatus(downloader.getError());
        } else {
            if (ParseResponse.checkLogin(mContext, downloader)) {
                tempHocKy = com.khoa.myptit.thoikhoabieu.util.ParseResponse.getHocKy(downloader);
                ArrayList<Tuan> mListTuan = com.khoa.myptit.thoikhoabieu.util.ParseResponse.getListTuan(downloader);
                if (!mListTuan.isEmpty()) {
                    tempHocKy.setListTuan(mListTuan);
                    postNextTuan();
                }
            } else {
                new LoginResponseGetter(TAG_GET, new BaseRepository<User>().read(mContext, User.mFileName)).start();
            }
        }
    }

    public void checkLoginPostTKB(Downloader downloader) {
        if (!downloader.getError().isEmpty()) {
            showErrorStatus(downloader.getError());
        } else {
            if (ParseResponse.checkLogin(mContext, downloader)) {
                tempHocKy.getListTuan().get(index).setMonHocs(com.khoa.myptit.thoikhoabieu.util.ParseResponse.parseDocumentTKB(mContext, downloader));
                index++;
                if (index != tempHocKy.getListTuan().size()) {
                    postNextTuan();
                } else {
                    showLoading.set(View.GONE);
                    showTKB.set(View.VISIBLE);
                    tempHocKy.setLastUpdate(Utils.getCurrentTime());
                    mHocKy.postValue(tempHocKy);
                    new BaseRepository<HocKy>().write(mContext, HocKy.mFileName, tempHocKy);
                }

            } else {
                new LoginResponseGetter(TAG_LOGIN, new BaseRepository<User>().read(mContext, User.mFileName)).start();
            }
        }
    }

    public void checkLogin(Downloader downloader) {
        if (!downloader.getError().isEmpty()) {
            showErrorStatus(downloader.getError());
        } else {
            if (ParseResponse.checkLogin(mContext, downloader)) {
                loadThoiKhoaBieu();
            } else {
                new LoginResponseGetter(TAG_LOGIN, new BaseRepository<User>().read(mContext, User.mFileName)).start();
            }
        }
    }

    private void postNextTuan() {
        showLoadingStatus("Đang tải môn học: " + tempHocKy.getListTuan().get(index).getTenTuan());
        new TKBTheoTuanPoster(TAG_POST, mURL, new BaseRepository<User>().read(mContext, User.mFileName)
                , tempHocKy.getMaHocKy(), tempHocKy.getListTuan().get(index).getValue()).start();
    }

    public void onHocKyChanged(HocKy hocKy) {
        if (hocKy != null) {
            showTKB.set(View.VISIBLE);
            showLoading.set(View.GONE);
            mLastUpdate.set("Cập nhật lần cuối: " + hocKy.getLastUpdate());
            mTenHocKy.set(hocKy.getTenHocKy());
            mPagerAdapter.setHocKy(hocKy);
            int position = com.khoa.myptit.thoikhoabieu.util.ParseResponse.getCurrentTuan(hocKy.getListTuan());
            mPosition.set(position);
            mPagerAdapter.notifyDataSetChanged();

        }
    }

    public void onTuanChanged(int position) {
        Tuan tuan = mHocKy.getValue().getListTuan().get(position);
        mTenTuan.set(tuan.getTenTuan());
        mThoiGian.set(tuan.getNgayBatDau() + " - " + tuan.getNgayKetThuc());
    }

    private void showErrorStatus(String string) {
        mStatus.set(Html.fromHtml("<p><font color=\"red\">Có lỗi xảy ra</font><p>") + string);
    }

    private void showLoadingStatus(String string) {
        mStatus.set(string);
    }

}
