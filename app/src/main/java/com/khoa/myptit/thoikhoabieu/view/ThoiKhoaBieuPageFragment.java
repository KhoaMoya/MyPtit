package com.khoa.myptit.thoikhoabieu.view;

/*
 * Created at 10/3/19 9:37 AM by Khoa
 */

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.khoa.myptit.R;
import com.khoa.myptit.databinding.FragmentViewpageTkbBinding;
import com.khoa.myptit.thoikhoabieu.model.MonHoc;
import com.khoa.myptit.thoikhoabieu.model.Tuan;

public class ThoiKhoaBieuPageFragment extends Fragment {

    private static Tuan mTuan;
    private FragmentViewpageTkbBinding mBinding;

    public ThoiKhoaBieuPageFragment(Tuan tuan) {
        this.mTuan = tuan;
    }

    public ThoiKhoaBieuPageFragment() {
    }

        @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_viewpage_tkb, container, false);
        showTable(mTuan.getMonHocs());
        return mBinding.getRoot();
    }

    private void showTable(MonHoc[][] monHocs) {

        // tiet 1
        for (int i = 2; i <= 7; i++) {
            String content = "";
            MonHoc monHoc = monHocs[1][i];
            if (monHoc != null) content = htmlContent(monHoc);
            mBinding.tiet1.addView(createItem(content, 1, i));
        }

        // tiet 2
        for (int i = 2; i <= 7; i++) {
            String content = "";
            MonHoc monHoc = monHocs[2][i];
            if (monHoc != null) content = htmlContent(monHoc);
            mBinding.tiet2.addView(createItem(content, 2, i));
        }

        // tiet 3
        for (int i = 2; i <= 7; i++) {
            String content = "";
            MonHoc monHoc = monHocs[3][i];
            if (monHoc != null) content = htmlContent(monHoc);
            mBinding.tiet3.addView(createItem(content, 3, i));
        }

        // tiet 4
        for (int i = 2; i <= 7; i++) {
            String content = "";
            MonHoc monHoc = monHocs[4][i];
            if (monHoc != null) content = htmlContent(monHoc);
            mBinding.tiet4.addView(createItem(content, 4, i));
        }

        // tiet 5
        for (int i = 2; i <= 7; i++) {
            String content = "";
            MonHoc monHoc = monHocs[5][i];
            if (monHoc != null) content = htmlContent(monHoc);
            mBinding.tiet5.addView(createItem(content, 5, i));
        }

        // tiet 6
        for (int i = 2; i <= 7; i++) {
            String content = "";
            MonHoc monHoc = monHocs[6][i];
            if (monHoc != null) content = htmlContent(monHoc);
            mBinding.tiet6.addView(createItem(content, 6, i));
        }

        // tiet 7
        for (int i = 2; i <= 7; i++) {
            String content = "";
            MonHoc monHoc = monHocs[7][i];
            if (monHoc != null) content = htmlContent(monHoc);
            mBinding.tiet7.addView(createItem(content, 7, i));
        }

        // tiet 8
        for (int i = 2; i <= 7; i++) {
            String content = "";
            MonHoc monHoc = monHocs[8][i];
            if (monHoc != null) content = htmlContent(monHoc);
            mBinding.tiet8.addView(createItem(content, 8, i));
        }

        // tiet 9
        for (int i = 2; i <= 7; i++) {
            String content = "";
            MonHoc monHoc = monHocs[9][i];
            if (monHoc != null) content = htmlContent(monHoc);
            mBinding.tiet9.addView(createItem(content, 9, i));
        }

        // tiet 10
        for (int i = 2; i <= 7; i++) {
            String content = "";
            MonHoc monHoc = monHocs[10][i];
            if (monHoc != null) content = htmlContent(monHoc);
            mBinding.tiet10.addView(createItem(content, 10, i));
        }

        // tiet 11
        for (int i = 2; i <= 7; i++) {
            String content = "";
            MonHoc monHoc = monHocs[11][i];
            if (monHoc != null) content = htmlContent(monHoc);
            mBinding.tiet11.addView(createItem(content, 11, i));
        }

        // tiet 12
        for (int i = 2; i <= 7; i++) {
            String content = "";
            MonHoc monHoc = monHocs[12][i];
            if (monHoc != null) content = htmlContent(monHoc);
            mBinding.tiet12.addView(createItem(content, 12, i));
        }
    }

    private TextView createItem(String text, final int tiet, final int thu) {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 2.0f;
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(3, 0, 3, 0);
        textView.setBackground(getContext().getDrawable(R.drawable.background_stroke));

        textView.setText(android.text.Html.fromHtml(text));
        textView.setTextSize(12);
        textView.setTextColor(getContext().getResources().getColor(R.color.colorBlack));

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonHoc[][] monHocs = mTuan.getMonHocs();
                new ChiTietMonHocDialogFragment(getContext(), monHocs[tiet][thu]).show(getFragmentManager(), "ChiTietMonHocDialogFragment");
            }
        });
        return textView;
    }

    private String htmlContent(MonHoc monHoc) {
        String content = "<font color=\"black\">" + monHoc.getTenMon() + "</font><br><br>";
        if (!monHoc.getPhongHoc().isEmpty())
            content += "<font color=\"#03A9F4\">" + monHoc.getPhongHoc() + "</font>";
        return content;
    }
}
