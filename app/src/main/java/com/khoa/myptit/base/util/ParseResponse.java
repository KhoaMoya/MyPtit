package com.khoa.myptit.base.util;

/*
 * Created at 9/25/19 11:07 AM by Khoa
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.khoa.myptit.base.dialog.MyDialog;
import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.base.model.User;
import com.khoa.myptit.base.net.Downloader;
import com.khoa.myptit.base.repository.BaseRepository;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Map;

public class ParseResponse {

    public static boolean checkLoginWithPost(Context mContext, Downloader downloader) {
        if (downloader == null) return false;
        if (downloader.getException() != null) {
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, "checkLoginWithPost", downloader.getException()));
            return false;
        }
        User mUser = downloader.getUser();
        Connection.Response mResponse = downloader.getResponse();
        Document mDocument = Jsoup.parse(mResponse.body());
        Element mElementUserName = mDocument.select("span[id=ctl00_Header1_ucLogout_lblNguoiDung]").first();
        Element viewState = mDocument.select("input[id=__VIEWSTATE]").first();

        Log.e("Loi", "Nguoi dung|" + mElementUserName.text() + "|");
        if (!mElementUserName.text().trim().equals("")) {

            // update cookie
            Map<String, String> loginCookies = mResponse.cookies();
            String mCookie = loginCookies.get(User.mKeyCookie);
            if (mCookie != null) {
                mUser.setCookie(mCookie);
            }

            // update viewstate
            if (!viewState.val().isEmpty()) mUser.setViewState(viewState.val());
            new BaseRepository<User>().write(mContext, User.mFileName, mUser);

            return true;
        }

        return false;
    }

    public static boolean checkLogin(Context mContext, Downloader downloader) {
        if (downloader == null) return false;
        if (downloader.getException() != null) {
            EventBus.getDefault().post(new EventMessager(EventMessager.EVENT.EXCEPTION, "checkLogin", downloader.getException()));
            return false;
        }
        User mUser = downloader.getUser();
        Connection.Response mResponse = downloader.getResponse();
        Document mDocument = Jsoup.parse(mResponse.body());
        Element mElementUserName = mDocument.select("span[id=ctl00_Header1_ucLogout_lblNguoiDung]").first();
        Element viewState = mDocument.select("input[id=__VIEWSTATE]").first();

        Log.e("Loi", "Nguoi dung|" + mElementUserName.text() + "|");
        if (!mElementUserName.text().trim().equals("Chào bạn")) {

            // update cookie
            Map<String, String> loginCookies = mResponse.cookies();
            String mCookie = loginCookies.get(User.mKeyCookie);
            if (mCookie != null) {
                mUser.setCookie(mCookie);
            }

            // update viewstate
            if (!viewState.val().isEmpty()) mUser.setViewState(viewState.val());
            new BaseRepository<User>().write(mContext, User.mFileName, mUser);

            return true;
        }

        return false;
    }

    public static void updateCookieAndViewState(Context context, Downloader downloader){
        if (downloader == null) return;
        if (downloader.getException() != null) {
            return;
        }
        User mUser = downloader.getUser();
        Connection.Response mResponse = downloader.getResponse();
        Document mDocument = Jsoup.parse(mResponse.body());
        Element viewState = mDocument.select("input[id=__VIEWSTATE]").first();

        Map<String, String> loginCookies = mResponse.cookies();
        String mCookie = loginCookies.get(User.mKeyCookie);
        if (mCookie != null) {
            mUser.setCookie(mCookie);
        }

        // update viewstate
        if (!viewState.val().isEmpty()) {
            mUser.setViewState(viewState.val());
        }
        new BaseRepository<User>().write(context, User.mFileName, mUser);
    }
}
