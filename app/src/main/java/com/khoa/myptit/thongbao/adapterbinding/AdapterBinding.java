package com.khoa.myptit.thongbao.adapterbinding;

/*
 * Created at 9/26/19 8:09 PM by Khoa
 */

import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khoa.myptit.thongbao.adapter.ThongBaoRecycleViewAdapter;
import com.khoa.myptit.thongbao.viewmodel.ThongBaoViewModel;

public class AdapterBinding {

    @BindingAdapter("setAdapter")
    public static void setAdapter(RecyclerView recyclerView, ThongBaoRecycleViewAdapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"setPosition", "setViewModel"})
    public static void setContent(TextView textView, int position, ThongBaoViewModel viewModel){
        textView.setText(viewModel.getListThongBao().get(position).getLink());
    }

}
