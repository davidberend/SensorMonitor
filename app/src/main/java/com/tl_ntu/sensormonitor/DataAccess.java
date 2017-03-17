package com.tl_ntu.sensormonitor;

import android.content.Context;
import com.tl_ntu.sensormonitor.pobjects.Records;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataAccess {

    Context context;

    public DataAccess(Context context){
        this.context = context;
    }

    public Records loadFileFromStorage(String fileName){

        Records records = new Records();

        // Check existence
        File file = context.getApplicationContext().getFileStreamPath(fileName);
        if(file.exists()) {

            // Overwrite new records instance with existing one
            try {
                FileInputStream fis = context.openFileInput(fileName);
                ObjectInputStream is = new ObjectInputStream(fis);
                records = (Records) is.readObject();
                is.close();
                fis.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Return records either way
        return records;
    }

    public void saveFileToStorage(String fileName, Records records){

        try{
            FileOutputStream fos= context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(records);
            os.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
