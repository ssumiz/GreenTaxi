package com.example.greentaxi;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;

import com.example.greentaxi.mlkitbarcodescan.BarcodeScanner.BarcodeScannerActivity;
import com.example.greentaxi.mlkitbarcodescan.ListingSetup.CustomItemClickListener;
import com.example.greentaxi.mlkitbarcodescan.LocalData.ContactDetail;
import com.example.greentaxi.mlkitbarcodescan.LocalData.DBHandler;
import com.example.greentaxi.mlkitbarcodescan.Util.AlertDialog.AlertDialogHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class main_logined extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    private backPress backpress;


    DBHandler dbHandler;
    RecyclerView rvContactsList;
    FloatingActionButton fabAdd;

    ArrayList<ContactDetail> contactDetailArrayList;
    LinearLayoutManager layoutManager;
    AlertDialogHelper alertDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);

        //initFirebaseDatabase();

        backpress = new backPress(this);
        ButterKnife.bind(this);
        dbHandler = new DBHandler(this);
        alertDialogHelper=new AlertDialogHelper(this);



    }

    @Override
    public void onBackPressed(){
        //super.onBackPressed();
        backpress.onBackPressed();

    }

    public void loadAdapter(final ArrayList<ContactDetail> itemsList) {

        rvContactsList.post(() -> {
            rvContactsList.getRecycledViewPool().clear();

        });
    }
    public CustomItemClickListener getItemCLickListener()
    {
        CustomItemClickListener customItemClickListener=new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }

        };

        return customItemClickListener;
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

                                // Get new Instance ID token
                                String token = task.getResult().getToken();

                                Log.d("FCM", token);
                                global.setFcmToken(token);

                            }
                        });


                Intent intent = new Intent(this , route_search.class );
                startActivity(intent);
                break;

            case R.id.main_alarm:
                Intent record = new Intent(this, com.example.greentaxi.mlkitbarcodescan.BarcodeScanner.BarcodeScannerActivity.class);
                startActivity(record);
                break;

            case R.id.main_record:
                Intent test = new Intent(this, RecordingListActivity.class);
                test.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(test);
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
            case R.id.main_qrcode:
                Intent qrcode = new Intent(this , HomeActivity.class);
                startActivity(qrcode);
                break;
        }

    }

/*
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
*/
}

