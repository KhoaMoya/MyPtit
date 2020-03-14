package com.khoa.myptit.thoikhoabieu.view;

/*
 * Created at 3/9/20 10:24 AM by Khoa
 */

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khoa.myptit.R;
import com.khoa.myptit.base.dialog.MyDialog;
import com.khoa.myptit.thoikhoabieu.model.Subject;

public class DetailSubjectDialog  extends MyDialog {

    public DetailSubjectDialog(@NonNull Context context) {
        super(context);
    }

    public void showDetailSubject(Subject subject){
        mDialog.setCancelable(true);
        mDialog.setContentView(R.layout.dialog_detail_subject);

        TextView txtSubjectName = mDialog.findViewById(R.id.txt_subject_name);
        TextView txtGroup = mDialog.findViewById(R.id.txt_group);
        TextView txtClass = mDialog.findViewById(R.id.txt_class);
        TextView txtRoom = mDialog.findViewById(R.id.txt_room);
        TextView txtTeacher = mDialog.findViewById(R.id.txt_teacher);
        TextView txtTinChi = mDialog.findViewById(R.id.txt_tinchi);

        txtSubjectName.setText(subject.subjectName);
        txtGroup.setText(subject.subjectCode);
        txtClass.setText(subject.classCode);
        txtRoom.setText(subject.roomName);
        txtTeacher.setText(subject.teacher);
        txtTinChi.setText(subject.soTinChi);

        mDialog.show();
    }

}
