package com.tl_ntu.sensormonitor;

import android.os.BatteryManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BatteryMeasurement implements Runnable {

    RandomAccessFile raf;

    ArrayList<String> rawData;
    ArrayList<Long> rawTimes;

    boolean ready;

    public BatteryMeasurement(){

        rawData = new ArrayList<>();
        rawTimes = new ArrayList<>();
        ready = false;
    }

    @Override
    public void run() {

        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        try {
            raf = new RandomAccessFile(new File(Constants.SYS_CLASS_CURRENT_NOW), "r");
            recordBattery();
            raf.close();
            ready = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void recordBattery() throws IOException {

        /*

        while(batteryState){
            raf.seek(0);
            rawBatteryData.add(raf.readLine());
            rawBatteryTimes.add(System.currentTimeMillis());
        }
        */
        for(int i = 0; i < 200; i++){ // THIS IS DONE AT 10MS PER ROUND --> EXAMPLE: i = 10 is 100MS
            raf.seek(0);
            rawData.add(raf.readLine());
            rawTimes.add(System.currentTimeMillis());
        }
    }

    public ArrayList<Long> getRawTimes() {
        return rawTimes;
    }

    public ArrayList<String> getRawData() {
        return rawData;
    }
}
