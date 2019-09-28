package com.khoa.myptit.thongbao.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.khoa.myptit.R;
import com.khoa.myptit.databinding.ActivityChiTietThongBaoBinding;
import com.khoa.myptit.login.model.User;
import com.khoa.myptit.login.net.DocumentGetter;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.thongbao.model.ThongBao;
import com.khoa.myptit.thongbao.util.ParseResponse;
import com.khoa.myptit.thongbao.viewmodel.ChiTietThongBaoViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/*
 * Created at 9/28/19 8:53 AM by Khoa
 */

public class ActivityChiTietThongBao extends AppCompatActivity {

    private ChiTietThongBaoViewModel mViewModel;
    private ActivityChiTietThongBaoBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBinding();

        setupListenerContentChanged();

        loadContent();
    }

    public void setupListenerContentChanged(){
        mViewModel.mContent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.txtContent.setText(s);
                mViewModel.showLoading.set(View.GONE);
                mViewModel.showContent.set(View.VISIBLE);
            }
        });
    }

    public void loadContent(){
        String link = mViewModel.getLink();
        new DocumentGetter(link, new BaseRepository<User>().read(this, User.mFileName)).start();
    }

    public void setupBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chi_tiet_thong_bao);
        mViewModel = ViewModelProviders.of(this).get(ChiTietThongBaoViewModel.class);
        ThongBao thongBao = (ThongBao) getIntent().getSerializableExtra(FragmentThongBao.KEY_ITEM);

        if(mViewModel.mThongBao==null) mViewModel.init(thongBao);

        mBinding.setViewmodel(mViewModel);
    }

    @Subscribe
    public void onEventDownloadDocumentDone(DocumentGetter documentGetter){
        mViewModel.updateContent(this, documentGetter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        if(isFinishing()){
            overridePendingTransition(R.anim.none,R.anim.slide_right);
        }
    }

    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }
}
