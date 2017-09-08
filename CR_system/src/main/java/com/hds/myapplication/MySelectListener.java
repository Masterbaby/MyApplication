package com.hds.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by apple on 17/9/3.
 */

class MySelectListener implements AdapterView.OnItemSelectedListener {

    Context context;
    String[] array;


    public MySelectListener(Context context,String[] array){
        this.context = context;
        this.array = array;
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2,long arg3){
        Toast.makeText(context,array[arg2],Toast.LENGTH_LONG).show();

    }
    public void onNothingSelected(AdapterView<?> arg0) {

    }

}
