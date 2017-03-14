package com.tl_ntu.sensormonitor;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.Sensor;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.tl_ntu.sensormonitor.pobjects.*;

/*
    Sensor implementation work flow
    - set requirement bool
    - set sensor object
    - set sensor data count
    - set sensor data list
    - init comp in constructor
    - reset in disable
 */

class DataManagement implements SensorListener{

    private SensorManagement sensorManagement;

    List<Integer> requiredSensors;

    private Records records;
    private int recordCount;

    // Accelerometer
    private boolean accelerometerState;
    com.tl_ntu.sensormonitor.pobjects.Sensor accelerometer;
    private int accelerometerDataID;
    private List<Data> accelerometerData;

    // Gyroscope
    private boolean gyroscopeState;
    com.tl_ntu.sensormonitor.pobjects.Sensor gyroscope;
    private int gyroscopeDataID;
    private Float gyroscopeX;
    private Float gyroscopeY;
    private Float gyroscopeZ;

    public DataManagement(Context context){
        sensorManagement = new SensorManagement(context, this);

        requiredSensors = new ArrayList<Integer>();

        records = new Records();
        recordCount = 0;
        
        accelerometerData = new ArrayList<Data>();
    }


    public void read(ArrayList<Integer> requiredSensors){
        
        // Get relevant sensors
        this.requiredSensors = requiredSensors;
        
        // Prepare 
        enableSensorMeasurements();
        sensorManagement.registerSensors(requiredSensors);

        // Start retrieving data
        recordCount += 1;
        Record record = new Record();
        records.getRecords().add(record);

        record.setId(Integer.toString(recordCount));
        record.setStart(Long.toString(System.currentTimeMillis()));
        
        // Add required sensors to record
        if(accelerometerState){
            accelerometer = new com.tl_ntu.sensormonitor.pobjects.Sensor();
            accelerometer.setName("accelerometer");
            accelerometer.setDataentries(accelerometerData);
            record.getSensors().add(accelerometer);
        }
        /*
        if(gyroscopeState) {
            gyroscope = new com.tl_ntu.sensormonitor.pobjects.Sensor();
            gyroscope.setName("gyroscope");
            record.getSensors().add(gyroscope);
        }*/

    }
    
    public void save(){
        disableSensorMeasurements();
        sensorManagement.unregisterSensors();
        records.getRecords().get(records.getRecords().size()-1).setStop(Long.toString(System.currentTimeMillis()));

        Gson gson = new Gson();
        String xyz = gson.toJson(records);
    }
    @Override
    public void onValueChange(SensorEvent event) {
        if(accelerometerState){
            Data data = new Data();
            accelerometerDataID += 1;
            data.setId(Integer.toString(accelerometerDataID));
            data.setTime(Long.toString(System.currentTimeMillis()));

            Value accelerometerX = new Value();
            accelerometerX.setName("x");
            accelerometerX.setValue(Float.toString(sensorManagement.getAccelerometerX()));

            Value accelerometerY = new Value();
            accelerometerY.setName("y");
            accelerometerY.setValue(Float.toString(sensorManagement.getAccelerometerY()));

            Value accelerometerZ = new Value();
            accelerometerZ.setName("z");
            accelerometerZ.setValue(Float.toString(sensorManagement.getAccelerometerZ()));

            data.getValues().add(accelerometerX);
            data.getValues().add(accelerometerY);
            data.getValues().add(accelerometerZ);

            accelerometerData.add(data);
        }
    }
    /*
    private Data method(int dataID, ){
        Data data = new Data();
        data.setId(Integer.toString(accelerometerDataID));
        data.setTime(Long.toString(System.currentTimeMillis()));
    }*/



    //=========================================
    // Measure utils
    //=========================================
    private void enableSensorMeasurements(){

        accelerometerDataID = 0;

        if(requiredSensors.contains(Sensor.TYPE_ACCELEROMETER))
            accelerometerState = true;

        if(requiredSensors.contains(Sensor.TYPE_GYROSCOPE))
            gyroscopeState = true;
    }

    private void disableSensorMeasurements(){
        accelerometerState = false;
        gyroscopeState = false;
    }

}
