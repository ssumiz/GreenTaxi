package com.example.greentaxi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class main extends AppCompatActivity {

    private EditText login_id;
    private EditText login_pass;
    private Button login_ok;
    private Button signup;

    DatabaseReference userName = null;
    DatabaseReference userPassword = null;


    private static final String TAG2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        login_id = findViewById(R.id.inputId);
        login_pass = findViewById(R.id.inputPassword);
        login_ok = findViewById(R.id.login);
        signup = findViewById(R.id.signUp);

        userName = FirebaseDatabase.getInstance().getReference().child("member_info").child(login_id.getText().toString());
        userPassword = FirebaseDatabase.getInstance().getReference().child("member_info").child(login_id.getText().toString());

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), signUp.class);
                startActivity(intent);

            }
        });

        login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (login_id.getText().toString().isEmpty() || login_pass.getText().toString().isEmpty()) {
                    Toast.makeText(main.this, "공백없이 입력해주세요.", Toast.LENGTH_SHORT).show();

                } else {

                    userPassword.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Object info = dataSnapshot.getKey();
                            Log.d("datasnapshot", info + " ");
                            if (dataSnapshot.child(login_id.getText().toString()).exists()) {
                                member_info user = dataSnapshot.child(login_id.getText().toString()).getValue(member_info.class);
                                if (user.getPassword().equals(login_pass.getText().toString())) {

                                    currentUserInfo c_user = new currentUserInfo();
                                    c_user.setId(login_id.getText().toString());

                                    Intent intent = new Intent(getApplicationContext(), main_logined.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(main.this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                Toast.makeText(main.this, "아이디를 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });


    }

    // 로그인 정보 유무 확인하는 메소드
    // 값 없으면 null 값
/*
    public boolean login_check() {


            userPassword.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Log.d(" id 값 :", login_id.getText().toString());
                    Log.d(" passwd 값 :", login_pass.getText().toString());

                    Object password = dataSnapshot.getValue(Object.class);
                    String pwd = dataSnapshot.getKey();

                    userinfo.setUserPassword((String) password);

                    Log.d("test", pwd + " ");
                    Log.d("test2", userinfo.getUserPassword() + " ");

                    if(userinfo.getUserPassword().equals(login_pass.getText().toString())) {
                        test = "true";
                    } else {
                        test = "false";
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

                @Override
                public boolean equals(@Nullable Object obj) {
                    return super.equals(obj);

                }


            });


        return false;
    }

 */


}




