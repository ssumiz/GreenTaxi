package com.example.greentaxi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class partner extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partner);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.partner_back:
                Intent intent = new Intent(this, main_logined.class);
                startActivity(intent);
                break;
        }
    }
}