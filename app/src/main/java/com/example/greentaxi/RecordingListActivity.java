package com.example.greentaxi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class RecordingListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerViewRecordings;
    private ArrayList<Recording> recordingArraylist;
    private RecordingAdapter recordingAdapter;
    private TextView textViewNoRecordings;

    private int RECORD_AUDIO_REQUEST_CODE =123 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_list);

        recordingArraylist = new ArrayList<Recording>();

        initViews();

        fetchRecordings();

    }

    private void fetchRecordings() {

        File root = android.os.Environment.getExternalStorageDirectory();
        String path = root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        if( files!=null ){

            for (int i = 0; i < files.length; i++) {

                Log.d("Files", "FileName:" + files[i].getName());
                String fileName = files[i].getName();
                String recordingUri = root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios/" + fileName;

                Recording recording = new Recording(recordingUri,fileName,false);
                recordingArraylist.add(recording);
            }

            textViewNoRecordings.setVisibility(View.GONE);
            recyclerViewRecordings.setVisibility(View.VISIBLE);
            setAdaptertoRecyclerView();

        }else{
            textViewNoRecordings.setVisibility(View.VISIBLE);
            recyclerViewRecordings.setVisibility(View.GONE);
        }

    }

    private void setAdaptertoRecyclerView() {
        recordingAdapter = new RecordingAdapter(this,recordingArraylist);
        recyclerViewRecordings.setAdapter(recordingAdapter);
    }

    private void initViews() {

        /** setting up the toolbar  **/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Recording List");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
        setSupportActionBar(toolbar);

        /** enabling back button ***/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** setting up recyclerView **/
        recyclerViewRecordings = (RecyclerView) findViewById(R.id.recyclerViewRecordings);
        recyclerViewRecordings.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerViewRecordings.setHasFixedSize(true);

        textViewNoRecordings = (TextView) findViewById(R.id.textViewNoRecordings);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToRecordAudio() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RECORD_AUDIO_REQUEST_CODE);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults.length == 3 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED){

                //Toast.makeText(this, "Record Audio permission granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        }

    }
}