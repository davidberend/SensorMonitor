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

    private SensorListener sensorListener;

    public SensorManagement(Context context, SensorListener sensorListener){
        mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);

        this.sensorListener = sensorListener;

        accelerometer  = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelerometerX = 0.0f;
        accelerometerY = 0.0f;
        accelerometerZ = 0.0f;
        gyroscope  = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gyroscopeX = 0.0f;
        gyroscopeY = 0.0f;
        gyroscopeY = 0.0f;
    }

    private void registerSensor(Sensor sensor){
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void registerSensors(ArrayList<Integer> requiredSensors){

        for(Integer sensorName : requiredSensors){
            switch(sensorName){
                case Sensor.TYPE_ACCELEROMETER:
                    registerSensor(accelerometer);
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    registerSensor(gyroscope);
                    break;
            }
        }
    }

    public void unregisterSensors(){
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER && event.sensor.getType() != Sensor.TYPE_GYROSCOPE)
            return;

        switch(event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerX = event.values[0];
                accelerometerY = event.values[1];
                accelerometerZ = event.values[2];
                sensorListener.onValueChange(event);
                break;                        

            case Sensor.TYPE_GYROSCOPE:
                gyroscopeX = event.values[0];
                gyroscopeY = event.values[1];
                gyroscopeZ = event.values[2];
                sensorListener.onValueChange(event);
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
