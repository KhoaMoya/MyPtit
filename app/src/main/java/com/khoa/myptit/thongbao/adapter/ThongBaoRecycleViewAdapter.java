package com.khoa.myptit.thongbao.adapter;

/*
 * Created at 9/26/19 3:12 PM by Khoa
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khoa.myptit.databinding.ItemThongbaoBinding;
import com.khoa.myptit.thongbao.model.ThongBao;

import java.util.ArrayList;

public class ThongBaoRecycleViewAdapter extends RecyclerView.Adapter<ThongBaoRecycleViewAdapter.ThongBaoViewHolder> {

    private ItemClickListener mListener;
    private ArrayList<ThongBao> thongBaos;

    public ThongBaoRecycleViewAdapter() {
    }

    public void setThongBaos(ArrayList<ThongBao> thongBaos) {
        this.thongBaos = thongBaos;
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemThongbaoBinding mItemThongBaoBinding = ItemThongbaoBinding.inflate(layoutInflater, parent, false);
        return new ThongBaoViewHolder(mItemThongBaoBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull final ThongBaoViewHolder holder, final int position) {

//        Pair<View, String> contain = Pair.create((View) holder.itemThongbaoBinding.cardview, "contain");
//        Pair<View, String> title = Pair.create((View) holder.itemThongbaoBinding.txtTitle, "title");
//        Pair<View, String> time = Pair.create((View) holder.itemThongbaoBinding.cardview, "time");
//        Pair<View, String> content = Pair.create((View) holder.itemThongbaoBinding.cardview, "content");
//        final ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mViewModel.getFragment().getActivity(), contain, title, time, content);

        holder.itemThongbaoBinding.txtTitle.setText(thongBaos.get(holder.getAdapterPosition()).getTitle());
        holder.itemThongbaoBinding.txtTime.setText(thongBaos.get(holder.getAdapterPosition()).getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClickItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return thongBaos == null ? 0 : thongBaos.size();
    }

    static class ThongBaoViewHolder extends RecyclerView.ViewHolder {
        ItemThongbaoBinding itemThongbaoBinding;

        ThongBaoViewHolder(ItemThongbaoBinding binding) {
            super(binding.getRoot());
            this.itemThongbaoBinding = binding;
        }
    }
}
