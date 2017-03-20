package com.tl_ntu.sensormonitor.pobjects;

import java.io.Serializable;

/**
 * Created by admin on 20.03.2017.
 */

public class Press implements Serializable{
    String buttonname;
    String time;

    public String getButtonname() {
        return buttonname;
    }

    public void setButtonname(String buttonname) {
        this.buttonname = buttonname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
