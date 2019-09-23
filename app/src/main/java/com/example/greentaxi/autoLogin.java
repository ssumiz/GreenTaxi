package com.example.greentaxi;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class autoLogin {
    static final String PREF_USER_NAME = "username";
    static final String PREF_PASS_WORD = "password";

    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME,userName);;
        editor.commit();
    }

    public static void setUserPassWord(Context ctx,String passWord){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PASS_WORD,passWord);
        editor.commit();
    }

    public static String getUserName(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_NAME,"");
    }
    public static String getUserPassWord(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_PASS_WORD,"");
    }

    public static void clearUserName(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }

    public static int checkisEmpty(Context ctx){
        if(!(getSharedPreferences(ctx).getString(PREF_USER_NAME,"").equals(""))){
            return 1;
        }
        else{
            return 0;
        }

    }


}