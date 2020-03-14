package com.khoa.myptit.xemdiem.adapter;

/*
 * Created at 10/26/19 9:28 PM by Khoa
 */

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khoa.myptit.R;
import com.khoa.myptit.libmoduleExpandable.Adapter.ExpandableRecyclerAdapter;
import com.khoa.myptit.libmoduleExpandable.Model.ParentListItem;
import com.khoa.myptit.libmoduleExpandable.ViewHolder.ChildViewHolder;
import com.khoa.myptit.libmoduleExpandable.ViewHolder.ParentViewHolder;
import com.khoa.myptit.xemdiem.model.DiemHocKy;
import com.khoa.myptit.xemdiem.model.DiemMonHoc;

import java.util.List;

import info.androidhive.fontawesome.FontTextView;

public class RecycleViewAdapterDiemHocKy extends ExpandableRecyclerAdapter<RecycleViewAdapterDiemHocKy.HocKyViewHolder, RecycleViewAdapterDiemHocKy.MonHocViewHolder> {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;
    private LayoutInflater mLayoutInflater;

    public RecycleViewAdapterDiemHocKy(Context context, @NonNull List<? extends ParentListItem> hocKyList) {
        super(hocKyList);
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setParentListItem(List<? extends ParentListItem> hocKyList){
        notifyParentItemRangeRemoved(0, this.mParentItemList.size());
        this.mParentItemList = hocKyList;
        notifyParentItemRangeInserted(0, this.mParentItemList.size());
    }

    @Override
    public HocKyViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View view = mLayoutInflater.inflate(R.layout.item_parent_diem_hoc_ky, parentViewGroup, false);
        return new HocKyViewHolder(view);
    }

    @Override
    public MonHocViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View view = mLayoutInflater.inflate(R.layout.item_child_diem_mon_hoc, childViewGroup, false);
        return new MonHocViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(HocKyViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        parentViewHolder.bind((DiemHocKy) parentListItem);
    }

    @Override
    public void onBindChildViewHolder(MonHocViewHolder childViewHolder, int position, Object childListItem) {
        childViewHolder.bind((DiemMonHoc) childListItem);
    }

    public class HocKyViewHolder extends ParentViewHolder {

        public TextView txtTenHocKy;
        public FontTextView txtExpand;

        public HocKyViewHolder(View itemView) {
            super(itemView);
            txtTenHocKy = itemView.findViewById(R.id.txt_ten_hoc_ky);
            txtExpand = itemView.findViewById(R.id.txt_expand);
        }

        public void bind(DiemHocKy hocKy){
            txtTenHocKy.setText(hocKy.getTenHocKy());
        }

        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);
            if(expanded) txtExpand.setText(R.string.fa_caret_up_solid);
            else txtExpand.setText(R.string.fa_caret_down_solid);
        }

        @Override
        public void onExpansionToggled(boolean expanded) {
            super.onExpansionToggled(expanded);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                RotateAnimation rotateAnimation;
                if (expanded) { // rotate clockwise
                    rotateAnimation = new RotateAnimation(ROTATED_POSITION, INITIAL_POSITION,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                } else { // rotate counterclockwise
                    rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION, INITIAL_POSITION,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                }

                rotateAnimation.setDuration(200);
                rotateAnimation.setFillAfter(true);
                txtExpand.startAnimation(rotateAnimation);
            }
        }
    }


    public class MonHocViewHolder extends ChildViewHolder {

        public TextView txtTenMonHoc;
        public TextView txtSTC;
        public TextView txtThi;
        public TextView txtTongKet;

        public MonHocViewHolder(View itemView) {
            super(itemView);

            txtTenMonHoc = itemView.findViewById(R.id.txt_ten_mon_hoc);
            txtSTC = itemView.findViewById(R.id.txt_stc);
            txtThi = itemView.findViewById(R.id.txt_diem_thi10);
            txtTongKet = itemView.findViewById(R.id.txt_tong_ket4);
        }

        public void bind(DiemMonHoc monHoc){
            txtTenMonHoc.setText(monHoc.getTenMonHoc());
            txtSTC.setText(monHoc.getSoTinChi());
            txtThi.setText(monHoc.getThi());
            txtTongKet.setText(monHoc.getTK4());
        }
    }
}
