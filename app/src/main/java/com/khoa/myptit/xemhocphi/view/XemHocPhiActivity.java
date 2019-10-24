package com.khoa.myptit.xemhocphi.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.khoa.myptit.R;
import com.khoa.myptit.databinding.ActivityXemHocPhiBinding;
import com.khoa.myptit.login.net.Downloader;
import com.khoa.myptit.thoikhoabieu.model.MonHoc;
import com.khoa.myptit.xemhocphi.model.HocPhi;
import com.khoa.myptit.xemhocphi.viewmodel.HocPhiViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/*
 * Created at 10/20/19 5:24 PM by Khoa
 */

/*
 * Created at 10/20/19 7:22 PM by Khoa
 */

public class XemHocPhiActivity extends AppCompatActivity {

    private ActivityXemHocPhiBinding mBinding;
    private HocPhiViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBinding(savedInstanceState);

        setupHocPhiListener();

        mViewModel.loadHocPhi();
    }

    public void setupBinding(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_xem_hoc_phi);
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewModel = ViewModelProviders.of(this).get(HocPhiViewModel.class);
        if (savedInstanceState == null) mViewModel.init(this);

        mBinding.setViewmodel(mViewModel);

        mBinding.refreshHocPhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.refreshHocPhi();
            }
        });

        mBinding.scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int maxDistance = mBinding.layoutSTK.getHeight();
                Log.e("Loi", "maxDistance" + maxDistance);
                int movement = mBinding.scrollview.getScrollY();
                if (movement >= 0 && movement <= maxDistance) {
                    mBinding.layoutSTK.setTranslationY(-movement/2);
                }
            }
        });
    }

    private void setupHocPhiListener() {
        mViewModel.mHocPhi.observe(this, new Observer<HocPhi>() {
            @Override
            public void onChanged(HocPhi hocPhi) {
                mBinding.soTaiKhoan.setText(hocPhi.getSoTaiKhoan());
                mBinding.lastUpdate.setText(hocPhi.getLastUpdate());

                mBinding.layoutSTK.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.zoom_out));

                mBinding.tongSoTinChi.setText(hocPhi.getTongSoTinChi());
                mBinding.tongSoTinChiHocPhi.setText(hocPhi.getTongSoTinChiHocPhi());
                mBinding.tongSoTienHocPhiHocKy.setText(hocPhi.getTongSoTienHocPhiHocKy());
                mBinding.soTienDongToiThieuLanDau.setText(hocPhi.getSoTienDongToiThieuLanDau());
                mBinding.soTienDaDongTrongHocKy.setText(hocPhi.getSoTienDaDongTrongHocKy());
                mBinding.soTienConNo.setText(hocPhi.getSoTienConNo());

                showListMonHoc(hocPhi);
            }
        });
    }

    private void showListMonHoc(HocPhi hocPhi) {
        mBinding.container.removeAllViews();
        ArrayList<View> listView = new ArrayList<>();
        for (MonHoc monHoc : hocPhi.getListMonHoc()) {
            LinearLayout row = new LinearLayout(this);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rowParams.setMargins(0, 0, 0, 40);
            row.setLayoutParams(rowParams);
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView tenMonHoc = new TextView(this);
            LinearLayout.LayoutParams paramsTenMonHoc = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsTenMonHoc.weight = 4.0f;
            tenMonHoc.setLayoutParams(paramsTenMonHoc);
            tenMonHoc.setText(monHoc.getTenMon());
            tenMonHoc.setTextColor(Color.BLACK);
            row.addView(tenMonHoc);

            TextView soTinChi = new TextView(this);
            LinearLayout.LayoutParams paramsSoTC = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsSoTC.weight = 1.0f;
            soTinChi.setLayoutParams(paramsSoTC);
            soTinChi.setText(monHoc.getSoTinChi());
            soTinChi.setTextColor(Color.BLACK);
            soTinChi.setGravity(Gravity.CENTER);
            row.addView(soTinChi);

            TextView phaiDong = new TextView(this);
            LinearLayout.LayoutParams paramsPhaiDong = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsPhaiDong.weight = 2.0f;
            phaiDong.setLayoutParams(paramsPhaiDong);
            phaiDong.setText(monHoc.getHocPhi());
            phaiDong.setTextColor(getResources().getColor(R.color.colorPrimary));
            phaiDong.setTypeface(Typeface.DEFAULT_BOLD);
            phaiDong.setGravity(Gravity.END);
            row.addView(phaiDong);
            row.setVisibility(View.INVISIBLE);

            listView.add(row);
            mBinding.container.addView(row);
        }

        for(int i=0; i<listView.size(); i++){
            final View view = listView.get(i);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setVisibility(View.VISIBLE);
                    view.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_up));
                }
            }, 300*i);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEventDocumentDownloaded(Downloader downloader) {
        if (downloader.getTag().equals(HocPhiViewModel.GetTag)) {
            mViewModel.getHocPhi(downloader);
        } else if (downloader.getTag().equals(HocPhiViewModel.LoginTag)) {
            mViewModel.loginHocPhi(downloader);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        if (isFinishing()) {
            overridePendingTransition(R.anim.none, R.anim.slide_right);
        }
    }

    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }
}
