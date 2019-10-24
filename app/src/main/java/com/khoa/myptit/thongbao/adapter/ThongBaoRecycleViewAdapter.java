package com.khoa.myptit.thongbao.adapter;

/*
 * Created at 9/26/19 3:12 PM by Khoa
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.khoa.myptit.R;
import com.khoa.myptit.databinding.ItemThongbaoBinding;
import com.khoa.myptit.thongbao.viewmodel.ThongBaoViewModel;

public class ThongBaoRecycleViewAdapter extends RecyclerView.Adapter<ThongBaoRecycleViewAdapter.ThongBaoViewHolder> {

    private ThongBaoViewModel mViewModel;
    private ItemThongbaoBinding mItemThongBaoBinding;
    private ItemClickListener mListener;


    public ThongBaoRecycleViewAdapter(ThongBaoViewModel viewModel) {
        this.mViewModel = viewModel;
        this.mListener = mViewModel.getFragment();
    }

    @NonNull
    @Override
    public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        mItemThongBaoBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_thongbao, parent, false);
        return new ThongBaoViewHolder(mItemThongBaoBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull final ThongBaoViewHolder holder, final int position) {
        holder.itemThongbaoBinding.setViewmodel(mViewModel);
        holder.itemThongbaoBinding.setPosition(position);

//        Pair<View, String> contain = Pair.create((View) holder.itemThongbaoBinding.cardview, "contain");
//        Pair<View, String> title = Pair.create((View) holder.itemThongbaoBinding.txtTitle, "title");
//        Pair<View, String> time = Pair.create((View) holder.itemThongbaoBinding.cardview, "time");
//        Pair<View, String> content = Pair.create((View) holder.itemThongbaoBinding.cardview, "content");
//        final ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mViewModel.getFragment().getActivity(), contain, title, time, content);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickItem(position);
            }
        });
        holder.itemThongbaoBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mViewModel.getListThongBao().size();
    }

    class ThongBaoViewHolder extends RecyclerView.ViewHolder {
        ItemThongbaoBinding itemThongbaoBinding;

        ThongBaoViewHolder(ItemThongbaoBinding binding) {
            super(binding.getRoot());
            this.itemThongbaoBinding = binding;
        }
    }
}
