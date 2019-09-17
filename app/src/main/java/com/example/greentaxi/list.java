package com.example.greentaxi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class list extends AppCompatActivity {

    EditText route_search_start;
    EditText route_search_destinate;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_search);

        route_search_start = findViewById(R.id.route_search_start);
        route_search_destinate = findViewById(R.id.route_search_destinate);


    }


    public void onClick(View view){

        switch (view.getId()){
            case R.id.route_search_ok:
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = firebaseDatabase.getReference("lately");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        lately lately_add = new lately(route_search_start.getText().toString(),
                                route_search_destinate.getText().toString());
                        currentUserInfo user = new currentUserInfo();
                        String userid = user.getId();
                        databaseReference.child(userid).setValue(lately_add);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                break;
        }
    }

}
