package com.khoa.myptit.thongbao.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import com.khoa.myptit.R;
import com.khoa.myptit.base.model.EventMessager;
import com.khoa.myptit.databinding.ActivityChiTietThongBaoBinding;
import com.khoa.myptit.thongbao.model.ThongBao;
import com.khoa.myptit.thongbao.net.ContentThongBaoDownloader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/*
 * Created at 9/28/19 8:53 AM by Khoa
 */

public class ChiTietThongBaoActivity extends AppCompatActivity {

    private ActivityChiTietThongBaoBinding mBinding;
    private MutableLiveData<String> content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityChiTietThongBaoBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        ThongBao thongBao = (ThongBao) getIntent().getSerializableExtra(ThongBaoFragment.KEY_ITEM);

        mBinding.txtTitle.setText(thongBao.getTitle());
        mBinding.txtTime.setText(thongBao.getTime());

        content = new MutableLiveData<>();
        content.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.txtContent.setText(s);
                mBinding.progress.setVisibility(View.GONE);
                mBinding.txtContent.setVisibility(View.VISIBLE);
            }
        });

        loadContent(thongBao.getLink());
    }

    public void loadContent(String link){
        mBinding.progress.setVisibility(View.VISIBLE);
        mBinding.txtContent.setVisibility(View.GONE);
        new ContentThongBaoDownloader(link).start();
    }

    @Subscribe
    public void onEventMessage(EventMessager eventMessager){
        if(eventMessager.getEvent() == EventMessager.EVENT.DOWNLOAD_FINNISH_DETAIL_NOTIFICATION) {
            content.postValue((String) eventMessager.getData());
        }
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
