package com.khoa.myptit.xemdiem.view;

/*
 * Created at 10/27/19 10:51 AM by Khoa
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khoa.myptit.R;
import com.khoa.myptit.xemdiem.model.DiemMonHoc;

import java.util.ArrayList;

public class ListMonHocDialog extends Dialog {

    private TextView txtTitle;
    private LinearLayout layoutContain;

    public ListMonHocDialog(@NonNull Context context, ArrayList<DiemMonHoc> list, String title, int color) {
        super(context);
        setContentView(R.layout.fragment_list_mon_hoc);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.MyDialogMonHoc;

        txtTitle = findViewById(R.id.title);
        layoutContain = findViewById(R.id.layout_contain);

        bindData(list, title, color);
    }

    public void bindData(ArrayList<DiemMonHoc> list, String title, int color){
        txtTitle.setText(title);
        txtTitle.setBackgroundColor(color);

        ArrayList<View> listView = new ArrayList<>();
        for (DiemMonHoc monHoc : list) {
            LinearLayout row = new LinearLayout(getContext());
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rowParams.setMargins(0, 0, 0, 40);
            row.setLayoutParams(rowParams);
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView tenMonHoc = new TextView(getContext());
            LinearLayout.LayoutParams paramsTenMonHoc = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsTenMonHoc.weight = 5.0f;
            tenMonHoc.setLayoutParams(paramsTenMonHoc);
            tenMonHoc.setText(monHoc.getTenMonHoc());
            tenMonHoc.setTextColor(Color.BLACK);
            tenMonHoc.setPadding(40,0,0,0);
            row.addView(tenMonHoc);

            TextView soTinChi = new TextView(getContext());
            LinearLayout.LayoutParams paramsSoTC = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsSoTC.weight = 1.0f;
            soTinChi.setLayoutParams(paramsSoTC);
            soTinChi.setText(monHoc.getSoTinChi());
            soTinChi.setTextColor(Color.BLACK);
            soTinChi.setGravity(Gravity.CENTER);
            row.addView(soTinChi);

            TextView thi = new TextView(getContext());
            LinearLayout.LayoutParams paramsThi = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsThi.weight = 1.0f;
            thi.setLayoutParams(paramsSoTC);
            thi.setText(monHoc.getThi());
            thi.setTextColor(Color.BLACK);
            thi.setGravity(Gravity.CENTER);
            row.addView(thi);

            TextView tongKet = new TextView(getContext());
            LinearLayout.LayoutParams paramsPhaiDong = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsPhaiDong.weight = 1.0f;
            tongKet.setLayoutParams(paramsPhaiDong);
            tongKet.setText(monHoc.getTK4());
            tongKet.setTextColor(getContext().getResources().getColor(R.color.colorAccentDark));
            tongKet.setTypeface(Typeface.DEFAULT_BOLD);
            tongKet.setGravity(Gravity.CENTER);
            row.addView(tongKet);

//            row.setVisibility(View.INVISIBLE);

            listView.add(row);
            layoutContain.addView(row);
        }

//        for(int i=0; i<listView.size(); i++){
//            final View view = listView.get(i);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    view.setVisibility(View.VISIBLE);
//                    view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_up));
//                }
//            }, 300*i);
//        }
    }
}
