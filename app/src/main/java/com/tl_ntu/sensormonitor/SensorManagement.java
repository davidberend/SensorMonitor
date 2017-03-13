package com.tl_ntu.sensormonitor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;


public class SensorManagement implements SensorEventListener{

    private SensorManager mSensorManager;
    
    private Sensor accelerometer;
    private Float accelerometerX;
    private Float accelerometerY;
    private Float accelerometerZ;
    
    private Sensor gyroscope;
    private Float gyroscopeX;
    private Float gyroscopeY;
    private Float gyroscopeZ;

    private ArrayList<Integer> requiredSensors;
    

    public SensorManagement(Context context, ArrayList<Integer> requiredSensors){
        this.requiredSensors = requiredSensors;
        
        // Initialize manager
        mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        
        // Initialize & register required sensors
        for(Integer sensorName : requiredSensors){
            switch(sensorName){
                case Sensor.TYPE_ACCELEROMETER: initializeSensor(accelerometer, sensorName);
                                                registerSensor(accelerometer);
                                                break;
                case Sensor.TYPE_GYROSCOPE:     initializeSensor(gyroscope, sensorName);
                                                registerSensor(gyroscope);
                                                break;                    
            }
        }
    }

    private void initializeSensor(Sensor sensor, Integer name){
        sensor = mSensorManager.getDefaultSensor(name);
    }

    public void registerSensor(Sensor sensor){
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void unregisterSensors(){
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerX = event.values[0];
                accelerometerY = event.values[1];
                accelerometerZ = event.values[2];
                break;                        

            case Sensor.TYPE_GYROSCOPE:
                gyroscopeX = event.values[0];
                gyroscopeY = event.values[1];
                gyroscopeZ = event.values[2];
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    //=========================================
    // Get
    //=========================================
    public Float getAccelerometerX() {
        return accelerometerX;
    }

    public Float getAccelerometerY() {
        return accelerometerY;
    }

    public Float getAccelerometerZ() {
        return accelerometerZ;
    }

    public Float getGyroscopeX() {
        return gyroscopeX;
    }

    public Float getGyroscopeY() {
        return gyroscopeY;
    }

    public Float getGyroscopeZ() {
        return gyroscopeZ;
    }
}
