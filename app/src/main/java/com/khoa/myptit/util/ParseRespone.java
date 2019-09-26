package com.khoa.myptit.util;

/*
 * Created at 9/25/19 11:07 AM by Khoa
 */

import android.content.Context;
import android.util.Log;

import com.khoa.myptit.baseModel.User;
import com.khoa.myptit.baseRepository.BaseRepository;
import com.khoa.myptit.baseNet.Downloader;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;

public class ParseRespone {

    public static boolean checkLogin(Context mContext, Downloader downloader) {
        if (downloader != null) {
            if (downloader.getError().equals("")) {
                Connection.Response mResponse = downloader.getResponse();
                try {
                    Document mDocument = mResponse.parse();
                    Element mElementUserName = mDocument.select("span[id=\"ctl00_Header1_ucLogout_lblNguoiDung\"]").first();
                    Log.e("Loi", mElementUserName.text());
                    if (mElementUserName != null) {
                        if (!mElementUserName.text().equals("")) {
                            Map<String, String> loginCookies = mResponse.cookies();
                            String mCookie = loginCookies.get(User.mKeyCookie);

                            if (mCookie != null) {
                                User mUser = new BaseRepository<User>().read(mContext, User.mFileName);
                                mUser.setCookie(mCookie);
                                new BaseRepository<User>().write(mContext, User.mFileName, mUser);
                            }
                            return true;
                        }
                    }
                } catch (IOException e) {
                    Log.e("Loi", e.getMessage());
                }
            }
        }
        return false;
    }


}
