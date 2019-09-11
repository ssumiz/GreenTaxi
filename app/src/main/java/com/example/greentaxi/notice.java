package com.example.greentaxi;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class notice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);
    }
    public void onClick(View view){

        switch (view.getId()){
            case R.id.notices:
                Intent notices = new Intent(this , route_search.class );
                break;

            case R.id.update:
                Intent update = new Intent(this , set.class);
                break;

            case R.id.notice_back:
                Intent back = new Intent(this , serviceCenter.class);
                startActivity(back);
                break;
        }

    }
}