package com.tl_ntu.sensormonitor;

import android.hardware.Sensor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

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
    long pushDown;
    long pushUp;
    //-----------------------------------------
    Button buttonRecord;
    //-----------------------------------------

    //=========================================
    // Variables
    //=========================================
    ArrayList<View> eventOwnerTextViews;
    ArrayList<View> eventOwnerSwitches;
    ArrayList<Integer> requiredEventOwners;
    ArrayList<View> pwButtons;
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
            public void onClick(View v) { measureAction(); }
        });
        setPWButtonOnClickListeners();
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
            showPWButtons();
        }
        else {
            dataManagement.save(fileName);
            enableComponents();
            buttonRecord.setText(getResources().getString(R.string.recordButtonStart));
            hidePWButtons();
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

    private void setPWButtonOnClickListeners(){

        for( View b : pwButtons){

            final Button btn = (Button) b;
            b.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    savePress(event, (String) btn.getText());
                    return false;
                }
            });

        }

    }

    private void showPWButtons(){
        for(View b:pwButtons){
            b.setVisibility(View.VISIBLE);
        }
    }

    private void hidePWButtons() {
        for (View b : pwButtons) {
            b.setVisibility(View.INVISIBLE);
        }
    }

    public void savePress(MotionEvent event, String buttonLabel){

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pushDown = System.currentTimeMillis();
        }
        else if (event.getAction() == MotionEvent.ACTION_UP){
            pushUp = System.currentTimeMillis();
            dataManagement.addPress(buttonLabel, pushDown, pushUp);
        }

    }
}
