package com.tl_ntu.sensormonitor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;

//Branch test
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

    private Sensor proximity;
    private Float proximityX;

    private Sensor magnetometer;
    private Float magnetometerX;
    private Float magnetometerY;
    private Float magnetometerZ;


    private Sensor barometer;
    private Float barometerX;

    private Sensor ambientLight;
    private Float ambientLightX;

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

        proximity  = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        proximityX = 0.0f;
        
        magnetometer  = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        magnetometerX = 0.0f;
        magnetometerY = 0.0f;
        magnetometerZ = 0.0f;

        barometer = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        barometerX = 0.0f;

        ambientLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        ambientLightX = 0.0f;
    }

    public void registerSensor(Sensor sensor){
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
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
                case Sensor.TYPE_PROXIMITY:
                    registerSensor(proximity);
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    registerSensor(magnetometer);
                    break;
                case Sensor.TYPE_PRESSURE:
                    registerSensor(barometer);
                    break;
                case Sensor.TYPE_LIGHT:
                    registerSensor(ambientLight);
                    break;
            }
        }
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
                sensorListener.onValueChange(event, Sensor.TYPE_ACCELEROMETER);
                break;                        

            case Sensor.TYPE_GYROSCOPE:
                gyroscopeX = event.values[0];
                gyroscopeY = event.values[1];
                gyroscopeZ = event.values[2];
                sensorListener.onValueChange(event, Sensor.TYPE_GYROSCOPE);
                break;

            case Sensor.TYPE_PROXIMITY:
                proximityX = event.values[0];
                sensorListener.onValueChange(event, Sensor.TYPE_PROXIMITY);
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                magnetometerX = event.values[0];
                magnetometerY = event.values[1];
                magnetometerZ = event.values[2];
                sensorListener.onValueChange(event, Sensor.TYPE_MAGNETIC_FIELD);
                break;

            case Sensor.TYPE_PRESSURE:
                barometerX = event.values[0];
                sensorListener.onValueChange(event, Sensor.TYPE_PRESSURE);
                break;

            case Sensor.TYPE_LIGHT:
                ambientLightX = event.values[0];
                sensorListener.onValueChange(event, Sensor.TYPE_LIGHT);
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

    public Float getProximityX() {
        return proximityX;
    }

    public Float getMagnetometerX() {
        return magnetometerX;
    }

    public Float getMagnetometerY() {
        return magnetometerY;
    }

    public Float getMagnetometerZ() {
        return magnetometerZ;
    }

    public Float getBarometerX() {
        return barometerX;
    }

    public Float getAmbientLightX() {
        return ambientLightX;
    }
}
