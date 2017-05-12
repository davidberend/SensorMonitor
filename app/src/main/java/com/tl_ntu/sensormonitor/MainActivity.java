package com.tl_ntu.sensormonitor;

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
import android.widget.Toast;

import com.google.gson.Gson;
import com.tl_ntu.sensormonitor.pobjects.Records;

import java.io.File;
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
    TextView textBattery;
    //-----------------------------------------
    Switch switchAccelerometer;
    Switch switchGyroscope;
    Switch switchProximity;
    Switch switchMagnetometer;
    Switch switchBarometer;
    Switch switchAmbientLight;
    Switch switchBattery;
    //-----------------------------------------
    Button buttonRecord;
    //-----------------------------------------

    //=========================================
    // Variables
    //=========================================
    ArrayList<View> eventOwnerTextViews;
    ArrayList<View> eventOwnerSwitches;
    ArrayList<Integer> requiredEventOwners;
    //-----------------------------------------
    DataManagement dataManagement;
    //-----------------------------------------
    DataAccess dataAccess;
    String fileName = "records.ser";
    int filecounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        initializeVariables();

        buttonRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                measureAction();
                //Toast.makeText(MainActivity.this, Integer.toString(i), Toast.LENGTH_SHORT).show();

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
        textBattery          = (TextView) findViewById(R.id.textBattery);
        //-----------------------------------------
        switchAccelerometer    = (Switch) findViewById(R.id.switchAccelerometer);
        switchGyroscope        = (Switch) findViewById(R.id.switchGyroscope);
        switchProximity        = (Switch) findViewById(R.id.switchProximity);
        switchMagnetometer     = (Switch) findViewById(R.id.switchMagnetometer);
        switchBarometer        = (Switch) findViewById(R.id.switchBarometer);
        switchAmbientLight     = (Switch) findViewById(R.id.switchAmbientLight);
        switchBattery          = (Switch) findViewById(R.id.switchBattery);
        //-----------------------------------------
        buttonRecord           = (Button) findViewById(R.id.buttonRecord);
        //-----------------------------------------
    }

    private void initializeVariables(){
        eventOwnerTextViews = new ArrayList<>();
        eventOwnerTextViews.add(textAccelerometer);
        eventOwnerTextViews.add(textGyroscope);
        eventOwnerTextViews.add(textProximity);
        eventOwnerTextViews.add(textMagnetometer);
        eventOwnerTextViews.add(textBarometer);
        eventOwnerTextViews.add(textAmbientLight);
        eventOwnerTextViews.add(textBattery);

        eventOwnerSwitches = new ArrayList<>();
        eventOwnerSwitches.add(switchAccelerometer);
        eventOwnerSwitches.add(switchGyroscope);
        eventOwnerSwitches.add(switchProximity);
        eventOwnerSwitches.add(switchMagnetometer);
        eventOwnerSwitches.add(switchBarometer);
        eventOwnerSwitches.add(switchAmbientLight);
        eventOwnerSwitches.add(switchBattery);

        requiredEventOwners = new ArrayList<>();

        dataManagement = new DataManagement(this);

        dataAccess = new DataAccess(this);

        filecounter = 0;
    }

    //=========================================
    // Measure process
    //=========================================
    private void measureAction(){
        if(buttonRecord.getText() == getResources().getString(R.string.recordButtonStart)){

            buttonRecord.setText(getResources().getString(R.string.recordButtonStop));
            disableComponents();
            getRequiredSensors();
            dataManagement.read(requiredEventOwners, fileName);
        }
        else {
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
        dataAccess.saveInExternalStorage(time + filecounter + ".txt" , jRecord);
        filecounter += 1;

    }

    private void demoDelete(){
        File tmpFile = this.getApplicationContext().getFileStreamPath(fileName);
        tmpFile.delete();
    }

    //=========================================
    // Measure utils
    //=========================================
    private void disableComponents(){
        for(View s : eventOwnerSwitches){
            s.setEnabled(false);
        }

        // color text bg light grey
        for(View t : eventOwnerTextViews){
            t.setBackgroundColor( getResources().getColor(R.color.colorDisabled));
        }
    }

    private void enableComponents(){
        for(View s : eventOwnerSwitches){
            s.setEnabled(true);
        }

        // color text bg white
        for(View t : eventOwnerTextViews){
            t.setBackgroundColor( getResources().getColor(R.color.colorEnabled));
        }
    }

    private void getRequiredSensors(){

        // drop all old data
        requiredEventOwners.clear();

        if (switchAccelerometer.isChecked())
            requiredEventOwners.add(Sensor.TYPE_ACCELEROMETER);

        if(switchGyroscope.isChecked())
            requiredEventOwners.add(Sensor.TYPE_GYROSCOPE);

        if(switchProximity.isChecked())
            requiredEventOwners.add(Sensor.TYPE_PROXIMITY);

        if(switchMagnetometer.isChecked())
            requiredEventOwners.add(Sensor.TYPE_MAGNETIC_FIELD);

        if(switchBarometer.isChecked())
            requiredEventOwners.add(Sensor.TYPE_PRESSURE);

        if(switchAmbientLight.isChecked())
            requiredEventOwners.add(Sensor.TYPE_LIGHT);

        if(switchBattery.isChecked())
            requiredEventOwners.add(Constants.TYPE_BATTERY);

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
