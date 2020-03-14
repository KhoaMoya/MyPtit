package com.khoa.myptit.thoikhoabieu.view;

/*
 * Created at 10/4/19 8:46 AM by Khoa
 */

import android.content.Context;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.khoa.myptit.R;
import com.khoa.myptit.base.dialog.MyDialog;
import com.khoa.myptit.thoikhoabieu.model.Week;

import java.util.ArrayList;

public class SelectWeekDialog extends MyDialog {

    private ArrayList<Week> mListWeek;
    private RadioGroup mRadioGroup;
    private int mCurrentIndex;

    public SelectWeekDialog(Context context, int currentIndex, ArrayList<Week> listWeek) {
        super(context);
        this.mCurrentIndex = currentIndex;
        this.mListWeek = listWeek;

        mDialog.setContentView(R.layout.dialog_select_tuan);
        mRadioGroup = mDialog.findViewById(R.id.group);
    }


    public void show(final OnCheckWeekRadioButtonListener listener) {
        for (int i = 0; i < mListWeek.size(); i++) {
            Week week = mListWeek.get(i);
            mRadioGroup.addView(createItem(week.getTenTuan() + " : " + week.getNgayBatDau() + " - " + week.getNgayKetThuc()), i);
        }
        RadioButton radioButton = (RadioButton) mRadioGroup.getChildAt(mCurrentIndex);
        radioButton.setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton checkedRB = radioGroup.findViewById(id);
                int index = radioGroup.indexOfChild(checkedRB);
                listener.onWeekSelected(index);
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private RadioButton createItem(String title) {
        RadioButton radioButton = new RadioButton(mContext);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 0, 0);
        radioButton.setLayoutParams(params);
        radioButton.setText(title);
        radioButton.setBackground(mContext.getResources().getDrawable(R.drawable.background_clickable));
        return radioButton;
    }

    public interface OnCheckWeekRadioButtonListener{
        void onWeekSelected(int index);
    }
}
