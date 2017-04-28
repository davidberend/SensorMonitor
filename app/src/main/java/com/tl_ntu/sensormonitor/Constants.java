package com.tl_ntu.sensormonitor;

public class Constants {
    // Hardware status files
    public static final String SYS_CLASS_VOLTAGE_NOW = "/sys/class/power_supply/battery/voltage_now";
    public static final String SYS_CLASS_CURRENT_NOW = "/sys/class/power_supply/battery/current_now";
    public static final String SYS_CLASS_TEMP = "/sys/class/power_supply/battery/temp";
    public static final String SYS_CLASS_CAPACITY = "/sys/class/power_supply/battery/capacity";

    // Sensor Files
    public static final int TYPE_BATTERY = 2122512;

}