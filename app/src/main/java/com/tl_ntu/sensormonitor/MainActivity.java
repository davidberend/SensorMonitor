package com.tl_ntu.sensormonitor;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tl_ntu.sensormonitor.pobjects.Records;
import java.io.File;
import java.io.FileOutputStream;
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
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button0;
    //-----------------------------------------

    //=========================================
    // Variables
    //=========================================
    ArrayList<View> sensorTextViews;
    ArrayList<View> sensorSwitches;
    ArrayList<View> pwButtons;
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

        hidePWBUttons();

        buttonRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                measureAction();
            }
        });

        setPWButtonActionListeners();
    }

    private void setPWButtonActionListeners(){
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManagement.addPress(button1.getText().toString());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManagement.addPress(button2.getText().toString());
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManagement.addPress(button3.getText().toString());
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManagement.addPress(button4.getText().toString());
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManagement.addPress(button5.getText().toString());
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManagement.addPress(button6.getText().toString());
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManagement.addPress(button7.getText().toString());
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManagement.addPress(button8.getText().toString());
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManagement.addPress(button9.getText().toString());
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManagement.addPress(button0.getText().toString());
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
        button1                = (Button) findViewById(R.id.button1);
        button2                = (Button) findViewById(R.id.button2);
        button3                = (Button) findViewById(R.id.button3);
        button4                = (Button) findViewById(R.id.button4);
        button5                = (Button) findViewById(R.id.button5);
        button6                = (Button) findViewById(R.id.button6);
        button7                = (Button) findViewById(R.id.button7);
        button8                = (Button) findViewById(R.id.button8);
        button9                = (Button) findViewById(R.id.button9);
        button0                = (Button) findViewById(R.id.button0);
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

        pwButtons = new ArrayList<>();
        pwButtons.add(button1);
        pwButtons.add(button2);
        pwButtons.add(button3);
        pwButtons.add(button4);
        pwButtons.add(button5);
        pwButtons.add(button6);
        pwButtons.add(button7);
        pwButtons.add(button8);
        pwButtons.add(button9);
        pwButtons.add(button0);

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
            showPWBUttons();
        }
        else {
            hidePWBUttons();
            dataManagement.save(fileName);
            enableComponents();
            buttonRecord.setText(getResources().getString(R.string.recordButtonStart));
        }
    }

    //=========================================
    // App utils
    //=========================================

    private void demoAction(){

        Records records = dataAccess.loadFileFromStorage(fileName);
        Gson gson = new Gson();
        String jRecord = gson.toJson(records);

        String folder_main = "PACE";

        boolean read = dataAccess.isExternalStorageReadable();
        boolean write = dataAccess.isExternalStorageWritable();

        String time = Float.toString(System.currentTimeMillis());
        dataAccess.saveInExternalStorage(time + ".txt" , jRecord);

        //File f = new File(Environment.getExternalStoragePublicDirectory(
        //        Environment.DIRECTORY_PICTURES), folder_main);
        //if (!f.exists()) {
        //    f.mkdirs();
        //}

        //Intent intent = new Intent(getBaseContext(), ResultActivity.class);
        //intent.putExtra("RECORDS", JRecord);
        //startActivity(intent);

    }

    private void demoDelete(){
        File tmpFile = this.getApplicationContext().getFileStreamPath(fileName);
        tmpFile.delete();
    }

    private void showPWBUttons(){
        for(View b:pwButtons){
            b.setVisibility(View.VISIBLE);
        }
    }

    private void hidePWBUttons() {
        for (View b : pwButtons) {
            b.setVisibility(View.INVISIBLE);
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
