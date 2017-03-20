package com.tl_ntu.sensormonitor;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.tl_ntu.sensormonitor.pobjects.Records;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import static android.content.ContentValues.TAG;

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

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void saveInExternalStorage(String fileName, String data){
        final File path =
                Environment.getExternalStoragePublicDirectory
                        (
                                //Environment.DIRECTORY_PICTURES
                                Environment.DIRECTORY_DOWNLOADS + "/records/"
                        );

        // Make sure the path directory exists.
        if(!path.exists())
        {
            // Make it, if it doesn't exit
            path.mkdirs();
        }

        File file = new File(path, fileName);

        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
