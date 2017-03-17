package com.tl_ntu.sensormonitor.pobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sensor implements Serializable{
    String name;
    List<Data> dataentries;

    public Sensor(){
        dataentries = new ArrayList<Data>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Data> getDataentries() {
        return dataentries;
    }

    public void setDataentries(List<Data> dataentries) {
        this.dataentries = dataentries;
    }
}
