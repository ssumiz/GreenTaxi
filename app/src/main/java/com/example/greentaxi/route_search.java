package com.example.greentaxi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class route_search extends AppCompatActivity {

    private backPress backpress;

    ImageButton route_search_startSearch, route_search_destSearch;
    private ListView recentSearch_list;
    private ListView favorites_list;
    private ArrayAdapter<String> adapter;
    private DatabaseReference databaseReference;
    List<Object> Array = new ArrayList<>();

    String c_user;

    currentUserInfo user = new currentUserInfo();

    EditText route_search_start, route_search_destinate;

    static final String routeSearchStart = "";
    static final String routeSearchDest = "";
    String start, dest;


    Bundle extras;

    private static String IP_ADDRESS = "175.112.209.195";
    private static String TAG = "phptest";

    private EditText starting;
    private EditText destini;


    private ImageView send,star;



    private String loginResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_search);

        backpress = new backPress(this);

        route_search_destSearch = findViewById(R.id.route_search_destSearch);
        route_search_startSearch = findViewById(R.id.route_search_startSearch);
        recentSearch_list = findViewById(R.id.route_search_recentSearch_list);
        favorites_list = findViewById(R.id.route_search_favorites_list);
        route_search_start = findViewById(R.id.route_search_start);
        route_search_destinate = findViewById(R.id.route_search_destinate);
        starting = findViewById(R.id.route_search_start);
        destini = findViewById(R.id.route_search_destinate);

        send = findViewById(R.id.route_search_ok);
        star = findViewById(R.id.star);




        ImageView buttonInsert = findViewById(R.id.route_search_ok);
        ImageView buttoninsert = findViewById(R.id.star);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String START = starting.getText().toString();
                String DESTI = destini.getText().toString();
                Log.d("텍스트 테스트 ", START + DESTI);


                InsertData task = new InsertData();
                try {
                    loginResult = task.execute("http://" + IP_ADDRESS + "/start_and.php", START, DESTI).get();
                    loginResult.trim();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (loginResult.equals("\uFEFF\uFEFF")) {
                    route_search.this.finish();
                    Log.d("맞다면 ", "true");
                } else {
                    Log.d("틀리다면", "false");
                }

                Intent intent4 = new Intent(route_search.this, route.class);
                intent4.putExtra("start", start);
                intent4.putExtra("end", dest);
                startActivity(intent4);

                System.out.println(loginResult);

                starting.setText("");
                destini.setText("");


            }
        });

        List<String> resentSearch = new ArrayList<>();
        List<String> favorites = new ArrayList<>();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resentSearch);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favorites);

        favorites_list.setAdapter(adapter2);
        recentSearch_list.setAdapter(adapter);


        favorites_list.setVisibility(View.INVISIBLE);

        extras = getIntent().getExtras();

        start = route_search_start.getText().toString();
        dest = route_search_destinate.getText().toString();


        route_search_start.setText(global.getStartName());
        route_search_destinate.setText(global.getDestName());


    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(route_search.this,
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

            String START = (String) params[1];
            String DESTI = (String) params[2];

            String serverURL = (String) params[0];
            String postParameters = "START=" + START + "&DESTI=" + DESTI;


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
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return "testestestestest" + sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }


    public void onBackPressed() {
        backpress.onBackPressed();
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.route_search_back:
                Intent intent = new Intent(this, main_logined.class);
                startActivity(intent);
                break;
            case R.id.route_search_recentSearch:
                // 최근검색 넣어주는 코드입니다.
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
                recentSearch_list.setAdapter(adapter);

                c_user = user.getId();
                Log.d("user", c_user + " ");

                databaseReference = FirebaseDatabase.getInstance().getReference("lately").child(c_user);

                databaseReference.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                adapter.clear();

                                                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                                                    String msg = data.getValue().toString();
                                                                    Array.add(msg);
                                                                    adapter.add(msg);
                                                                }
                                                                adapter.notifyDataSetChanged();
                                                                recentSearch_list.setSelection(adapter.getCount() - 1);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                recent();

                break;
            case R.id.route_search_favorites:
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
                recentSearch_list.setAdapter(adapter);

                c_user = user.getId();
                Log.d("user", c_user + " ");

                databaseReference = FirebaseDatabase.getInstance().getReference("favorite").child(c_user);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        adapter.clear();

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String msg = data.getValue().toString();
                            Array.add(msg);
                            adapter.add(msg);
                        }
                        adapter.notifyDataSetChanged();
                        recentSearch_list.setSelection(adapter.getCount() - 1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                break;
            case R.id.route_search_startSearch:
                Intent intent2 = new Intent(this, map.class);
                startActivity(intent2);
                break;
            case R.id.route_search_destSearch:
                Intent intent3 = new Intent(this, map2.class);
                startActivity(intent3);
                break;
            case R.id.route_search_ok:
                Intent intent4 = new Intent(this, route.class);
                intent4.putExtra("start", start);
                intent4.putExtra("end", dest);
                startActivity(intent4);
                break;
            case R.id.star:
                favorite();




        }

    }

    public void recent() {
        // 최근검색한것을 디비에 넣어주는 코드입니다.
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("lately");
        lately lately_add = new lately(route_search_start.getText().toString(),
                route_search_destinate.getText().toString());
        Log.d("테스트",route_search_start.getText().toString());
        currentUserInfo user = new currentUserInfo();
        String userid = user.getId();
        databaseReference.child(userid).setValue(lately_add);


    }

    public void favorite(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("favorite");
        favorite favorite_add = new favorite(route_search_start.getText().toString(),
                route_search_destinate.getText().toString());
        Log.d("테스트",route_search_start.getText().toString());
        currentUserInfo user = new currentUserInfo();
        String userid = user.getId();
        databaseReference.child(userid).setValue(favorite_add);
    }

}



