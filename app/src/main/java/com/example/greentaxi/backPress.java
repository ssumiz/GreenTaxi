package com.example.greentaxi;

import android.app.Activity;


import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class backPress {



    private long backPressTime = 0;
    private Activity activity;
    private Toast toast;

    public backPress(Activity context) {
        this.activity = context;

    }


    public void onBackPressed(){

        if(System.currentTimeMillis()> backPressTime+2000){
            backPressTime = System.currentTimeMillis();
            backSpaceToast();
            return;
        }
        else if(System.currentTimeMillis()<=backPressTime+2000){
            activity.moveTaskToBack(true);
            activity.finishAffinity();
            ActivityCompat.finishAffinity(activity);

            toast.cancel();

        }

    }

    public void backSpaceToast(){
        toast = Toast.makeText(activity,"뒤로가기 버튼을 한번 더 누르시면 어플이 종료됩니다.",  Toast.LENGTH_SHORT);

        toast.show();
    }
}
