package com.example.greentaxi;

public class currentUserInfo {
    private static String Id;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        currentUserInfo.name = name;
    }

    private static String name;

    public static String getId() {
        return Id;
    }

    public static void setId(String id) {
        Id = id;
    }
}
