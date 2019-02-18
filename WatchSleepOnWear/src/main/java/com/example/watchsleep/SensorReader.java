package com.example.watchsleep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;


import java.util.ArrayList;


public class SensorReader implements SensorEventListener {
    // https://stackoverflow.com/questions/32227049/pass-context-from-activity
    //private Context mContext;

//    public SensorReader(Context context) {
//        mContext = context;
//    }

    // https://stackoverflow.com/questions/7836415/call-a-public-method-in-the-activity-class-from-another-class#comment9554272_7836465
    private WearActivity mFunc;

    public SensorReader(WearActivity func) {
        mFunc = func;
    }

    private static final String TAG = SensorReader.class.getSimpleName();
    private ArrayList<String> accelerometerData = new ArrayList<String>();



    //Intent intent = new Intent(mContext, WearActivity.sendData(accelerometerData));

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Log.d(TAG, "onSensorChanged is triggered");

        // If sensor is unreliable, then just return
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            return;
        }

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // Calculating something
        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;

        // Log.d(TAG, "x is " + Float.toString(x));


        // Show this on the watch face
//            mTextValues.setText(
//                    "x = " + Float.toString(x) + "\n" +
//                            "y = " + Float.toString(y) + "\n" +
//                            "z = " + Float.toString(z) + "\n"
//            );

        accelerometerData.add(System.currentTimeMillis() + "," + gX + "," + gY + "," + gZ);

        if(accelerometerData.size() == 200) {
            mFunc.sendData(accelerometerData);

            accelerometerData.clear();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
