package com.example.greentaxi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class signupdb extends AppCompatActivity {

    private static String IP_ADDRESS = "211.58.194.135";
    private static String TAG = "phptest";

    private EditText signUp_id;
    private EditText signUp_pass;
    private EditText signUp_passCheck;
    private EditText signUp_name;
    private EditText signUp_email;
    private EditText signUp_number;

    private Button signUp_ok;
    private Button signUp_no;

    private String loginResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        signUp_id = findViewById(R.id.editId);
        signUp_pass = findViewById(R.id.editPassword);
        signUp_passCheck = findViewById(R.id.editPassword2);
        signUp_name = findViewById(R.id.editName);
        signUp_email = findViewById(R.id.editEmail);
        signUp_number = findViewById(R.id.editPhoneNumber);

        signUp_ok = findViewById(R.id.signUp_ok);
        signUp_no = findViewById(R.id.signUp_cancel);




        Button buttonInsert = (Button)findViewById(R.id.signUp_ok);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ID = signUp_id.getText().toString();
                String PASSWORD = signUp_pass.getText().toString();
                String PASSWORDCHECK = signUp_passCheck.getText().toString();
                String NAME = signUp_name.getText().toString();
                String EMAIL = signUp_email.getText().toString();
                String PHONE = signUp_number.getText().toString();

                InsertData task = new InsertData();
                try {
                    loginResult = task.execute("http://" + IP_ADDRESS + "/signup_and.php", ID,PASSWORD,PASSWORDCHECK
                    ,NAME,EMAIL,PHONE).get();
                    loginResult.trim();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(loginResult.equals("\uFEFF\uFEFF"))
                {
                    signupdb.this.finish();
                    Log.d("맞다면 " , "true");
                } else {
                    Log.d("틀리다면", "false");
                }


                System.out.println(loginResult);

                signUp_id.setText("");
                signUp_pass.setText("");
                signUp_passCheck.setText("");
                signUp_name.setText("");
                signUp_email.setText("");
                signUp_number.setText("");


            }
        });

    }



    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(signupdb.this,
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
            String PASSWORDCHECK = (String)params[3];
            String NAME = (String)params[4];
            String EMAIL = (String)params[5];
            String PHONE = (String)params[6];

            String serverURL = (String)params[0];
            String postParameters = "ID=" + ID + "&PASSWORD=" + PASSWORD + "&PASSWORDCHECK=" + PASSWORDCHECK
                    + "&NAME=" + NAME+ "&EMAIL=" + EMAIL+ "&PHONE=" + PHONE;


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


}