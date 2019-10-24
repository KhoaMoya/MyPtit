package com.khoa.myptit.xemdiem.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.khoa.myptit.R;
import com.khoa.myptit.databinding.ActivityXemDiemBinding;
import com.khoa.myptit.login.net.Downloader;
import com.khoa.myptit.xemdiem.model.DiemHocKy;
import com.khoa.myptit.xemdiem.model.DiemMonHoc;
import com.khoa.myptit.xemdiem.viewmodel.XemDiemViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/*
 * Created at 10/21/19 9:13 PM by Khoa
 */

public class XemDiemActivity extends AppCompatActivity {

    private XemDiemViewModel mViewModel;
    private ActivityXemDiemBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBinding(savedInstanceState);

        setupPieChart();

        setupLineChart();

        setupDiemListener();

        mViewModel.loadAllDiem();
    }

    public void setupBinding(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_xem_diem);
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewModel = ViewModelProviders.of(this).get(XemDiemViewModel.class);
        if (savedInstanceState == null) mViewModel.init(this);
        mBinding.setViewmodel(mViewModel);
        mBinding.refreshDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.refreshDiem();
            }
        });

    }

    public void setupDiemListener() {
        mViewModel.mLisDiemHocKy.observe(this, new Observer<ArrayList<DiemHocKy>>() {
            @Override
            public void onChanged(ArrayList<DiemHocKy> listDiemHocKy) {
                if (!listDiemHocKy.equals(new ArrayList<DiemHocKy>())) {
                    Log.e("Loi", "update");
                    showPieChart(listDiemHocKy);
                    showLineChart(listDiemHocKy);
                }
            }
        });
    }

    public void setupPieChart() {
        mBinding.pieChart.setTransparentCircleAlpha(0);
        mBinding.pieChart.getDescription().setEnabled(false);
        mBinding.pieChart.setDrawHoleEnabled(true);
        mBinding.pieChart.setHoleRadius(40f);
        mBinding.pieChart.setHoleColor(Color.WHITE);
        mBinding.pieChart.setUsePercentValues(false);
        mBinding.pieChart.setDrawEntryLabels(true);
        mBinding.pieChart.getLegend().setEnabled(false);


        mBinding.pieChart.setDrawCenterText(true);
        mBinding.pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
        mBinding.pieChart.setCenterTextColor(Color.BLACK);
        mBinding.pieChart.setCenterTextSize(20);
        mBinding.pieChart.setEntryLabelColor(Color.WHITE);
        mBinding.pieChart.setEntryLabelTextSize(13f);
    }

    private void showPieChart(ArrayList<DiemHocKy> listDiemHocKi) {

        // tong hop diem cac mon
        int soMonDaHoc = 0;
        int soTinChiNo = 0;
        Map<String, Integer> map = new HashMap<>();
        for (DiemHocKy diemHocKy : listDiemHocKi) {
            for (DiemMonHoc diemMonHoc : diemHocKy.getListMonHoc()) {
                String diemChu = diemMonHoc.getTK4();
                if (diemChu.isEmpty()) continue;
                soMonDaHoc++;
                if (diemChu.equals("F")) soTinChiNo += Integer.valueOf(diemMonHoc.getSoTinChi());
                if (map.containsKey(diemChu)) {
                    int dem = map.get(diemChu);
                    map.remove(diemChu);
                    map.put(diemChu, dem + 1);
                } else {
                    map.put(diemChu, 1);
                }
            }
        }

        // sap xep diem theo thu tu ABC
        map = new TreeMap<>(map);

        // tạo list entry từ map
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (String key : map.keySet()) {
            entries.add(new PieEntry(map.get(key), key));
        }

        // tạo piedataset từ list entry
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawValues(true);
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        dataSet.setColors(colors);


        // tạo piedata từ piedataset
        PieData data = new PieData(dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(Typeface.DEFAULT_BOLD);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });
        mBinding.pieChart.setData(data);
        mBinding.pieChart.animateXY(1500, 1500);
        mBinding.pieChart.invalidate();

        if (listDiemHocKi.size() > 1) {
            DiemHocKy lastHocKy = listDiemHocKi.get(listDiemHocKi.size() - 2);
            mBinding.pieChart.setCenterText(lastHocKy.getDiemTBTichLuy4());

            ArrayList<String> values = new ArrayList<>();
            values.add(soMonDaHoc + "");
            values.add(lastHocKy.getSoTinChiTichLuy().isEmpty() ? "0" : lastHocKy.getSoTinChiTichLuy());
            values.add(lastHocKy.getDiemTBTichLuy10().isEmpty() ? "0" : lastHocKy.getDiemTBTichLuy10());
            values.add(lastHocKy.getDiemTBTichLuy4().isEmpty() ? "0" : lastHocKy.getDiemTBTichLuy4());
            values.add(soTinChiNo + "");

            showLayoutInfo(values);
        }

        if(map.get("F")!=null && soMonDaHoc>0) {
            float soMonNo = map.get("F");
            float tiLeQua = (1 - soMonNo / soMonDaHoc) * 100;
            mBinding.tiLeQua.setText( String.format("%.1f", tiLeQua)+ "%");
        } else {
            mBinding.tiLeQua.setText("0%");
        }
    }

    private void showLayoutInfo(ArrayList<String> values) {
        mBinding.containerInfo.removeAllViews();

        ArrayList<String> labels = new ArrayList<>();
        labels.add("Số môn đã học");
        labels.add("Số tín chỉ tích lũy");
        labels.add("Điểm TBTL hệ 10");
        labels.add("Điểm TBTL hệ 4");
        labels.add("Số tín chỉ nợ");

        ArrayList<View> listView = new ArrayList<>();
        for (int i = 0; i < labels.size(); i++) {

            LinearLayout row = new LinearLayout(this);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rowParams.setMargins(0, 0, 0, 20);
            row.setLayoutParams(rowParams);
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtLabel = new TextView(this);
            LinearLayout.LayoutParams paramsTenMonHoc = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsTenMonHoc.weight = 4.0f;
            txtLabel.setLayoutParams(paramsTenMonHoc);
            txtLabel.setText(labels.get(i));
            txtLabel.setTextColor(Color.BLACK);
            txtLabel.setTextSize(13f);
            row.addView(txtLabel);

            TextView txtValue = new TextView(this);
            LinearLayout.LayoutParams paramsSoTC = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsSoTC.weight = 1.0f;
            txtValue.setLayoutParams(paramsSoTC);
            txtValue.setText(values.get(i));
            txtValue.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtValue.setGravity(Gravity.END);
            txtValue.setTypeface(Typeface.DEFAULT_BOLD);
            row.addView(txtValue);

            row.setVisibility(View.INVISIBLE);

            listView.add(row);
            mBinding.containerInfo.addView(row);

        }

        for (int i = 0; i < listView.size(); i++) {
            final View view = listView.get(i);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setVisibility(View.VISIBLE);
                    view.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_from_end_to_start));
                }
            }, 300 * i);
        }
    }

    private void setupLineChart() {
        mBinding.lineChart.setViewPortOffsets(60, 0, 60, 50);
        mBinding.lineChart.setBackgroundColor(Color.TRANSPARENT);
        mBinding.lineChart.getDescription().setEnabled(false);

        mBinding.lineChart.setTouchEnabled(false);
        mBinding.lineChart.setDrawGridBackground(false);
        mBinding.lineChart.setMaxHighlightDistance(300);

        XAxis x = mBinding.lineChart.getXAxis();
        x.setTextColor(Color.BLACK);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setAxisLineColor(Color.BLACK);

        mBinding.lineChart.getAxisLeft().setEnabled(false);
        mBinding.lineChart.getAxisRight().setEnabled(false);
        mBinding.lineChart.getLegend().setEnabled(false);

        mBinding.lineChart.animateX(1);

        mBinding.lineChart.setScaleYEnabled(false);
//        mBinding.lineChart.invalidate();
    }

    private void showLineChart(ArrayList<DiemHocKy> listDiemHocKy) {

        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < listDiemHocKy.size(); i++) {
            if (listDiemHocKy.get(i).getDiemTB4() != null) {
                entries.add(new Entry(i, Float.valueOf(listDiemHocKy.get(i).getDiemTB4())));
            }
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "Điểm trung bình hệ 4");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.2f);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setColor(getResources().getColor(R.color.colorPrimary));
        lineDataSet.setDrawValues(true);

        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(getResources().getColor(R.color.colorPrimary));
        lineDataSet.setFillAlpha(10);
        lineDataSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return mBinding.lineChart.getAxisLeft().getAxisMinimum();
            }
        });

        lineDataSet.setDrawCircles(true);
        lineDataSet.setCircleRadius(4f);
        lineDataSet.setCircleColor(getResources().getColor(R.color.colorPrimary));

        LineData data = new LineData(lineDataSet);
        data.setValueTextSize(9f);
        data.setDrawValues(true);

        // set label cho truc X
        final ArrayList<String> labels = new ArrayList<>();
        for(DiemHocKy diemHocKy : listDiemHocKy){
            String string = diemHocKy.getTenHocKy();
            // Học kỳ 3 - Năm học 2018-2019
            String label = string.charAt(7)+ "(" + string.charAt(21)+ string.charAt(22) + "-" + string.charAt(26)+ string.charAt(27)+")";
            Log.e("Loi", label);
            labels.add(label);
        }

        mBinding.lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return labels.get(Math.round(value));
            }
        });

        // set data
        mBinding.lineChart.setData(data);
        mBinding.lineChart.invalidate();
    }

    public float chuyenDiemTuChuSangSo(String chu) {
        switch (chu) {
            case "A+":
                return 4f;
            case "A":
                return 3.7f;
            case "B+":
                return 3.5f;
            case "B":
                return 3f;
            case "C+":
                return 2.5f;
            case "C":
                return 2f;
            case "D+":
                return 1.5f;
            case "D":
                return 1f;
            case "F":
                return 0f;
            default:
                return 0f;
        }
    }

    @Subscribe
    public void onEventDocumentDownloaded(Downloader downloader) {
        if (downloader.getTag().equals(XemDiemViewModel.GetTag)) {
            mViewModel.getXemDiem(downloader);
        } else if (downloader.getTag().equals(XemDiemViewModel.LoginTag)) {
            mViewModel.loginXemDiem(downloader);
        } else if (downloader.getTag().equals(XemDiemViewModel.PostTag)) {
            mViewModel.postXemDiem(downloader);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
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
