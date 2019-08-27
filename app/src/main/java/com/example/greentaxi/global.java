package com.example.greentaxi;

import android.app.Application;

public class global extends Application {
    private static String startName;
    private static String destName;

    public static void setStartName(String str){ global.startName = str; }
<<<<<<< HEAD
    public    static void setDestName(String str){
=======
    public static void setDestName(String str){
>>>>>>> 기존에 있던 PHP 관련 된것들 삭제
        global.destName = str;
    }
    public static String getStartName(){
        return startName;
    }
    public static String getDestName(){ return destName; }


    // 출발 위치 위도,경도
    private static Double s_Lati;
    private static Double s_Logi;

    // 도착 위치 위도,경도
    private static Double d_Lati;
    private static Double d_Logi;

    // 현재 위치 위도, 경도
    private static Double c_Lati;
    private static Double c_Logi;

    public static Double getS_Lati() {
        return s_Lati;
    }

    public static void setS_Lati(Double s_Lati) {
        global.s_Lati = s_Lati;
    }

    public static Double getS_Logi() {
        return s_Logi;
    }

    public static void setS_Logi(Double s_Logi) {
        global.s_Logi = s_Logi;
    }

    public static Double getD_Lati() {
        return d_Lati;
    }

    public static void setD_Lati(Double d_Lati) {
        global.d_Lati = d_Lati;
    }

    public static Double getD_Logi() {
        return d_Logi;
    }

    public static void setD_Logi(Double d_Logi) {
        global.d_Logi = d_Logi;
    }

    public static Double getC_Lati() {
        return c_Lati;
    }

    public static void setC_Lati(Double c_Lati) {
        global.c_Lati = c_Lati;
    }

    public static Double getC_Logi() {
        return c_Logi;
    }

    public static void setC_Logi(Double c_Logi) {
        global.c_Logi = c_Logi;
    }


    public static String fcmToken;

    //Fcm Token 저장및 갱신
    public static String getFcmToken() {
        return fcmToken;
    }

    public static void setFcmToken(String fcmToken) {
        global.fcmToken = fcmToken;
    }
}
