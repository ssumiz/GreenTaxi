package com.example.greentaxi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapInfo;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class route extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY = "AAAAA7ULgec:APA91bHN7mj4A1XuBvCutP70RJJb55yamoDOTavLIMoVoLAyHvAhZcN4iWssGcmr_9ynWrxG4g2PulB5qhSgDAUacuVPwiqdKW66HcsKf7qpsLNkfQckQ01Eq5tSrXhn8cJXyQCc66JB";
    private backPress backpress;

    boolean ride = false;;

    TextView test, textView, taxifare;

    String s_location = null, d_location = null;
    String fare = null, dis = null;

    LatLng s_LatLng = null, d_LatLng = null;

    Double s_LatLng_lati = null, s_LatLng_long = null, d_LatLng_lati = null, d_LatLng_long = null;

    Button sos;
    ImageButton back;

    TMapView tmapView = null;

    List<Address> s_list = null;
    List<Address> d_list = null;

    TMapPoint tMapPointStart = null, tMapPointEnd = null;

    TMapGpsManager tmapgps = null;

    TMapData tMapData = new TMapData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route);

        backpress = new backPress(this);

        back = findViewById(R.id.route_back);
        tmapView = new TMapView(this);
        textView = findViewById(R.id.route_ride);
        taxifare = findViewById(R.id.route_taxifare);
        sos = findViewById(R.id.route_sos);

        sos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        //s_location = intent.getExtras().getString("start");
        s_location = global.getStartName();
        d_location = global.getDestName();
        Log.d("Test","s_location 확인 :"+s_location);
        Log.d("Test","d_location 확인 :"+d_location);

        Geocoder geocoder = new Geocoder(this);

        try {
                s_list = geocoder.getFromLocationName(s_location, 1);
                d_list = geocoder.getFromLocationName(d_location, 1);
                Log.d("Test", "s_list 받아오기 1차 :" + s_list);
                Log.d("Test", "d_list 받아오기 1차 :" + d_list);

                /*
                   while(s_list.size() == 0 || d_list.size() == 0) {
                        s_list = geocoder.getFromLocationName(s_location, 1);
                        d_list = geocoder.getFromLocationName(d_location, 1);
                        Log.d("Test", "s_list 받아오기 2차 :" + s_list);
                        Log.d("Test", "d_list 받아오기 2차:" + d_list);

                    }
                    */
            // d_list 에서 값을 못받아옴
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러 발생");
        } catch (NullPointerException e) {
            Log.e("test", "index and size 오류");
        }

        s_LatLng = new LatLng(s_list.get(0).getLatitude(), s_list.get(0).getLongitude());
        d_LatLng = new LatLng(d_list.get(0).getLatitude(), d_list.get(0).getLongitude());
        // 122,123 오류 잡기 !

        s_LatLng_lati = s_LatLng.latitude;
        s_LatLng_long = s_LatLng.longitude;
        Log.d("test","s_Latlng"+s_LatLng_lati);
        d_LatLng_lati = d_LatLng.latitude;
        d_LatLng_long = d_LatLng.longitude;

        try{
            global.setS_Lati(s_LatLng_lati);
            global.setS_Logi(s_LatLng_long);
            global.setD_Lati(s_LatLng_lati);
            global.setD_Logi(s_LatLng_long);
            Log.d("test","Global getS_Lati"+global.getS_Lati());
            Log.d("test","Global getD_Logi"+global.getD_Logi());
            Log.d("test","Global getC_Lati"+global.getC_Lati());
        }catch (Exception e){

        }

        // 출발 지점 마커 생성
        TMapMarkerItem markerItem1 = new TMapMarkerItem();
        TMapPoint tMapPoint1 = new TMapPoint(s_LatLng_lati, s_LatLng_long); //
        markerItem1.setTMapPoint(tMapPoint1);
        markerItem1.setName("출발 지점");
        tmapView.addMarkerItem("markerItem1", markerItem1);

        // 도착 지점 마커 생성
        TMapMarkerItem markerItem2 = new TMapMarkerItem();
        TMapPoint tMapPoint2 = new TMapPoint(d_LatLng_lati, d_LatLng_long); //
        markerItem2.setTMapPoint(tMapPoint2);
        markerItem2.setName("도착 지점");
        tmapView.addMarkerItem("markerItem2", markerItem2);

        tMapPointStart = new TMapPoint(s_LatLng_lati, s_LatLng_long);
        tMapPointEnd = new TMapPoint(d_LatLng_lati, d_LatLng_long);


        LinearLayout tmap = findViewById(R.id.tmap);

        tmapView.setSKTMapApiKey("fb390902-e9ca-4aed-bf74-66f14d37841e");
        tmap.addView(tmapView);


        // 메인 스레드가 아닌 새로운 스레드를 생성해서 실행
        findViewById(R.id.tmap).post(new Runnable() {
            @Override
            public void run() {
                findPathData();
            }
        });

        tmapView.setIconVisibility(true); // 현재위치에 아이콘을 표시할지 여부 설정

        setGps();

        // 택시요금, 소요시간 파싱해온것
        tMapData.findPathDataAllType(TMapData.TMapPathType.CAR_PATH, tMapPointStart, tMapPointEnd, new TMapData.FindPathDataAllListenerCallback() {
            @Override
            public void onFindPathDataAll(Document document) {
                Element root = document.getDocumentElement();
                NodeList nodeListPlacemark = root.getElementsByTagName("Document");
                for (int i = 0; i < nodeListPlacemark.getLength(); i++) {

                    NodeList tax = root.getElementsByTagName("tmap:taxiFare");
                    fare = tax.item(0).getChildNodes().item(0).getNodeValue();

                    int bun, cho;
                    NodeList distance = root.getElementsByTagName("tmap:totalTime");
                    dis = distance.item(0).getChildNodes().item(0).getNodeValue();
                    bun = Integer.parseInt(dis) / 60;
                    cho = Integer.parseInt(dis) % 60;
                    taxifare.setText("  예상 택시요금 : " + fare + " 원\n" + "  예상 소요시간 : " + bun + "분 " + cho + "초");

                }
            }

        });



    }


    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                tmapView.setLocationPoint(longitude, latitude);
                try {
                    global.setC_Lati(latitude);
                    global.setC_Logi(longitude);
                    Log.d("test","Global getC_Logi"+global.getC_Logi());
                }catch (Exception e){

                };
            }

        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    public void setGps() {
        final LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
                1000, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);
    }


    // 자동차기준 경로 표시를 해주는 메소드
    private void findPathData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               /*
                TMapPoint tMapPointStart = new TMapPoint(37.570841, 126.985302); // SKT타워(출발지)
                TMapPoint tMapPointEnd = new TMapPoint(37.551135, 126.988205); // N서울타워(목적지)
               */

                try {
                    TMapPolyLine tMapPolyLine = new TMapData().findPathData(tMapPointStart, tMapPointEnd);
                    tMapPolyLine.setLineColor(Color.BLUE);
                    tMapPolyLine.setLineWidth(2);
                    tmapView.addTMapPolyLine("Line1", tMapPolyLine);

                    // 중심점과 줌레벨 설정
                    setBounds(tMapPolyLine.getLinePoint());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 중심점과 줌레벨 설정해주는 메소드 정의
    private void setBounds(ArrayList<TMapPoint> alTMapPoint) {
        TMapInfo tMapInfo = tmapView.getDisplayTMapInfo(alTMapPoint);
        tmapView.setCenterPoint(tMapInfo.getTMapPoint().getLongitude(), tMapInfo.getTMapPoint().getLatitude());
        tmapView.setZoomLevel(tMapInfo.getTMapZoomLevel());
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backpress.onBackPressed();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.route_back:
                Intent intent = new Intent(this, route_search.class);
                startActivity(intent);
                break;

            case R.id.route_ride:

                if (ride == false){
                    sendPostToFCM();
                    textView.setText("하차 하기");
                    textView.setTextColor(Color.parseColor("#FF0000"));

                    ride = true;


                }
                else if( ride == true ){
                    sendPostToFCM2();
                    textView.setTextColor(Color.parseColor("#FF000000"));
                    textView.setText("승차 하기");

                    ride = false;


                }

        }

    }

    private void sendPostToFCM(){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.getReference("message").child("userData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // FMC 메시지 생성 start
                            JSONObject root = new JSONObject();
                            JSONObject notification = new JSONObject();
                            notification.put("body", "파트너가 택시에 승차하였습니다.");
                            notification.put("title", getString(R.string.app_name));
                            root.put("click_action","OPEN_ACTIVITY");
                            root.put("notification", notification);
                            root.put("to", "dBiP_uEQQUs:APA91bHdTM3V0aa-YQ5BqFSPh-SK5d7DoaziNaDPWLW5EAtItlMVEyRThvpEaUDIeXwHZJCA-JRxKDkFSdKzW8XS9-L7zS-o8yaSmlX3jLqY-2SiHXbhPUlVrwajeG_5xQnF2TRwmP0N");
                            // FMC 메시지 생성 end
                            // 재혁

                            URL Url = new URL(FCM_MESSAGE_URL);
                            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            conn.addRequestProperty("Authorization", "key=" + SERVER_KEY);
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setRequestProperty("Content-type", "application/json");
                            OutputStream os = conn.getOutputStream();
                            os.write(root.toString().getBytes("utf-8"));
                            os.flush();
                            conn.getResponseCode();

                            // 여기서도 notifacation 값 받아짐
                            String body =notification.getString("title");
                                    Log.d("Test","Notification Body2 : "+body);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        mDatabaseReference = mFirebaseDatabase.getReference("message");
        mDatabaseReference.child("Latitude").push().setValue(global.getC_Lati());
        mDatabaseReference.child("Logitude").push().setValue(global.getC_Logi());
        mDatabaseReference.child("Activity").push().setValue("route.class");
            Log.d("test","aria");

    }

    private void sendPostToFCM2(){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.getReference("message").child("userData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // FMC 메시지 생성 start
                            JSONObject root = new JSONObject();
                            JSONObject notification = new JSONObject();
                            notification.put("body", "파트너가 택시에 하차하였습니다.");
                            notification.put("title", getString(R.string.app_name));
                            root.put("notification", notification);
                            root.put("to", "dBiP_uEQQUs:APA91bHdTM3V0aa-YQ5BqFSPh-SK5d7DoaziNaDPWLW5EAtItlMVEyRThvpEaUDIeXwHZJCA-JRxKDkFSdKzW8XS9-L7zS-o8yaSmlX3jLqY-2SiHXbhPUlVrwajeG_5xQnF2TRwmP0N");
                            // FMC 메시지 생성 end
                            // 재혁

                            URL Url = new URL(FCM_MESSAGE_URL);
                            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            conn.addRequestProperty("Authorization", "key=" + SERVER_KEY);
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setRequestProperty("Content-type", "application/json");
                            OutputStream os = conn.getOutputStream();
                            os.write(root.toString().getBytes("utf-8"));
                            os.flush();
                            conn.getResponseCode();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


