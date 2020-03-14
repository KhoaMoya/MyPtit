package com.khoa.myptit.xemdiem.viewmodel;

/*
 * Created at 10/21/19 9:12 PM by Khoa
 */

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.khoa.myptit.base.model.User;
import com.khoa.myptit.base.net.Downloader;
import com.khoa.myptit.base.net.HtmlGetter;
import com.khoa.myptit.base.net.LoginPoster;
import com.khoa.myptit.base.repository.BaseRepository;
import com.khoa.myptit.base.util.ParseResponse;
import com.khoa.myptit.xemdiem.model.DiemHocKy;
import com.khoa.myptit.xemdiem.model.DiemMonHoc;
import com.khoa.myptit.xemdiem.net.DownloadDocumentAllDiem;
import com.khoa.myptit.xemdiem.util.ParseDiem;

import java.util.ArrayList;
import java.util.TreeMap;

public class XemDiemViewModel extends ViewModel {

    public final static String GetTag = "get_xemdiem";
    public final static String LoginTag = "login_xemdiem";
    public final static String PostTag = "post_xemdiem";
    public final String URLXemDiem = "http://qldt.ptit.edu.vn/Default.aspx?page=xemdiemthi";

    private Context mContext;
    public MutableLiveData<ArrayList<DiemHocKy>> mListDiemHocKy;
    public TreeMap<String, DiemMonHoc> mTreeMapMonHoc;
    public TreeMap<String, ArrayList> mTreeMapDiem4;
    public ArrayList<PieEntry> mEntryList;
    public ArrayList<DiemMonHoc> mListHocLai;
    public ArrayList<DiemMonHoc> mListHocCaiThien;
    public ArrayList<Integer> mListColor;

    public MutableLiveData<Boolean> loginError;
    public MutableLiveData<Exception> mException;

    public void init(Context context) {
        mContext = context;
        mListDiemHocKy = new MutableLiveData<>(new ArrayList<DiemHocKy>());
        mTreeMapMonHoc = new TreeMap<>();
        mTreeMapDiem4 = new TreeMap<>();
        mEntryList = new ArrayList<>();
        mListHocLai = new ArrayList<>();
        mListHocCaiThien = new ArrayList<>();
        loginError = new MutableLiveData<>();
        mException = new MutableLiveData<>();

        mListColor = new ArrayList<>();
        for (int c : ColorTemplate.PASTEL_COLORS) mListColor.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS) mListColor.add(c);
    }

    public void refreshDiem() {
        new HtmlGetter(GetTag, URLXemDiem, new BaseRepository<User>().read(mContext, User.mFileName)).start();
    }

    public void loadAllDiem() {
        ArrayList<DiemHocKy> list = new BaseRepository<ArrayList<DiemHocKy>>().read(mContext, DiemMonHoc.mFileName);
        if (list == null) {
            refreshDiem();
        } else {
            mListDiemHocKy.postValue(list);
        }
    }

    public void convertToPieChartData(ArrayList<DiemHocKy> diemHocKyArrayList) {
        ParseDiem.convertToTreeMapMonHoc(this, diemHocKyArrayList);
        mTreeMapDiem4 = ParseDiem.convertToTreeMapDiem(mTreeMapMonHoc);
        mEntryList = ParseDiem.convertToPieEntries(mTreeMapDiem4);
    }

    public void getXemDiem(Downloader downloader) {
        if (ParseResponse.checkLogin(mContext, downloader)) {
            new DownloadDocumentAllDiem(PostTag, URLXemDiem, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        } else {
            new LoginPoster(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public void postXemDiem(Downloader downloader) {
        if (ParseResponse.checkLogin(mContext, downloader)) {
            ArrayList<DiemHocKy> list = ParseDiem.convertToListHocKy(mContext, downloader);
            new BaseRepository<ArrayList<DiemHocKy>>().write(mContext, DiemMonHoc.mFileName, list);
            mListDiemHocKy.postValue(list);
        } else {
            new LoginPoster(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public void loginXemDiem(Downloader downloader) {
        if (ParseResponse.checkLoginWithPost(mContext, downloader)) {
            new HtmlGetter(GetTag, URLXemDiem, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        } else {
            loginError.postValue(true);
//            new LoginPoster(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

}
