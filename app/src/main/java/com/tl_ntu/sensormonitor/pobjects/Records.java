package com.tl_ntu.sensormonitor.pobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Records implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Record> records;

    public Records(){
        records = new ArrayList<Record>();
    }

    public List<Record> getRecords() {
        return records;
    }
}
