package com.tl_ntu.sensormonitor;

import android.content.Intent;
import android.hardware.Sensor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tl_ntu.sensormonitor.pobjects.Records;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //=========================================
    // Components
    //=========================================
    TextView textAccelerometer;
    TextView textGyroscope;
    TextView textProximity;
    TextView textMagnetometer;
    TextView textBarometer;
    TextView textAmbientLight;
    //-----------------------------------------
    Switch switchAccelerometer;
    Switch switchGyroscope;
    Switch switchProximity;
    Switch switchMagnetometer;
    Switch switchBarometer;
    Switch switchAmbientLight;
    //-----------------------------------------
    Button buttonRecord;
    //-----------------------------------------

    //=========================================
    // Variables
    //=========================================
    ArrayList<View> sensorTextViews;
    ArrayList<View> sensorSwitches;
    ArrayList<Integer> requiredSensors;
    //-----------------------------------------
    DataManagement dataManagement;
    //-----------------------------------------
    DataAccess dataAccess;
    String fileName = "records.ser";

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
        //-----------------------------------------
        switchAccelerometer    = (Switch) findViewById(R.id.switchAccelerometer);
        switchGyroscope        = (Switch) findViewById(R.id.switchGyroscope);
        switchProximity        = (Switch) findViewById(R.id.switchProximity);
        switchMagnetometer     = (Switch) findViewById(R.id.switchMagnetometer);
        switchBarometer         = (Switch) findViewById(R.id.switchBarometer);
        switchAmbientLight     = (Switch) findViewById(R.id.switchAmbientLight);
        //-----------------------------------------
        buttonRecord           = (Button) findViewById(R.id.buttonRecord);
        //-----------------------------------------
    }

    private void initializeVariables(){
        sensorTextViews = new ArrayList<>();
        sensorTextViews.add(textAccelerometer);
        sensorTextViews.add(textGyroscope);
        sensorTextViews.add(textProximity);
        sensorTextViews.add(textMagnetometer);
        sensorTextViews.add(textBarometer);
        sensorTextViews.add(textAmbientLight);

        sensorSwitches = new ArrayList<>();
        sensorSwitches.add(switchAccelerometer);
        sensorSwitches.add(switchGyroscope);
        sensorSwitches.add(switchProximity);
        sensorSwitches.add(switchMagnetometer);
        sensorSwitches.add(switchBarometer);
        sensorSwitches.add(switchAmbientLight);

        requiredSensors = new ArrayList<>();

        dataManagement = new DataManagement(this);

        dataAccess = new DataAccess(this);
    }

    //=========================================
    // Measure process
    //=========================================
    private void measureAction(){
        if(buttonRecord.getText() == getResources().getString(R.string.recordButtonStart)){

            buttonRecord.setText(getResources().getString(R.string.recordButtonStop));
            disableComponents();
            getRequiredSensors();
            dataManagement.read(requiredSensors, fileName);
        }
        else {
            dataManagement.save(fileName);
            enableComponents();
            buttonRecord.setText(getResources().getString(R.string.recordButtonStart));
        }
    }

    private void demoAction(){

        Records records = dataAccess.loadFileFromStorage(fileName);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJRecord = gson.toJson(records);

        Intent intent = new Intent(getBaseContext(), ResultActivity.class);
        intent.putExtra("RECORDS", prettyJRecord);
        startActivity(intent);

    }

    private void demoDelete(){
        File tmpFile = this.getApplicationContext().getFileStreamPath(fileName);
        tmpFile.delete();
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

        if(switchProximity.isChecked())
            requiredSensors.add(Sensor.TYPE_PROXIMITY);

        if(switchMagnetometer.isChecked())
            requiredSensors.add(Sensor.TYPE_MAGNETIC_FIELD);

        if(switchBarometer.isChecked())
            requiredSensors.add(Sensor.TYPE_PRESSURE);

        if(switchAmbientLight.isChecked())
            requiredSensors.add(Sensor.TYPE_LIGHT);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_results:
                demoAction();
                return true;

            case R.id.action_delete:
                demoDelete();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
