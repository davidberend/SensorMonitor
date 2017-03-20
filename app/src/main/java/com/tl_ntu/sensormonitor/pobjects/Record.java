package com.tl_ntu.sensormonitor.pobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Record implements Serializable{

    String start;
    String stop;
    List<Sensor> sensors;
    List<Press> presses;

    public Record(){
        sensors = new ArrayList<Sensor>();
        presses = new ArrayList<Press>();
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

    public List<Press> getPresses() {
        return presses;
    }
}
