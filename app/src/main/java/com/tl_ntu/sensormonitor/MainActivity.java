package com.tl_ntu.sensormonitor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    //=========================================
    // Components
    //=========================================
    TextView textAccelerometer;
    TextView textGyroscope;
    TextView textProximity;
    TextView textMagnetometer;
    TextView textBarometer;
    TextView textAmbientLight;
    TextView textGPS;
    //-----------------------------------------
    Switch switchAccelerometer;
    Switch switchGyroscope;
    Switch switchProximity;
    Switch switchMagnetometer;
    Switch switchBarometer;
    Switch switchAmbientLight;
    Switch switchGPS;
    //-----------------------------------------
    Button buttonRecord;

    // Test coordinates components
    TextView textAccelerometerX;
    TextView textAccelerometerY;
    TextView textAccelerometerZ;
    TextView textGyroscopeX;
    TextView textGyroscopeY;
    TextView textGyroscopeZ;

    //=========================================
    // Variables
    //=========================================
    ArrayList<View> sensorTextViews;
    ArrayList<View> sensorSwitches;
    ArrayList<Sensor> checkedSensors;
    //-----------------------------------------
    SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor gyroscope;
    //-----------------------------------------
    DataManagement dataManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        initializeVariables();

        setComponentListeners();
        setSensorListeners();
    }

    //=========================================
    // Class Management
    //=========================================
    private void initializeComponents(){

        textAccelerometer    = (TextView) findViewById(R.id.textAccelerometer);
        textGyroscope        = (TextView) findViewById(R.id.textGyroscope);
        textProximity        = (TextView) findViewById(R.id.textProximity);
        textMagnetometer     = (TextView) findViewById(R.id.textMagnetometer);
        textBarometer        = (TextView) findViewById(R.id.textBarometer);
        textAmbientLight     = (TextView) findViewById(R.id.textAmbientLight);
        textGPS              = (TextView) findViewById(R.id.textGPS);
        //-----------------------------------------
        switchAccelerometer    = (Switch) findViewById(R.id.switchAccelerometer);
        switchGyroscope        = (Switch) findViewById(R.id.switchGyroscope);
        switchProximity        = (Switch) findViewById(R.id.switchProximity);
        switchMagnetometer     = (Switch) findViewById(R.id.switchMagnetometer);
        switchBarometer        = (Switch) findViewById(R.id.switchBarometer);
        switchAmbientLight     = (Switch) findViewById(R.id.switchAmbientLight);
        switchGPS              = (Switch) findViewById(R.id.switchGPS);
        //-----------------------------------------
        buttonRecord           = (Button) findViewById(R.id.buttonRecord);
        //-----------------------------------------
        textAccelerometerX    = (TextView) findViewById(R.id.textAccelerometerX);
        textAccelerometerY    = (TextView) findViewById(R.id.textAccelerometerY);
        textAccelerometerZ    = (TextView) findViewById(R.id.textAccelerometerZ);
        textGyroscopeX        = (TextView) findViewById(R.id.textGyroscopeX);
        textGyroscopeY        = (TextView) findViewById(R.id.textGyroscopeY);
        textGyroscopeZ        = (TextView) findViewById(R.id.textGyroscopeZ);
    }

    private void initializeVariables(){
        sensorTextViews = new ArrayList<>();
        sensorTextViews.add(textAccelerometer);
        sensorTextViews.add(textGyroscope);
        sensorTextViews.add(textProximity);
        sensorTextViews.add(textMagnetometer);
        sensorTextViews.add(textBarometer);
        sensorTextViews.add(textAmbientLight);
        sensorTextViews.add(textGPS);

        sensorSwitches = new ArrayList<>();
        sensorSwitches.add(switchAccelerometer);
        sensorSwitches.add(switchGyroscope);
        sensorSwitches.add(switchProximity);
        sensorSwitches.add(switchMagnetometer);
        sensorSwitches.add(switchBarometer);
        sensorSwitches.add(switchAmbientLight);
        sensorSwitches.add(switchGPS);

        checkedSensors = new ArrayList<>();

        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        accelerometer  = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope      = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        dataManagement = new DataManagement(checkedSensors);
    }

    private void setComponentListeners(){
        buttonRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(buttonRecord.getText() == getResources().getString(R.string.recordButtonStart)){

                    buttonRecord.setText(getResources().getString(R.string.recordButtonStop));

                    // deactivate components
                    for(View s : sensorSwitches){
                        s.setEnabled(false);
                    }

                    // color text light grey
                    for(View t : sensorTextViews){
                        t.setBackgroundColor( getResources().getColor(R.color.colorDisabled));
                    }

                    checkedSensors = getCheckedSensors();
                    setSensorListeners(checkedSensors);
                    dataManagement.read();

                }
                else {

                    dataManagement.save();

                    // color text standard grey
                    for(View t : sensorTextViews){
                        t.setBackgroundColor( getResources().getColor(R.color.colorEnabled));
                    }

                    // activate components
                    for(View s : sensorSwitches){
                        s.setEnabled(true);
                    }

                    buttonRecord.setText(getResources().getString(R.string.recordButtonStart));
                }
            }
        });
    }



    private ArrayList<Sensor> getCheckedSensors(){
        ArrayList<Sensor> checkedSensors = new ArrayList<>();
        if (switchAccelerometer.isChecked() == true)
            checkedSensors.add(accelerometer);

        if(switchGyroscope.isChecked() == true)
            checkedSensors.add(gyroscope);

        return checkedSensors;
    }

    //=========================================
    // Sensor Management
    //=========================================

    // Activate required sensors
    private void setSensorListeners(ArrayList<Sensor> sensors){
        for(Sensor s : sensors){
            mSensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        switch(event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                textAccelerometerX.setText(Float.toString(event.values[0]));
                textAccelerometerY.setText(Float.toString(event.values[1]));
                textAccelerometerZ.setText(Float.toString(event.values[2]));
                break;

            case Sensor.TYPE_GYROSCOPE:
                textGyroscopeX.setText(Float.toString(event.values[0]));
                textGyroscopeY.setText(Float.toString(event.values[1]));
                textGyroscopeZ.setText(Float.toString(event.values[2]));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
