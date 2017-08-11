package com.tl_ntu.sensormonitor.pobjects;

import java.io.Serializable;

public class Press implements Serializable{


    String label;

    String start;
    String stop;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }
}
