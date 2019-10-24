package com.khoa.myptit.login.util;

/*
 * Created at 9/25/19 11:07 AM by Khoa
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.net.Downloader;
import com.khoa.myptit.login.repository.BaseRepository;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Map;

public class ParseResponse {

    public static boolean checkLogin(Context mContext, Downloader downloader) {
        if (downloader != null) {
            if (!downloader.getError().isEmpty()) {
                Toast.makeText(mContext, "Error parse document: " + downloader.getError(), Toast.LENGTH_SHORT).show();
            } else {
                User mUser = downloader.getUser();
                Connection.Response mResponse = downloader.getResponse();
                Document mDocument = Jsoup.parse(mResponse.body());
                Element mElementUserName = mDocument.select("span[id=ctl00_Header1_ucLogout_lblNguoiDung]").first();
                Element viewState = mDocument.select("input[id=__VIEWSTATE]").first();

                Log.e("Loi", "Nguoi dung: " + mElementUserName.text());
                if (!mElementUserName.text().equals("")) {

                    // update cookie
                    Map<String, String> loginCookies = mResponse.cookies();
                    String mCookie = loginCookies.get(User.mKeyCookie);
                    if (mCookie != null) {
                        Log.e("Loi", "sessionID: " + mCookie);
                        mUser.setCookie(mCookie);
                    }

                    // update viewstate
                    if(!viewState.val().isEmpty()) mUser.setViewState(viewState.val());
                    new BaseRepository<User>().write(mContext, User.mFileName, mUser);

                    return true;
                }


            }
            return false;
        }
        Log.e("Loi", "document null");
        return false;
    }
}
