package com.hfad.appgodsproject.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.EditText;

import com.hfad.appgodsproject.R;

import java.util.Calendar;
import java.util.Date;

//Based on https://developer.android.com/guide/topics/ui/controls/pickers#java

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        DateFormat df = new DateFormat();
        Date d = new Date(year - 1900, month, day);
        EditText dt =  getActivity().findViewById(R.id.dateText);
        dt.setText(df.format(DATE_FORMAT, d));
    }
}
