package com.khoa.myptit.canhan.view;

/*
 * Created at 10/27/19 4:48 PM by Khoa
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khoa.myptit.R;
import com.khoa.myptit.canhan.viewmodel.CaNhanViewModel;
import com.khoa.myptit.login.LoginActivity;

public class LogoutDialog extends Dialog {

    public LogoutDialog(@NonNull final Activity activity, final CaNhanViewModel viewModel) {
        super(activity);
        setContentView(R.layout.dialog_logout);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogLogout;
        getWindow().setGravity(Gravity.BOTTOM|Gravity.CENTER);

        TextView txtLogout = findViewById(R.id.txt_logout);
        TextView txtCancel = findViewById(R.id.txt_cancel);

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteData();
                dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(activity, LoginActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                }, 300);

            }
        });
    }
}
