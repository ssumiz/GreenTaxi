package com.example.greentaxi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.concurrent.ExecutionException;


public class main extends AppCompatActivity {
<<<<<<< HEAD
    private static String IP_ADDRESS = "211.58.194.135";
=======
    private static String IP_ADDRESS = "175.112.209.195";
>>>>>>> 기존에 있던 PHP 관련 된것들 삭제
    private static String TAG = "phptest";

    private EditText login_id;
    private EditText login_pass;

    private Button login_ok;
    private Button signup;


    private String loginResult;
<<<<<<< HEAD
    private final String TAG3 = "main_get_keyhash";


    private static final String TAG2 = "";
    SessionCallback callback;
=======



    private static final String TAG2 = "";
>>>>>>> 기존에 있던 PHP 관련 된것들 삭제

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

<<<<<<< HEAD
        login_id= findViewById(R.id.inputId);
=======
        login_id = findViewById(R.id.inputId);
>>>>>>> 기존에 있던 PHP 관련 된것들 삭제
        login_pass = findViewById(R.id.inputPassword);


        login_ok = findViewById(R.id.login);
        signup = findViewById(R.id.signUp);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                Intent intent = new Intent(getApplicationContext(),signupdb.class);
                startActivity(intent);
            }
        });





        Button buttonInsert = (Button)findViewById(R.id.login);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ID = login_id.getText().toString();
                String PASSWORD = login_pass.getText().toString();


                InsertData task = new InsertData();
                try {
                    loginResult = task.execute("http://" + IP_ADDRESS + "/login_and.php", ID,PASSWORD).get();
                    loginResult.trim();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(loginResult.equals("\uFEFF\uFEFF"))
                {
                    Intent intent = new Intent(getApplicationContext(),main_logined.class);
                    startActivity(intent);
                    main.this.finish();
                    Log.d("맞다면 " , "true");
                } else {
                    Log.d("틀리다면", "false");
                }


                System.out.println(loginResult);

                login_id.setText("");
                login_pass.setText("");


            }
        });
        getHashKey(this);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        requestMe();



    }


    // 카카오톡 디버그용 키
    @Nullable
    public static String getHashKey(Context context) {

        final String TAG = "KeyHash";
        String keyHash = null;
        try {
            PackageInfo info =

                    context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {

                MessageDigest md;

                md = MessageDigest.getInstance("SHA");

                md.update(signature.toByteArray());

                keyHash = new String(Base64.encode(md.digest(), 0));

                Log.d(TAG, keyHash);

            }
        } catch (Exception e) {

            Log.e("name not found", e.toString());

        }
        if (keyHash != null) {

            return keyHash;

        } else {

            return null;

        }
    }

    class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            UserManagement.requestMe(new MeResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        //에러로 인한 로그인 실패
                        // finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                }

                @Override
                public void onNotSignedUp() {

                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.

                    // Log.e("UserProfile", userProfile.toString());
                    // Log.e("UserProfile", userProfile.getId() + "");


                    long number = userProfile.getId();


                }
            });

        }
        // 세션 실패시
        @Override
        public void onSessionOpenFailed(KakaoException exception) {


        }
    }
    public void requestMe() {
            //유저의 정보를 받아오는 함수
            UserManagement.requestMe(new MeResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    Log.e(TAG3, "error message=" + errorResult);
//                super.onFailure(errorResult);
                }
                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.d(TAG3, "onSessionClosed1 =" + errorResult);
                }
                @Override
                public void onNotSignedUp() {
                    //카카오톡 회원이 아닐시
                    Log.d(TAG3, "onNotSignedUp ");
                }
                @Override
                public void onSuccess(UserProfile result) {
                    Log.e("UserProfile", result.toString());
                    Log.e("UserProfile", result.getId() + "");
                }
            });
    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(main.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String ID = (String)params[1];
            String PASSWORD = (String)params[2];


            String serverURL = (String)params[0];
            String postParameters = "ID=" + ID + "&PASSWORD=" + PASSWORD;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }




=======
                Intent intent = new Intent(getApplicationContext(), signUp.class);
                startActivity(intent);

            }
        });

        login_ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), main_logined.class);
                startActivity(intent);
                finish();
            }
        });

    }
>>>>>>> 기존에 있던 PHP 관련 된것들 삭제
}


