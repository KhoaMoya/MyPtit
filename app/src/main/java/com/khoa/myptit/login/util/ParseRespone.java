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

public class ParseRespone {

    public static boolean checkLogin(Context mContext, Downloader downloader) {
        if (downloader != null) {
            if (!downloader.getError().isEmpty()) {
                Toast.makeText(mContext, "Error parse document: " + downloader.getError(), Toast.LENGTH_SHORT).show();
            } else {
                Connection.Response mResponse = downloader.getResponse();
                Document mDocument = Jsoup.parse(mResponse.body());
                Element mElementUserName = mDocument.select("span[id=\"ctl00_Header1_ucLogout_lblNguoiDung\"]").first();

                Log.e("Loi", "Nguoi dung: " + mElementUserName.text());
                if (!mElementUserName.text().equals("")) {
                    Map<String, String> loginCookies = mResponse.cookies();
                    String mCookie = loginCookies.get(User.mKeyCookie);

                    if (mCookie != null) {
                        User mUser = downloader.getUser();
                        mUser.setCookie(mCookie);
                        new BaseRepository<User>().write(mContext, User.mFileName, mUser);
                    }
                    return true;
                }
            }
            return false;
        }
        Log.e("Loi", "document null");
        return false;
    }
}
