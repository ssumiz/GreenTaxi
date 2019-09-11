package com.example.greentaxi;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class partner_register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partner_regiser);
    }

    public void onClick(View view){
     switch (view.getId()){
         case R.id.partner_ok:
            // DB 에 데이터 전송하는 코드 추후에 작성해야함
             Intent login = new Intent(this, main.class);
             startActivity(login);
             break;
             case R.id.partner_cancel:
            finish();

    }

}
}
