package com.khoa.myptit.thongbao.adapter;

/*
 * Created at 9/26/19 3:12 PM by Khoa
 */

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.khoa.myptit.BR;
import com.khoa.myptit.R;
import com.khoa.myptit.thongbao.viewmodel.ThongBaoViewModel;

public class ThongBaoRecycleViewAdapter extends RecyclerView.Adapter<ThongBaoRecycleViewAdapter.ThongBaoViewHolder> {

    private ThongBaoViewModel viewModel;

    public ThongBaoRecycleViewAdapter(ThongBaoViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_thongbao, parent, false);
        return new ThongBaoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongBaoViewHolder holder, int position) {
        holder.bind(viewModel, position);
    }

    @Override
    public int getItemCount() {
        return viewModel.getListThongBao().size();
    }

    class ThongBaoViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        ThongBaoViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.binding = viewDataBinding;
        }

        void bind(ThongBaoViewModel viewModel, Integer position) {
            binding.setVariable(BR.viewmodel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
