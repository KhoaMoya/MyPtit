package com.khoa.myptit.xemdiem.viewmodel;

/*
 * Created at 10/21/19 9:12 PM by Khoa
 */

import android.content.Context;
import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.net.Downloader;
import com.khoa.myptit.login.net.LoginResponseGetter;
import com.khoa.myptit.login.net.ResponseGetter;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.login.util.ParseResponse;
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
    public ObservableInt showLoading;
    public ObservableInt showDiem;
    public TreeMap<String, DiemMonHoc> mTreeMapMonHoc;
    public TreeMap<String, ArrayList> mTreeMapDiem4;
    public ArrayList<PieEntry> mEntryList;
    public ArrayList<DiemMonHoc> mListHocLai;
    public ArrayList<DiemMonHoc> mListHocCaiThien;
    public  ArrayList<Integer> mListColor;

    public void init(Context context){
        mContext = context;
        mListDiemHocKy = new MutableLiveData<>(new ArrayList<DiemHocKy>());
        showLoading = new ObservableInt(View.VISIBLE);
        showDiem = new ObservableInt(View.GONE);
        mTreeMapMonHoc = new TreeMap<>();
        mTreeMapDiem4 = new TreeMap<>();
        mEntryList = new ArrayList<>();
        mListHocLai = new ArrayList<>();
        mListHocCaiThien = new ArrayList<>();

        mListColor = new ArrayList<>();
        for (int c : ColorTemplate.PASTEL_COLORS) mListColor.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS) mListColor.add(c);
    }

    public void refreshDiem(){
        showLoading.set(View.VISIBLE);
        showDiem.set(View.GONE);
        new ResponseGetter(GetTag, URLXemDiem, new BaseRepository<User>().read(mContext, User.mFileName)).start();
    }

    public void loadAllDiem(){
        ArrayList<DiemHocKy> list = new BaseRepository<ArrayList<DiemHocKy>>().read(mContext, DiemMonHoc.mFileName);
        if(list==null) {
            refreshDiem();
        }else{
            showLoading.set(View.GONE);
            showDiem.set(View.VISIBLE);
            mListDiemHocKy.postValue(list);
        }
    }

    public void convertToPieChartData(ArrayList<DiemHocKy> diemHocKyArrayList){
        ParseDiem.convertToTreeMapMonHoc(this, diemHocKyArrayList);
        mTreeMapDiem4 = ParseDiem.convertToTreeMapDiem(mTreeMapMonHoc);
        mEntryList = ParseDiem.convertToPieEntries(mTreeMapDiem4);
    }

    public void getXemDiem(Downloader downloader){
        if(ParseResponse.checkLogin(mContext, downloader)){
            new DownloadDocumentAllDiem(PostTag, URLXemDiem, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }else{
            new LoginResponseGetter(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public void postXemDiem(Downloader downloader){
        if(ParseResponse.checkLogin(mContext, downloader)){
            ArrayList<DiemHocKy> list = ParseDiem.convertToListHocKy(mContext, downloader);
            mListDiemHocKy.postValue(list);
            showLoading.set(View.GONE);
            showDiem.set(View.VISIBLE);
        } else {
            new LoginResponseGetter(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public void loginXemDiem(Downloader downloader){
        if(ParseResponse.checkLogin(mContext, downloader)){
            new ResponseGetter(GetTag, URLXemDiem, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        } else {
            new LoginResponseGetter(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

}
