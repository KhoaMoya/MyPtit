package com.khoa.myptit.xemhocphi.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.khoa.myptit.R;
import com.khoa.myptit.base.dialog.MyDialog;
import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.base.net.Downloader;
import com.khoa.myptit.databinding.ActivityXemHocPhiBinding;
import com.khoa.myptit.thoikhoabieu.model.Subject;
import com.khoa.myptit.xemhocphi.model.HocPhi;
import com.khoa.myptit.xemhocphi.viewmodel.HocPhiViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/*
 * Created at 10/20/19 7:22 PM by Khoa
 */

public class XemHocPhiActivity extends AppCompatActivity {

    private ActivityXemHocPhiBinding mBinding;
    private HocPhiViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityXemHocPhiBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewModel = new ViewModelProvider(this).get(HocPhiViewModel.class);
        if (savedInstanceState == null) mViewModel.init(this);

        setupBinding();

        setupHocPhiListener();

        mViewModel.loadHocPhi();

    }

    public void setupBinding() {
        mBinding.refreshHocPhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.scrollview.setVisibility(View.INVISIBLE);
                mBinding.progressLoading.setVisibility(View.VISIBLE);
                mViewModel.refreshHocPhi();
            }
        });

        mViewModel.loginError.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginError) {
                if(loginError){
                    mBinding.scrollview.setVisibility(View.VISIBLE);
                    mBinding.progressLoading.setVisibility(View.GONE);
                    new MyDialog(XemHocPhiActivity.this).showNotificationDialog("Đăng nhập thất bại", "Không thể đăng nhập vào QLDT. Vui lòng đăng nhập nhập lại");
                }
            }
        });

        mViewModel.mException.observe(this, new Observer<Exception>() {
            @Override
            public void onChanged(Exception exception) {
                new MyDialog(XemHocPhiActivity.this).showNotificationDialog("Exception", exception.getCause() + "\n" + exception.getMessage());
            }
        });

        mBinding.scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int maxDistance = mBinding.layoutSTK.getHeight();
                int movement = mBinding.scrollview.getScrollY();
                if (movement >= 0 && movement <= maxDistance) {
                    mBinding.layoutSTK.setTranslationY(-movement / 2);
                }
            }
        });
    }

    private void setupHocPhiListener() {
        mViewModel.mHocPhi.observe(this, new Observer<HocPhi>() {
            @Override
            public void onChanged(HocPhi hocPhi) {
                mBinding.scrollview.setVisibility(View.VISIBLE);
                mBinding.progressLoading.setVisibility(View.GONE);

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

    private void showListMonHoc(final HocPhi hocPhi) {
        mBinding.container.removeAllViews();
        final ArrayList<View> listRow = new ArrayList<>();
        for (Subject subject : hocPhi.getListMonHoc()) {
            View row = LayoutInflater.from(XemHocPhiActivity.this).inflate(R.layout.row_subject_tuition, mBinding.container, false);
            TextView txtSubjectName = row.findViewById(R.id.txt_subject_name);
            TextView txtSTC = row.findViewById(R.id.txt_stc);
            TextView txt_tuition = row.findViewById(R.id.txt_tuition);

            txtSubjectName.setText(subject.subjectName);
            txtSTC.setText(subject.soTinChi);
            txt_tuition.setText(subject.tuition);

            row.setVisibility(View.INVISIBLE);

            listRow.add(row);
            mBinding.container.addView(row);
        }

        for (int i = 0; i < listRow.size(); i++) {
            final View view = listRow.get(i);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setVisibility(View.VISIBLE);
                    view.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_down_fade_in));
                }
            }, 100 * i);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEventMessage(EventMessager eventMessager) {
        if (eventMessager.getEvent() == EventMessager.EVENT.DOWNLOAD_FINNISH) {
            if (eventMessager.getTag().equals(HocPhiViewModel.GetTag)) {
                mViewModel.getHocPhi((Downloader) eventMessager.getData());
            }
        } else if (eventMessager.getEvent() == EventMessager.EVENT.LOGIN_FINNISH) {
            if (eventMessager.getTag().equals(HocPhiViewModel.LoginTag)) {
                mViewModel.loginHocPhi((Downloader) eventMessager.getData());
            }
        } else if(eventMessager.getEvent() == EventMessager.EVENT.EXCEPTION){
            String tag = eventMessager.getTag();
            Exception exception = (Exception) eventMessager.getData();
            mViewModel.mException.postValue(exception);
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
