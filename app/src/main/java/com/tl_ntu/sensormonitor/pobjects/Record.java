package com.tl_ntu.sensormonitor.pobjects;

import java.util.ArrayList;
import java.util.List;

public class Record {
    String id;
    String start;
    String stop;
    List<Sensor> sensors;

    public Record(){
        sensors = new ArrayList<Sensor>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Sensor> getSensors() {
        return sensors;
    }
}
