package com.tl_ntu.sensormonitor.pobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventOwner implements Serializable{
    String name;
    List<Event> eventEntries;

    public EventOwner(){
        eventEntries = new ArrayList<Event>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEventEntries() {
        return eventEntries;
    }

    public void setEventEntries(List<Event> eventEntries) {
        this.eventEntries = eventEntries;
    }
}
