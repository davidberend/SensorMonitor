package com.tl_ntu.sensormonitor.pobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Record implements Serializable{

    String start;
    String stop;
    List<Event> events;

    String encryptionStart;
    String encryptionStop;


    public Record(){
        events = new ArrayList<Event>();
    }

    public String getEncryptionStart() {
        return encryptionStart;
    }

    public void setEncryptionStart(String encryptionStart) {
        this.encryptionStart = encryptionStart;
    }

    public String getEncryptionStop() {
        return encryptionStop;
    }

    public void setEncryptionStop(String encryptionStop) {
        this.encryptionStop = encryptionStop;
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

    public List<Event> getSensors() {
        return events;
    }
}
