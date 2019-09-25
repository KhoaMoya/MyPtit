package com.khoa.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/*
 * Created at 9/22/19 9:00 PM by Khoa
 */

public class LoginActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private String username = "B16DCCN193";
    private String password = "phamvankhoa";
    private String strUrl = "http://qldt.ptit.edu.vn/default.aspx?page=gioithieu&ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtMatKhau=phamvankhoa&ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtTaiKhoa=B16DCCN193&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=&__VIEWSTATEGENERATOR=CA0B0334&ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$btnDangNhap=Ä\u0090Ä\u0083ng Nháº\u00ADp";
    private String strUrlHome = "http://qldt.ptit.edu.vn";
    private String strUrlTKB = "http://qldt.ptit.edu.vn/Default.aspx?page=thoikhoabieu";

    private TextView txtTv;
    private String strRespone;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtTv = findViewById(R.id.text_view);
        initPermission();


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang kết nối");
        progressDialog.show();

        ParseHtml parseHtml = new ParseHtml();
        parseHtml.execute();
    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {

            } else {
                requestPermission();
            }
        } else {

        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public class ParseHtml extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                Connection.Response response = Jsoup.connect("http://qldt.ptit.edu.vn/default.aspx")
                        .data("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtMatKhau", "phamvankhoa")
                        .data("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtTaiKhoa", "B16DCCN193")
                        .data("__EVENTTARGET", "")
                        .data("__EVENTARGUMENT", "")
                        .data("__VIEWSTATE", "")
                        .data("__VIEWSTATEGENERATOR", "")
                        .data("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$btnDangNhap", "")
                        .method(Connection.Method.POST)
                        .timeout(2000)
                        .execute();
                Document document = response.parse();
                Map<String, String> loginCookies = response.cookies();
                Log.e("Loi", "session id: " + loginCookies.toString());

                Element element = document.select("span").first();
                if (element.text().equals("")) {
                    Log.e("Loi", "Chưa đăng nhập" );
                    strRespone = "Chưa đăng nhập";
                }
                else {
                    Log.e("Loi", "Đã đăng nhập" + element.text());
                    strRespone = element.text();
                }

                publishProgress();
                //Element elementCapcha = document.select("span[id=\"ctl00_ContentPlaceHolder1_ctl00_lblCapcha\"]").first();
                //if (elementCapcha != null) Log.e("Loi", elementCapcha.text());

//                Document docTKB = Jsoup.connect(strUrlTKB).cookies(loginCookies).get();
//                Elements elmTkb = docTKB.select("table[id=\"ctl00_ContentPlaceHolder1_ctl00_Table1\"]");
//                Log.e("Loi", elmTkb.html());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            txtTv.setText(strRespone);
            Log.e("Loi", strRespone);
            progressDialog.hide();
            super.onProgressUpdate(values);
        }

    }

}
