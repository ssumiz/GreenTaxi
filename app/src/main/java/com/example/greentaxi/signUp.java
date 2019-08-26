package com.example.greentaxi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class signUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
    }
    public void onClick(View view){

        switch (view.getId()){
            case R.id.signUp_ok:
                // DB 에 데이터 전송하는 코드 추후에 작성해야함
                Intent intent = new Intent(this, partner_register.class);
                startActivity(intent);
                break;
            case R.id.signUp_cancel:
                finish();

       }

    }
}
