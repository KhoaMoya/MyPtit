package com.khoa.myptit.base.dialog;

/*
 * Created at 2/26/20 8:47 PM by Khoa
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khoa.myptit.R;

import java.util.Objects;

public class MyDialog {

    protected Dialog mDialog;
    protected Context mContext;
    public MyDialog(@NonNull Context context) {
        this.mContext = context;
        mDialog = new Dialog(context);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.getWindow().getAttributes().windowAnimations = R.style.MyDialog;
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void showNotificationDialog(String title, String content){
        mDialog.setCancelable(true);
        mDialog.setContentView(R.layout.dialog_notification);

        TextView txtTitle = mDialog.findViewById(R.id.txt_title);
        TextView txtContent = mDialog.findViewById(R.id.txt_content);

        txtTitle.setText(title);
        txtContent.setText(content);

//        mDialog.findViewById(R.id.txt_ok).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//            }
//        });

        mDialog.show();
    }
}
