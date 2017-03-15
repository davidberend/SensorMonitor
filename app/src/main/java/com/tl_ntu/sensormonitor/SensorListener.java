package com.tl_ntu.sensormonitor;

import android.app.usage.UsageEvents;
import android.hardware.SensorEvent;

import java.util.EventListener;

interface SensorListener extends EventListener {
    void onValueChange(SensorEvent event, int sensor);
}
