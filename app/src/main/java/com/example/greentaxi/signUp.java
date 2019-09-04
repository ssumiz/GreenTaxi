package com.example.greentaxi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.regex.Pattern;

public class signUp extends AppCompatActivity {

    EditText editId;
    EditText editPassword;
    EditText editPassword2;
    EditText editName;
    EditText editEmail;
    EditText editPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

         editId = findViewById(R.id.editId);
         editPassword = findViewById(R.id.editPassword);
         editPassword2 = findViewById(R.id.editPassword2);
         editName = findViewById(R.id.editName);
         editEmail = findViewById(R.id.editEmail);
         editPhoneNumber = findViewById(R.id.editPhoneNumber);


    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUp_ok:

                Boolean empty_Check = emptyCheck(), password_Check = passwordCheck();


                if( empty_Check == true && password_Check == true ) {


                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    final DatabaseReference databaseReference = firebaseDatabase.getReference("member_info");

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(editId.getText().toString()).exists()){
                                Toast.makeText(signUp.this, "동일한 ID가 존재합니다.", Toast.LENGTH_SHORT).show();
                            }else{

                                // 토큰 값 구하기
                                String token = FirebaseInstanceId.getInstance().getToken();
                                Log.d("디버깅한 디바이스의 토큰 값 :",token);

                                // 정보를 담고있는 클래스를 생성해서 Firebase에 저장하는 코드
                                member_info member_add = new member_info(editName.getText().toString(),editId.getText().toString(),
                                        editPassword.getText().toString(),editEmail.getText().toString(),editPhoneNumber.getText().toString(),token);

                                databaseReference.child(editId.getText().toString()).setValue(member_add);
                                Toast.makeText(signUp.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),main.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                    /*
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("회원 가입");
                    dialog.setMessage("회원가입이 완료되었습니다.");
                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getApplicationContext(),main.class);
                            startActivity(intent);
                            finish();

                        }
                    });
                    dialog.show();
                    Log.d("회원가입",": 회원 가입 완료 ");
                    */

                }
            break;


            case R.id.signUp_cancel:
                finish();
                break;

        }
    }

    public void signUp(){


        //databaseReference.child("member_info").child(editId.getText().toString()).setValue(member_add);


    }

    // 회원가입창 공백 여부 체크 메소드
    public boolean emptyCheck(){
        if(editId.getText().toString().length()==0 || editPassword.getText().toString().length() ==0 || editPassword2.getText().toString().length() ==0
        || editEmail.getText().toString().length() == 0 || editName.getText().toString().length() == 0 || editPhoneNumber.getText().toString().length() == 0 ){

            // 공백없이 입력해주세요 알림창
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("오류");
            dialog.setMessage("공백없이 입력해주셔야 합니다.");
            dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();
            Log.d("회원가입창 공백",":공백 오류 ");
            return false;
        }
        else
            Log.d("회원가입창 공백",": 오류 없음 ");
        return true;

    }

    // 패스워드 일치여부 확인 메소드
    public boolean passwordCheck(){
        if((editPassword.getText().toString().equals(editPassword2.getText().toString()))){

            Log.d("회원가입창 패스워드 일치",": 패스워드 일치 ");

            return true;

        }
        else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("오류");
            dialog.setMessage("비밀번호가 서로 다릅니다.");
            dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();
            Log.d("회원가입창 패스워드 불일치",": 패스워드 불일치 ");

            return false;
        }

    }


}
