package com.example.rakeshvasal.myapplication.Utilities;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rakesh.vasal on 5/5/2016.
 */
public class DateFormattingClass {
    String date1,initDateFormat,endDateFormat;
    Context activity;

    public String formatDate (Context activity, String date, String initDateFormat, String endDateFormat) throws ParseException {
        this.activity=activity;
        this.date1=date;
        this.initDateFormat = initDateFormat;
        this.endDateFormat = endDateFormat;
        Date initDate = new SimpleDateFormat(initDateFormat).parse(date1);
        SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
        String finaldate = formatter.format(initDate);

        return finaldate;
    }



}
