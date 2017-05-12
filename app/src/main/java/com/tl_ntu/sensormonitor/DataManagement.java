package com.tl_ntu.sensormonitor;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.Sensor;

import java.util.ArrayList;
import java.util.List;

import com.tl_ntu.sensormonitor.pobjects.*;

class DataManagement implements SensorListener{

    // Context from application to save file
    Context context;

    private SensorManagement sensorManagement;

    List<Integer> requiredSensors;

    DataAccess dataAccess;

    private Records records;
    private Record record;

    // Accelerometer
    private boolean accelerometerState;
    com.tl_ntu.sensormonitor.pobjects.EventOwner accelerometer;
    private int accelerometerEventID;
    private List<Event> accelerometerEvent;

    // Gyroscope
    private boolean gyroscopeState;
    com.tl_ntu.sensormonitor.pobjects.EventOwner gyroscope;
    private int gyroscopeEventID;
    private List<Event> gyroscopeEvent;
    
    // Proximity
    private boolean proximityState;
    com.tl_ntu.sensormonitor.pobjects.EventOwner proximity;
    private int proximityEventID;
    private List<Event> proximityEvent;
    
    // Magnetometer
    private boolean magnetometerState;
    com.tl_ntu.sensormonitor.pobjects.EventOwner magnetometer;
    private int magnetometerEventID;
    private List<Event> magnetometerEvent;
    
    // Barometer
    private boolean barometerState;
    com.tl_ntu.sensormonitor.pobjects.EventOwner barometer;
    private int barometerEventID;
    private List<Event> barometerEvent;
    
    // Ambient Light
    private boolean ambientLightState;
    com.tl_ntu.sensormonitor.pobjects.EventOwner ambientLight;
    private int ambientLightEventID;
    private List<Event> ambientLightEvent;

    // Battery
    private boolean batteryState;
    com.tl_ntu.sensormonitor.pobjects.EventOwner battery;
    private int batteryEventID;
    private List<Event> batteryEvent;
    // Battery Utils
    BatteryMeasurement batteryMeasurement;

    public DataManagement(Context context){
        this.context = context;
        sensorManagement = new SensorManagement(context, this);

        requiredSensors = new ArrayList<Integer>();

        accelerometerEvent = new ArrayList<Event>();
        gyroscopeEvent = new ArrayList<Event>();
        proximityEvent = new ArrayList<Event>();
        magnetometerEvent = new ArrayList<Event>();
        barometerEvent = new ArrayList<Event>();
        ambientLightEvent = new ArrayList<Event>();
        batteryEvent = new ArrayList<Event>();

        records = new Records();

        dataAccess = new DataAccess(context);

    }


    public void read(ArrayList<Integer> requiredSensors, String fileName){
        
        // Get relevant sensors
        this.requiredSensors = requiredSensors;
        
        // Prepare 
        enableSensorMeasurements();
        records = dataAccess.loadFileFromStorage(fileName);
        record = new Record();
        records.getRecords().add(record);

        // Add required sensors to record
        if(accelerometerState){
            accelerometer = new com.tl_ntu.sensormonitor.pobjects.EventOwner();
            accelerometer.setName("accelerometer");
            accelerometer.setEventEntries(accelerometerEvent);
            record.getEventOwners().add(accelerometer);
        }
        
        if(gyroscopeState) {
            gyroscope = new com.tl_ntu.sensormonitor.pobjects.EventOwner();
            gyroscope.setName("gyroscope");
            gyroscope.setEventEntries(gyroscopeEvent);
            record.getEventOwners().add(gyroscope);
        }

        if(proximityState) {
            proximity = new com.tl_ntu.sensormonitor.pobjects.EventOwner();
            proximity.setName("proximity");
            proximity.setEventEntries(proximityEvent);
            record.getEventOwners().add(proximity);
        }

        if(magnetometerState) {
            magnetometer = new com.tl_ntu.sensormonitor.pobjects.EventOwner();
            magnetometer.setName("magnetometer");
            magnetometer.setEventEntries(magnetometerEvent);
            record.getEventOwners().add(magnetometer);
        }

        if(barometerState) {
            barometer = new com.tl_ntu.sensormonitor.pobjects.EventOwner();
            barometer.setName("barometer");
            barometer.setEventEntries(barometerEvent);
            record.getEventOwners().add(barometer);
        }

        if(ambientLightState) {
            ambientLight = new com.tl_ntu.sensormonitor.pobjects.EventOwner();
            ambientLight.setName("ambientLight");
            ambientLight.setEventEntries(ambientLightEvent);
            record.getEventOwners().add(ambientLight);
        }

        if(batteryState) {
            battery = new com.tl_ntu.sensormonitor.pobjects.EventOwner();
            battery.setName("battery");
            battery.setEventEntries(batteryEvent);
            record.getEventOwners().add(battery);
        }

        // Start receiving Event
        record.setStart(Long.toString(System.currentTimeMillis()));
        sensorManagement.registerSensors(requiredSensors);

        if(batteryState){
            Thread measure = new Thread(batteryMeasurement = new BatteryMeasurement());
            measure.start();
        }
    }

    public void save(String fileName){
        if(batteryState) unregisterBattery();
        sensorManagement.unregisterSensors();
        disableSensorMeasurements();
        record.setStop(Long.toString(System.currentTimeMillis()));

        dataAccess.saveFileToStorage(fileName, records);

        dropMeasurements();
    }

    private void unregisterBattery(){

        batteryState = false;
        saveBatteryData();
    }

    @Override
    public void onValueChange(SensorEvent sensorEvent, int sensor) {
        if(sensor == Sensor.TYPE_ACCELEROMETER){
            accelerometerEventID += 1;

            Event event = createEvent(accelerometerEventID);

            Value x = createValue("x", sensorManagement.getAccelerometerX());
            Value y = createValue("y", sensorManagement.getAccelerometerY());
            Value z = createValue("z", sensorManagement.getAccelerometerZ());

            event.getValues().add(x);
            event.getValues().add(y);
            event.getValues().add(z);

            accelerometerEvent.add(event);
        }

        if(sensor == Sensor.TYPE_GYROSCOPE){
            gyroscopeEventID += 1;

            Event event = createEvent(gyroscopeEventID);

            Value x = createValue("x", sensorManagement.getGyroscopeX());
            Value y = createValue("y", sensorManagement.getGyroscopeY());
            Value z = createValue("z", sensorManagement.getGyroscopeZ());

            event.getValues().add(x);
            event.getValues().add(y);
            event.getValues().add(z);

            gyroscopeEvent.add(event);
        }

        if(sensor == Sensor.TYPE_PROXIMITY){
            proximityEventID += 1;

            Event event = createEvent(proximityEventID);

            Value x = createValue("x", sensorManagement.getProximityX());

            event.getValues().add(x);

            proximityEvent.add(event);
        }

        if(sensor == Sensor.TYPE_MAGNETIC_FIELD){
            magnetometerEventID += 1;

            Event event = createEvent(magnetometerEventID);

            Value x = createValue("x", sensorManagement.getMagnetometerX());
            Value y = createValue("y", sensorManagement.getMagnetometerY());
            Value z = createValue("z", sensorManagement.getMagnetometerZ());

            event.getValues().add(x);
            event.getValues().add(y);
            event.getValues().add(z);

            magnetometerEvent.add(event);
        }

        if(sensor == Sensor.TYPE_PRESSURE){
            barometerEventID += 1;

            Event event = createEvent(barometerEventID);

            Value x = createValue("x", sensorManagement.getBarometerX());

            event.getValues().add(x);

            barometerEvent.add(event);
        }

        if(sensor == Sensor.TYPE_LIGHT){
            ambientLightEventID += 1;

            Event event = createEvent(ambientLightEventID);

            Value x = createValue("x", sensorManagement.getAmbientLightX());

            event.getValues().add(x);

            ambientLightEvent.add(event);
        }
    }

    private void saveBatteryData(){

        ArrayList<String> rawData  = batteryMeasurement.getRawData();
        ArrayList<Long> rawTimes = batteryMeasurement.getRawTimes();

        ArrayList<Long> longData = new ArrayList<>();
        for ( String event : rawData){
            Long ldata = Long.parseLong(event);
            longData.add(ldata);
        }

        ArrayList<ArrayList<Long>> returnLists = doInsertionSort(rawTimes, longData);

        rawTimes = returnLists.get(0);
        longData = returnLists.get(1);

        for (int i = 0; i < rawData.size(); i++){
            batteryEventID += 1;
            Event event = new Event();
            event.setId(Integer.toString(batteryEventID));
            event.setTime(Long.toString(rawTimes.get(i)));
            Value amp = createValue("amp", Float.parseFloat(Long.toString(longData.get(i))));
            event.getValues().add(amp);
            batteryEvent.add(event);
        }
    }

    public ArrayList<ArrayList<Long>> doInsertionSort(ArrayList<Long> input, ArrayList<Long> followArr){

        ArrayList<ArrayList<Long>> returnLists = new ArrayList<>();

        long temp;
        long tempfol;
        for (int i = 1; i < input.size(); i++) {
            for(int j = i ; j > 0 ; j--){
                if(input.get(j) < input.get(j-1)){
                    temp = input.get(j);
                    tempfol = followArr.get(j);

                    input.set(j, input.get(j-1));
                    followArr.set(j, followArr.get(j-1));

                    input.set(j-1, temp);
                    followArr.set(j-1, tempfol);
                }
            }
        }

        returnLists.add(input);
        returnLists.add(followArr);

        return returnLists;
    }

    private Event createEvent(int eventID){
        Event event = new Event();
        event.setId(Integer.toString(eventID));
        event.setTime(Long.toString(System.currentTimeMillis()));

        return event;
    }

    private Value createValue(String name, Float sensorData){
        Value value = new Value();
        value.setName(name);
        value.setValue(Float.toString(sensorData));

        return value;
    }

    public void addPress(String name, long start, long stop) {
        Press press = new Press();
        press.setLabel(name);
        press.setStart(Long.toString(start));
        press.setStop(Long.toString(stop));

        record.getPresses().add(press);
    }
    //=========================================
    // Measure utils
    //=========================================
    private void enableSensorMeasurements(){

        // Reset Event ID
        accelerometerEventID = 0;
        gyroscopeEventID = 0;
        proximityEventID = 0;
        magnetometerEventID = 0;
        barometerEventID = 0;
        ambientLightEventID = 0;
        batteryEventID = 0;


        if(requiredSensors.contains(Sensor.TYPE_ACCELEROMETER))
            accelerometerState = true;

        if(requiredSensors.contains(Sensor.TYPE_GYROSCOPE))
            gyroscopeState = true;

        if(requiredSensors.contains(Sensor.TYPE_PROXIMITY))
            proximityState= true;

        if(requiredSensors.contains(Sensor.TYPE_MAGNETIC_FIELD))
            magnetometerState = true;

        if(requiredSensors.contains(Sensor.TYPE_PRESSURE))
            barometerState = true;

        if(requiredSensors.contains(Sensor.TYPE_LIGHT))
            ambientLightState = true;

        if(requiredSensors.contains(Constants.TYPE_BATTERY))
            batteryState = true;
    }

    private void disableSensorMeasurements(){
        accelerometerState = false;
        gyroscopeState = false;
        proximityState = false;
        magnetometerState = false;
        barometerState = false;
        ambientLightState = false;
        batteryState = false;
    }

    private void dropMeasurements(){
        accelerometerEvent.clear();
        gyroscopeEvent.clear();
        proximityEvent.clear();
        magnetometerEvent.clear();
        barometerEvent.clear();
        ambientLightEvent.clear();
        batteryEvent.clear();
    }
}
