package com.example.greentaxi;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class question extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.qna_back:
                Intent back = new Intent(this, serviceCenter.class);
                startActivity(back);
                break;
        }

    }
}