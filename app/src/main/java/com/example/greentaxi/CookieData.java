package com.example.greentaxi;
// 디비 커넥션 클래스에서 언급111

public class CookieData {
    private String cookie;

    public String getCookie(){
        return cookie;
    }

    public void setCookie(String cookie){
        this.cookie = cookie;
    }

    private static CookieData instance = null;

    public static synchronized CookieData getInstance() {
        if (null == instance) {
            instance = new CookieData();
        }
        return instance;
    }
}