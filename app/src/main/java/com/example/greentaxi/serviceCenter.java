package com.example.greentaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;

public class serviceCenter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_center);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sC_back:
                Intent intent = new Intent(this, main_logined.class);
                startActivity(intent);
                break;
            case R.id.sC_notice:
                Intent intent2 = new Intent(this, notice.class);
                startActivity(intent2);
                break;
            case R.id.sC_qna:
                Intent intent3 = new Intent(this, question.class);
                startActivity(intent3);
                break;
        }
    }
}
