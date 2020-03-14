package com.khoa.myptit.base.net;

import com.khoa.myptit.base.model.User;

import org.jsoup.Connection;

/*
 * Created at 9/24/19 2:09 PM by Khoa
 */


public abstract class Downloader extends Thread {

    protected String mURL;
    protected Connection.Response mResponse;
    protected Exception mException;
    protected User mUser;
    protected String mTag;

    public Downloader(String tag, String mURL, User mUser) {
        this.mURL = mURL;
        this.mUser = mUser;
        this.mResponse = null;
        this.mTag = tag;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String mTag) {
        this.mTag = mTag;
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

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }

    public Exception getException() {
        return mException;
    }
}
