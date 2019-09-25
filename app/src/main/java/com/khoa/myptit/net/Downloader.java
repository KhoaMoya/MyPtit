package com.khoa.myptit.net;

import androidx.databinding.Observable;

import com.khoa.myptit.baseModel.User;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

/*
 * Created at 9/24/19 2:09 PM by Khoa
 */

public abstract class Downloader extends Thread {

    protected String mURL;
    protected Connection.Response mResponse;
    protected String mError;
    protected User mUser;

    public Downloader(String mURL, User mUser) {
        this.mURL = mURL;
        this.mUser = mUser;
        this.mError = "";
        this.mResponse = null;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String mURL) {
        this.mURL = mURL;
    }

    public Connection.Response getResponse() {
        return mResponse;
    }

    public void setResponse(Connection.Response mResponse) {
        this.mResponse = mResponse;
    }

    public String getError() {
        return mError;
    }

    public void setError(String mError) {
        this.mError = mError;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }
}
