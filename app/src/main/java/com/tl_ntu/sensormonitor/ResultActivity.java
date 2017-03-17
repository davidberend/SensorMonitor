package com.tl_ntu.sensormonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;


public class ResultActivity extends AppCompatActivity {

    EditText textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get file
        String file = getIntent().getStringExtra("RECORDS");

        // Show file
        textResult = (EditText) findViewById(R.id.textResult);
        textResult.setText(file);
    }


}
