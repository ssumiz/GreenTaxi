package com.example.greentaxi;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class set extends AppCompatActivity {
    private Switch sw1, sw2, sw3;
    private Button btnGet;
    private TextView sound_but1;
    private TextView font_but1;
    ImageView imageView;
    AudioManager myAudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);
        // default sound mode
        myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        //font_but1 = findViewById(R.id.font_but1);
        sound_but1 = findViewById(R.id.sound_but1);

        sound_but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(sound_but1.getText());
                String getMode = sound_but1.getText().toString().trim();
                if(getMode.equals("소리")){
                    sound_but1.setText("진동");
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                } else if(getMode.equals("진동")){
                    sound_but1.setText("소리");
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                } else {

                }
            }
        });
/*
        font_but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getMode = font_but1.getText().toString().trim();
                if(getMode.equals("보통")){
                    font_but1.setText("크게");
                } else {
                    font_but1.setText("보통");
                }
            }
        });*/

    }

    public void onClick(View view) {

        imageView = findViewById(R.id.mypage);

        switch (view.getId()) {
            case R.id.route_search_back:
                Intent intent = new Intent(this, main_logined.class);
                startActivity(intent);
                break;
            case R.id.mypage:
                Intent mypage = new Intent(this, mypage.class);
                startActivity(mypage);
                break;
            case R.id.sos:
                Intent sos = new Intent(this, sos.class);
                startActivity(sos);
                break;

/*
            case R.id.Ahegao:
                Intent Ahegao = new Intent(this, partner.class);
                startActivity(Ahegao);
                break;*/
        }
    }
}
