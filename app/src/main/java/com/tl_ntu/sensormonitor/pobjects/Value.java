package com.tl_ntu.sensormonitor.pobjects;

import java.io.Serializable;

public class Value implements Serializable{
    String name;
    String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
