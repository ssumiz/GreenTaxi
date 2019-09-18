package com.example.greentaxi;

public class favorite {
    public String getRoute_search_start() {
        return route_search_start;
    }

    public void setRoute_search_start(String route_search_start) {
        this.route_search_start = route_search_start;
    }

    public String getRoute_search_destinate() {
        return route_search_destinate;
    }

    public void setRoute_search_destinate(String route_search_destinate) {
        this.route_search_destinate = route_search_destinate;
    }

    private String route_search_start;
    private String route_search_destinate;

    public favorite(String a, String b){
        this.route_search_start =a;
        this.route_search_destinate =b;
    }
}