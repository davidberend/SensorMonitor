package com.tl_ntu.sensormonitor.pobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Event implements Serializable{
    String id;
    String time;
    List<Value> values;

    public Event(){
        values = new ArrayList<Value>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Value> getValues() {
        return values;
    }
}
