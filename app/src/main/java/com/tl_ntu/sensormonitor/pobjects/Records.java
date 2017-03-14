package com.tl_ntu.sensormonitor.pobjects;


import java.util.ArrayList;
import java.util.List;

public class Records {
    List<Record> records;

    public Records(){
        records = new ArrayList<Record>();
    }

    public List<Record> getRecords() {
        return records;
    }
}
