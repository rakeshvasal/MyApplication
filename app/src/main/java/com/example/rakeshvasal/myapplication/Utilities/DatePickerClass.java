package com.example.rakeshvasal.myapplication.Utilities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Axisvation on 11/28/2017.
 */

public class DatePickerClass {

    static int mYear, mMonth, mDay;
    static String strdayOfMonth, strmonthOfYear;

    public static void getSetDate(Activity activity, final TextView setTextView, String title) {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        view.updateDate(year, monthOfYear, dayOfMonth);

                        int dayOfMonthlength = (int) Math.log10(dayOfMonth) + 1;
                        if (dayOfMonthlength == 1) {
                            strdayOfMonth = "0" + dayOfMonth;
                        } else {
                            strdayOfMonth = "" + dayOfMonth;
                        }
                        int monthOfYearlength = (int) Math.log10(monthOfYear + 1) + 1;
                        if (monthOfYearlength == 1) {
                            strmonthOfYear = "0" + (monthOfYear + 1);
                        } else {
                            strmonthOfYear = "" + (monthOfYear + 1);
                        }
                        setTextView.setText(strdayOfMonth + "/"
                                + strmonthOfYear + "/" + year);



                    }
                }, mYear, mMonth, mDay);
        dpd.setTitle(title);
        //dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dpd.show();

    }

}
