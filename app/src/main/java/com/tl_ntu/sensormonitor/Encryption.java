package com.tl_ntu.sensormonitor;

class Encryption implements Runnable{

    // Constructor
    public Encryption(){

    }

    // Gets executed as soon as Thread is run --> Put your executable code in here
    @Override
    public void run() {
        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);


    }
}
