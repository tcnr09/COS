package tw.tcnr.cos;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import tw.tcnr.cos.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class pointRecord_Fragment extends Fragment implements View.OnClickListener {


    private TextView startTime, endTime;
    private int year, month, day;
    private Calendar c = null;

    public pointRecord_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pointrecord, container, false);
        startTime = (TextView) view.findViewById(R.id.startdate);
        endTime = (TextView) view.findViewById(R.id.enddate);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        Calendar now = Calendar.getInstance();
        switch (v.getId()) {
            case R.id.startdate:
                onCreateDialog(startTime).show();
                break;
            case R.id.enddate:
                onCreateDialog(endTime).show();
                break;


        }
    }
    protected Dialog onCreateDialog(final TextView TextView) {
        Dialog dialog = null;
        c = Calendar.getInstance();
        dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                TextView.setText(year + "年" + (month + 1) + "月" + dayOfMonth +"日");
            }
        }, c.get(Calendar.YEAR), // 傳入年份
                c.get(Calendar.MONTH), // 傳入月份
                c.get(Calendar.DAY_OF_MONTH) // 傳入天数
       );

        return dialog;
    }


}

