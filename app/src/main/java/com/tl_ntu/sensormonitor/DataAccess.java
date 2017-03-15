package com.tl_ntu.sensormonitor;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataAccess {

    Context context;

    public DataAccess(Context context){

    }

    public String loadFileFromStorage(String fileName){
        // Check if file already exists
        File tmpFile = context.getApplicationContext().getFileStreamPath(fileName);
        FileOutputStream outputStream;
        if (!tmpFile.exists()){
            Toast.makeText(context, "No record yet", Toast.LENGTH_LONG);
            return "No record yet";
        }
        try{
            FileInputStream inStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder records = new StringBuilder();
            String oneLine;
            while((oneLine = bufferedReader.readLine()) != null){
                records.append(oneLine);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inStream.close();

            return records.toString();

        } catch (IOException e){
            e.printStackTrace();
        }

        return "something failed";
    }

}
