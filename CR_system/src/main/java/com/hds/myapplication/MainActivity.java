package com.hds.myapplication;


import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements OnClickListener,OnDateSetListener{

    private Button btn_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置下拉列表
        ArrayAdapter<String> numberadapter = new ArrayAdapter<>(this,R.layout.item_select,
                numberarray);
        numberadapter.setDropDownViewResource(R.layout.item_dropdown);
        Spinner sp_number = (Spinner)findViewById(R.id.sp_number);
        sp_number.setAdapter(numberadapter);
        sp_number.setSelection(0);
        sp_number.setOnItemSelectedListener(new MySelectListener(this,numberarray));

        ArrayAdapter<String> majoradapter = new ArrayAdapter<>(this,R.layout.item_select,
                majorarray);
        numberadapter.setDropDownViewResource(R.layout.item_dropdown);
        Spinner sp_major = (Spinner)findViewById(R.id.sp_major);
        sp_major.setAdapter(majoradapter);
        sp_major.setSelection(0);
        sp_major.setOnItemSelectedListener(new MySelectListener(this,majorarray));
        //设置下拉列表


        //设置日期
        btn_date =(Button)findViewById(R.id.btn_date);
        btn_date.setOnClickListener(this);
        //设置日期

        //管理员按钮
        findViewById(R.id.btn_manager).setOnClickListener(this);



    }

    public void onClick (View v){
        if(v.getId() == R.id.btn_date ){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this,this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();
        }else if(v.getId() == R.id.btn_manager){
            Intent intent = new Intent(this , ManagerActivity.class);
            startActivity(intent);
        }

    }

    public void onDateSet (DatePicker v,int year,int month,int day){
        String date = year + "年" + (month+1) + "月" + day + "日";
        btn_date.setText(date);

    }

    String[] numberarray = {"飞机编号","174","184","207","209","213","215"};

    String[] majorarray = {"选择专业","机械","军械","火控","电气","仪氧","无线电","雷达"};



    public class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int mMaxLength;
        private CharSequence mStr;

        public HideTextWatcher(EditText v) {
            super();
            mView = v;
            mMaxLength = ViewUtil.getMaxLength(v);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mStr = s;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mStr == null || mStr.length() == 0)
                return;
            if (mStr.length() == 11 && mMaxLength == 11) {
                ViewUtil.hideAllInputMethod(MainActivity.this);
            }
            if (mStr.length() == 6 && mMaxLength == 6) {
                ViewUtil.hideOneInputMethod(MainActivity.this, mView);
            }
        }
    }

}



