package com.khoa.myptit.thoikhoabieu.view;

/*
 * Created at 10/3/19 9:49 PM by Khoa
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.khoa.myptit.R;
import com.khoa.myptit.thoikhoabieu.model.MonHoc;

public class ChiTietMonHocDialog {

    private Context mContext;
    private Dialog mDialog;
    private TextView txtTenMon;
    private TextView txtNhom;
    private TextView txtLop;
    private TextView txtPhong;
    private TextView txtGiangVien;
    private TextView txtTinChi;
    private TextView txtNothing;
    private Button btnGhiChu;



    public ChiTietMonHocDialog(Context context) {
        mContext = context;
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.dialog_monhoc);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.getWindow().getAttributes().windowAnimations = R.style.MyDialogMonHoc;
        binding();
    }

    public void show(MonHoc monHoc){
        mDialog.show();
        if(monHoc!=null){
            show();
            txtTenMon.setText(monHoc.getTenMon());
            txtNhom.setText("Nhóm: " + monHoc.getMaMon());
            txtLop.setText("Lớp: " + monHoc.getMaLop());
            txtPhong.setText("Phòng: " + monHoc.getPhongHoc());
            txtGiangVien.setText("Giảng viên: " + monHoc.getGiangVien());
            txtTinChi.setText("Tín chỉ: " + monHoc.getSoTinChi());
        } else {
            hide();
        }
    }

    private void binding(){
        txtTenMon = mDialog.findViewById(R.id.tenMon);
        txtNhom = mDialog.findViewById(R.id.nhom);
        txtLop = mDialog.findViewById(R.id.lop);
        txtPhong = mDialog.findViewById(R.id.phong);
        txtGiangVien = mDialog.findViewById(R.id.giangvien);
        txtTinChi = mDialog.findViewById(R.id.tinchi);
        txtNothing = mDialog.findViewById(R.id.nothing);
        btnGhiChu = mDialog.findViewById(R.id.ghichu);
    }

    private void show(){
        txtTenMon.setVisibility(View.VISIBLE);
        txtNhom.setVisibility(View.VISIBLE);
        txtLop.setVisibility(View.VISIBLE);
        txtPhong.setVisibility(View.VISIBLE);
        txtGiangVien.setVisibility(View.VISIBLE);
        txtTinChi.setVisibility(View.VISIBLE);
        txtNothing.setVisibility(View.GONE);
    }

    private void hide(){
        txtTenMon.setVisibility(View.GONE);
        txtNhom.setVisibility(View.GONE);
        txtLop.setVisibility(View.GONE);
        txtPhong.setVisibility(View.GONE);
        txtGiangVien.setVisibility(View.GONE);
        txtTinChi.setVisibility(View.GONE);
        txtNothing.setVisibility(View.VISIBLE);
    }

}
