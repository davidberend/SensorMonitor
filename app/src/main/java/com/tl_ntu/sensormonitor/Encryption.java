package com.tl_ntu.sensormonitor;
import android.content.Context;
import android.os.SystemClock;
import android.widget.Toast;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class Encryption implements Runnable{

    // Variables
    private String iv = "fedcba9876543210";            // Dummy iv (CHANGE IT!)
    private IvParameterSpec ivspec;
    private SecretKeySpec keyspec;
    private Cipher cipher;
    private String SecretKey = "0123456789abcdef";     // Dummy secretKey (CHANGE IT!)
    private Context context;

    // Constructor
    //public Encryption(){


    // init variables
    // }

    public Encryption(Context context) {
        this.context = context;
        ivspec = new IvParameterSpec(iv.getBytes());
        keyspec = new SecretKeySpec(SecretKey.getBytes(), "AES");

        try {

// cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static String byteArrayToHexString(byte[] array) {
        StringBuffer hexString = new StringBuffer();
        for (byte b : array) {
            int intVal = b & 0xff;
            if (intVal < 0x10)
                hexString.append("0");
            hexString.append(Integer.toHexString(intVal));
        }
        return hexString.toString();
    }

    public static byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {

            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(
                        str.substring(i * 2, i * 2 + 2), 16);

            }
            return buffer;
        }
    }

    private static String padString(String source) {
        char paddingChar = 0;
        int size = 16;
        int x = source.length() % size;
        int padLength = size - x;
        for (int i = 0; i < padLength; i++) {
            source += paddingChar;
        }
        return source;
    }

    public byte[] encrypt(String text) throws Exception {
        if (text == null || text.length() == 0)
            throw new Exception("Empty string");

        byte[] encrypted = null;
        try {
// Cipher.ENCRYPT_MODE = Constant for encryption operation mode.
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);

            encrypted = cipher.doFinal(padString(text).getBytes());
        } catch (Exception e) {
            throw new Exception("[encrypt] " + e.getMessage());
        }
        return encrypted;
    }

    public byte[] decrypt(String text) throws Exception {
        if (text == null || text.length() == 0)
            throw new Exception("Empty string");

        byte[] decrypted = null;
        try {
// Cipher.DECRYPT_MODE = Constant for decryption operation mode.
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            decrypted = cipher.doFinal(hexToBytes(text));
        } catch (Exception e) {
            throw new Exception("[decrypt] " + e.getMessage());
        }
        return decrypted;
    }




    // Gets executed as soon as Thread is run --> Put your executable code in here
    @Override
    public void run() {
        SystemClock.sleep(1000);
        //int start = System.currentTimeMillis();
        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        //Encryption mcrypt = new Encryption();
        String dummyStr = "123456";
        String encryptedStr = "";
        String decryptedStr = "";

// Encrypting our dummy String
        try {
            encryptedStr = new String(encrypt(dummyStr));
            // call variables
            Toast.makeText(context, encryptedStr, Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try {
            decryptedStr = new String(decrypt(encryptedStr));
            Toast.makeText(context, decryptedStr, Toast.LENGTH_SHORT).show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        SystemClock.sleep(1000);
    }

    //int stop = System.currentTimeMillis();

}

