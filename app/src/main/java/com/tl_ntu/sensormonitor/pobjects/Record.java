package com.tl_ntu.sensormonitor.pobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Record implements Serializable{

    String start;
    String stop;
    List<EventOwner> eventOwners;

    public Record(){
        eventOwners = new ArrayList<EventOwner>();
    }

    public String getStart() {
        return start;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public void setStart(String start) {
        this.start = start;
    }
    
    public List<EventOwner> getEventOwners() {
        return eventOwners;
    }
}

