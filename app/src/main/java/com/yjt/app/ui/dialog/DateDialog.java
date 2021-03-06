package com.yjt.app.ui.dialog;

import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;

import com.yjt.app.R;
import com.yjt.app.constant.Temp;
import com.yjt.app.ui.base.BaseDialogFragment;
import com.yjt.app.ui.dialog.builder.DateDialogBuilder;
import com.yjt.app.ui.listener.dialog.OnDateDialogListener;
import com.yjt.app.utils.BundleUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateDialog extends BaseDialogFragment {

    private DatePicker dpDate;
    private Calendar mCalendar;

    @Override
    protected Builder build(Builder builder) {
        CharSequence title = BundleUtil.getInstance().getCharSequenceData(getArguments(), Temp.DIALOG_TITLE.getContent());
        CharSequence positive = BundleUtil.getInstance().getCharSequenceData(getArguments(), Temp.DIALOG_BUTTON_POSITIVE.getContent());
        CharSequence negative = BundleUtil.getInstance().getCharSequenceData(getArguments(), Temp.DIALOG_BUTTON_NEGATIVE.getContent());
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(positive)) {
            builder.setPositiveButton(positive, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (OnDateDialogListener listener : getDialogListeners(OnDateDialogListener.class)) {
                        listener.onPositiveButtonClicked(mRequestCode, getDate());
                    }
                    dismiss();
                }
            });
        }
        if (!TextUtils.isEmpty(negative)) {
            builder.setNegativeButton(negative, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (OnDateDialogListener listener : getDialogListeners(OnDateDialogListener.class)) {
                        listener.onNegativeButtonClicked(mRequestCode);
                    }
                    dismiss();
                }
            });
        }
        dpDate = (DatePicker) builder.getLayoutInflater().inflate(R.layout.view_date, null);
        builder.setView(dpDate);
        mCalendar = Calendar.getInstance(TimeZone.getTimeZone(BundleUtil.getInstance().getStringData(getArguments(), Temp.TIME_ZONE.getContent())));
        mCalendar.setTimeInMillis(BundleUtil.getInstance().getLongData(getArguments(), Temp.DATE.getContent(), System.currentTimeMillis()));
        dpDate.updateDate(mCalendar.get(Calendar.YEAR)
                , mCalendar.get(Calendar.MONTH)
                , mCalendar.get(Calendar.DAY_OF_MONTH));
        return builder;
    }

    private Date getDate() {
        mCalendar.set(Calendar.YEAR, dpDate.getYear());
        mCalendar.set(Calendar.MONTH, dpDate.getMonth());
        mCalendar.set(Calendar.DAY_OF_MONTH, dpDate.getDayOfMonth());
        return mCalendar.getTime();
    }

    public static DateDialogBuilder createBuilder(FragmentManager manager) {
        return new DateDialogBuilder(manager, DateDialog.class);
    }
}
