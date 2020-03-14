package com.khoa.myptit.canhan.viewmodel;

/*
 * Created at 10/18/19 9:53 PM by Khoa
 */

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoa.myptit.canhan.model.ThongTin;
import com.khoa.myptit.base.model.User;
import com.khoa.myptit.base.net.Downloader;
import com.khoa.myptit.base.net.LoginPoster;
import com.khoa.myptit.base.repository.BaseRepository;
import com.khoa.myptit.base.util.ParseResponse;
import com.khoa.myptit.canhan.net.ProfileDowloader;
import com.khoa.myptit.canhan.util.ParseResponseCN;
import com.khoa.myptit.thoikhoabieu.model.Semester;
import com.khoa.myptit.xemdiem.model.DiemMonHoc;
import com.khoa.myptit.xemhocphi.model.HocPhi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CaNhanViewModel extends ViewModel {

    private Context mContext;
    public static String GetTag = "getCaNhan";
    public static String LoginTag = "loginCaNhan";
    private final String URLThongTin = "http://qldt.ptit.edu.vn/Default.aspx?page=xemdiemthi";
    public MutableLiveData<ThongTin> mThongTin;
    public MutableLiveData<Boolean> loginError;

    public void init(Context context){
        mContext = context;
        mThongTin = new MutableLiveData<>(new ThongTin());
        loginError = new MutableLiveData<>();
    }

    public void deleteData(){
        Log.e("Loi", "delete file hocky.data :" + mContext.deleteFile(Semester.mFileName));
        Log.e("Loi", "delete file user.data :" + mContext.deleteFile(User.mFileName));
        Log.e("Loi", "delete file thongtin.data: " + mContext.deleteFile(ThongTin.mFileName));
        Log.e("Loi", "delete file hocphi.data" + mContext.deleteFile(HocPhi.mFileName));
        Log.e("Loi", "delete file diem.data" + mContext.deleteFile(DiemMonHoc.mFileName));
    }

    public void loadThongTin(){
        ThongTin thongTin = new BaseRepository<ThongTin>().read(mContext, ThongTin.mFileName);
        if(thongTin==null) {
            new ProfileDowloader(GetTag, URLThongTin , new BaseRepository<User>().read(mContext, User.mFileName)).start();
        } else {
            mThongTin.setValue(thongTin);
        }
    }

    public void getCaNhan(Downloader downloader){
        if(ParseResponse.checkLogin(mContext, downloader)){
            Document document = Jsoup.parse(downloader.getResponse().body());
            ThongTin thongTin = ParseResponseCN.convertToThongTin(document);
            new BaseRepository<ThongTin>().write(mContext, ThongTin.mFileName, thongTin);
            mThongTin.postValue(thongTin);
        } else {
            new LoginPoster(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }

    public void loginCaNhan(Downloader downloader){
        if(ParseResponse.checkLoginWithPost(mContext, downloader)){
            new ProfileDowloader(GetTag, URLThongTin, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        } else {
            loginError.postValue(true);
//            new LoginPoster(LoginTag, new BaseRepository<User>().read(mContext, User.mFileName)).start();
        }
    }
}
