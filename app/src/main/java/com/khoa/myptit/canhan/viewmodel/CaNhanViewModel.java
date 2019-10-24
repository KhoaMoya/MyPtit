package com.khoa.myptit.canhan.viewmodel;

/*
 * Created at 10/18/19 9:53 PM by Khoa
 */

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.canhan.model.ThongTin;
import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.net.Downloader;
import com.khoa.myptit.login.net.LoginResponseGetter;
import com.khoa.myptit.login.net.ResponseGetter;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.login.util.ParseResponse;
import com.khoa.myptit.thoikhoabieu.model.HocKy;
import com.khoa.myptit.xemdiem.model.DiemMonHoc;
import com.khoa.myptit.xemhocphi.model.HocPhi;
import com.khoa.myptit.xemhocphi.viewmodel.HocPhiViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CaNhanViewModel extends ViewModel {

    private Context mContext;
    public static String GetTag = "getCaNhan";
    public static String LoginTag = "loginCaNhan";
    private final String URLThongTin = "http://qldt.ptit.edu.vn/Default.aspx?page=xemdiemthi";
    public MutableLiveData<ThongTin> mThongTin;
    public ObservableInt showLoading;
    public ObservableInt showThongTin;

    public void init(Context context){
        mContext = context;
        mThongTin = new MutableLiveData<>(new ThongTin());
        showLoading = new ObservableInt(View.VISIBLE);
        showThongTin = new ObservableInt(View.GONE);
    }

    public void deleteData(){
        Log.e("Loi", "delete file hocky.data :" + mContext.deleteFile(HocKy.mFileName));
        Log.e("Loi", "delete file user.data :" + mContext.deleteFile(User.mFileName));
        Log.e("Loi", "delete file thongtin.data: " + mContext.deleteFile(ThongTin.mFileName));
        Log.e("Loi", "delete file hocphi.data" + mContext.deleteFile(HocPhi.mFileName));
        Log.e("Loi", "delete file diem.data" + mContext.deleteFile(DiemMonHoc.mFileName));
    }

    public void loadThongTin(){
        showThongTin.set(View.GONE);
        showLoading.set(View.VISIBLE);
        ThongTin thongTin = new BaseRepository<ThongTin>().read(mContext, ThongTin.mFileName);
        if(thongTin==null) {
            Log.e("Loi", "null");
            new ResponseGetter(GetTag, URLThongTin , new BaseRepository<User>().read(mContext, User.mFileName)).start();
        } else {
            mThongTin.setValue(thongTin);
            showThongTin.set(View.VISIBLE);
            showLoading.set(View.GONE);
        }
    }

    public void getCaNhan(Downloader downloader){
        if(ParseResponse.checkLogin(mContext, downloader)){
            Document document = Jsoup.parse(downloader.getResponse().body());
            ThongTin thongTin = com.khoa.myptit.canhan.util.ParseResponse.convertToThongTin(document);
            new BaseRepository<ThongTin>().write(mContext, ThongTin.mFileName, thongTin);
            mThongTin.postValue(thongTin);
            showThongTin.set(View.VISIBLE);
            showLoading.set(View.GONE);
        } else {
            new LoginResponseGetter(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public void loginCaNhan(Downloader downloader){
        if(ParseResponse.checkLogin(mContext, downloader)){
            new ResponseGetter(GetTag, URLThongTin, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        } else {
            new LoginResponseGetter(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public String getHoTen(){
        return mThongTin.getValue().getHoTen();
    }

    public String getMaSV(){
        return mThongTin.getValue().getMaSV();
    }

    public String getNgaySinh(){
        return mThongTin.getValue().getNgaySinh();
    }

    public String getNoiSinh(){
        return mThongTin.getValue().getNoiSinh();
    }

    public String getLop(){
        return mThongTin.getValue().getLop();
    }

    public String getNganh(){
        return mThongTin.getValue().getNganh();
    }

    public String getKhoa(){
        return mThongTin.getValue().getKhoa();
    }

    public String getHeDaoTao(){
        return mThongTin.getValue().getHeDaoTao();
    }

    public String getKhoaHoc(){
        return mThongTin.getValue().getKhoaHoc();
    }

    public String getCvht(){
        return mThongTin.getValue().getCoVanHocTap();
    }
}
