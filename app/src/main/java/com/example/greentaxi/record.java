package com.example.greentaxi;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;


public class record extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);







    }
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_record:
                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivity(intent);
                break;
        }



        switch (view.getId()) {
            case R.id.bt_list:
                Intent play = new Intent(this, com.example.greentaxi.recordstart.class);
                startActivity(play);
                break;
        }
    }
}


