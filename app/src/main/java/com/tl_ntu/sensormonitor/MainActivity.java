package com.tl_ntu.sensormonitor;

import android.hardware.Sensor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

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

    //=========================================
    // Variables
    //=========================================
    ArrayList<View> sensorTextViews;
    ArrayList<View> sensorSwitches;
    ArrayList<Integer> requiredSensors;
    //-----------------------------------------
    DataManagement dataManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        initializeVariables();

        buttonRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                measureAction();
            }
        });
    }

    //=========================================
    // Initialization
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

        requiredSensors = new ArrayList<>();

        dataManagement = new DataManagement(this);
    }

    //=========================================
    // Measure process
    //=========================================
    private void measureAction(){
        if(buttonRecord.getText() == getResources().getString(R.string.recordButtonStart)){

            buttonRecord.setText(getResources().getString(R.string.recordButtonStop));
            disableComponents();
            getRequiredSensors();
            dataManagement.read(requiredSensors);

        }
        else {

            dataManagement.save();
            enableComponents();
            buttonRecord.setText(getResources().getString(R.string.recordButtonStart));
        }
    }

    //=========================================
    // Measure utils
    //=========================================
    private void disableComponents(){
        for(View s : sensorSwitches){
            s.setEnabled(false);
        }

        // color text bg light grey
        for(View t : sensorTextViews){
            t.setBackgroundColor( getResources().getColor(R.color.colorDisabled));
        }
    }

    private void enableComponents(){
        for(View s : sensorSwitches){
            s.setEnabled(true);
        }

        // color text bg white
        for(View t : sensorTextViews){
            t.setBackgroundColor( getResources().getColor(R.color.colorEnabled));
        }
    }

    private void getRequiredSensors(){

        // drop all old data
        requiredSensors.clear();

        if (switchAccelerometer.isChecked())
            requiredSensors.add(Sensor.TYPE_ACCELEROMETER);

        if(switchGyroscope.isChecked())
            requiredSensors.add(Sensor.TYPE_GYROSCOPE);
    }
}
