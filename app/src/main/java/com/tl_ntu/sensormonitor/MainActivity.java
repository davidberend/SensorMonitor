package com.tl_ntu.sensormonitor;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
    TextView textRotation;
    //-----------------------------------------
    Switch switchAccelerometer;
    Switch switchGyroscope;
    Switch switchProximity;
    Switch switchMagnetometer;
    Switch switchBarometer;
    Switch switchAmbientLight;
    Switch switchBattery;
    Switch switchRotation;
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

    // PERMISSION HANDLING
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);


        initializeComponents();
        initializeVariables();

        buttonRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { measureAction(); }
        });
        setPWButtonOnClickListeners();
        hidePWButtons();
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
        textRotation         = (TextView) findViewById(R.id.textRotation);
        //-----------------------------------------
        switchAccelerometer  = (Switch) findViewById(R.id.switchAccelerometer);
        switchGyroscope      = (Switch) findViewById(R.id.switchGyroscope);
        switchProximity      = (Switch) findViewById(R.id.switchProximity);
        switchMagnetometer   = (Switch) findViewById(R.id.switchMagnetometer);
        switchBarometer      = (Switch) findViewById(R.id.switchBarometer);
        switchAmbientLight   = (Switch) findViewById(R.id.switchAmbientLight);
        switchBattery        = (Switch) findViewById(R.id.switchBattery);
        switchRotation       = (Switch) findViewById(R.id.switchRotation);
        //-----------------------------------------
        button1              = (Button) findViewById(R.id.button1);
        button2              = (Button) findViewById(R.id.button2);
        button3              = (Button) findViewById(R.id.button3);
        button4              = (Button) findViewById(R.id.button4);
        button5              = (Button) findViewById(R.id.button5);
        button6              = (Button) findViewById(R.id.button6);
        button7              = (Button) findViewById(R.id.button7);
        button8              = (Button) findViewById(R.id.button8);
        button9              = (Button) findViewById(R.id.button9);
        button0              = (Button) findViewById(R.id.button0);
        //-----------------------------------------
        buttonRecord         = (Button) findViewById(R.id.buttonRecord);
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
        eventOwnerTextViews.add(textRotation);

        eventOwnerSwitches = new ArrayList<>();
        eventOwnerSwitches.add(switchAccelerometer);
        eventOwnerSwitches.add(switchGyroscope);
        eventOwnerSwitches.add(switchProximity);
        eventOwnerSwitches.add(switchMagnetometer);
        eventOwnerSwitches.add(switchBarometer);
        eventOwnerSwitches.add(switchAmbientLight);
        eventOwnerSwitches.add(switchBattery);
        eventOwnerSwitches.add(switchRotation);

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
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                proceedAfterPermission();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();


                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }
    private void proceedAfterPermission() {
        //We've got the permission, now we can proceed further
        Toast.makeText(getBaseContext(), "We got the Storage Permission", Toast.LENGTH_LONG).show();
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

    //=========================================
    // App utils
    //=========================================

    private void demoAction(){

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE,false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
            }


            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE,true);
            editor.commit();


        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }

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

        if(switchRotation.isChecked())
            requiredEventOwners.add(Sensor.TYPE_ROTATION_VECTOR);
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

    int cnt = 0;

    private void setPWButtonOnClickListeners(){

        for( View b : pwButtons){

            final Button btn = (Button) b;
            b.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        pushDown = System.currentTimeMillis();
                        cnt++;
                        return true;
                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP) {
                        pushUp = System.currentTimeMillis();
                        cnt++;
                        dataManagement.addPress((String) btn.getText(), pushDown, pushUp);
                        return true;
                    }
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

}
