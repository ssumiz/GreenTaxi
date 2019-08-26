package com.example.greentaxi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class main_logined extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    private backPress backpress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);
        initFirebaseDatabase();
        backpress = new backPress(this);



    }

    @Override
    public void onBackPressed(){
        //super.onBackPressed();
        backpress.onBackPressed();

    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.main_search:
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("FCM", "getInstanceId failed", task.getException());
                                    return;
                                }

                                // Get new Instance ID token ( 토큰 받아오는 메소드 )
                                String token = task.getResult().getToken();

                                Log.d("FCM_token", token);
                                global.setFcmToken(token);

                            }
                        });
                mDatabaseReference.push().setValue("fcmToken :"+ global.getFcmToken()+"\n"+"userId : Test" );

                Intent intent = new Intent(this , route_search.class );
                startActivity(intent);
                break;

            case R.id.main_alarm:
                break;

            case R.id.main_record:
                Intent record = new Intent(this, com.example.greentaxi.record.class);
                startActivity(record);
                break;

            case R.id.main_partner:
                Intent partner = new Intent(this, com.example.greentaxi.partner.class);
                startActivity(partner);
                break;

            case R.id.main_serviceCenter:
                Intent intent2 = new Intent(this , serviceCenter.class );
                startActivity(intent2);
                break;
            case R.id.main_settingButton:
                Intent setting = new Intent(this , set.class);
                startActivity(setting);
                break;
        }

    }


    // FCM 초기화 시켜주는 메소드
    private void initFirebaseDatabase() {

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("message").child("userData");

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String message = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }

    public void onDestroy(){
        super.onDestroy();
        mDatabaseReference.removeEventListener(mChildEventListener);
    }

}

