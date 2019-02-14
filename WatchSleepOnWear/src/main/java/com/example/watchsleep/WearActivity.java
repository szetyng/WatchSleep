package com.example.watchsleep;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

public class WearActivity extends WearableActivity {
    private static final String TAG = WearActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;

    private Button sleepButton;
    private Button wakeButton;
    private boolean isMeasuring;
    private SensorReader mSensorReader; // SensorEventListener
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private static ArrayList<String> accelerometerData;

    public static void setAccelerometerData(ArrayList<String> newData) {
        accelerometerData = newData;
    }

    TextView mTextView;
    TextView mTextValues;
    TextView mTextTitle;

    private void initButtons(){
        sleepButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isMeasuring = true;
                //mTextView.setText("You are now in bed!");
                //draw();
                setContentView(R.layout.sensor);

                // Start reading accelerometer data
                // https://android.processing.org/tutorials/sensors/index.html
                mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

                mSensorReader = new SensorReader(); // new listener
                mSensorManager.registerListener(mSensorReader, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

                //sendData();
                // go to ambient mode
            }
        });

        wakeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isMeasuring = false;
                mTextView.setText("You woke up!");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear);
        mTextView = findViewById(R.id.text);
        sleepButton = findViewById(R.id.sleepButton);
        wakeButton = findViewById(R.id.wakeButton);
        isMeasuring = false;

        initButtons();

        // Enables Always-on
        setAmbientEnabled();

        Log.d(TAG, "log is working");

        // initialise API client for sending data to phone here


    }

    // make onSensorChanged call this
    public void sendData() {
        Log.d(TAG, "sending data");
        Log.d(TAG, accelerometerData.get(0) + " size is " + accelerometerData.size());
    }

//    public void sendData()
//    {
//        if(accelerometerData.size() < 200) return;
//
//        Log.i(TAG, "Sending data!");
//        Log.i(TAG, "Connected? " + mGoogleApiClient.isConnected());
//
//        if(mGoogleApiClient.isConnected())
//        {
//            PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/count");
//            putDataMapReq.getDataMap().putStringArrayList("from_wear", accelerometerData);
//
//            PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
//            PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
//            Log.i(TAG, pendingResult.toString());
//        }
//
//        accelerometerData.clear();
//    }




    // https://android.okhelp.cz/onsensorchanged-android-example/
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        if (isMeasuring) {
//            // If sensor is unreliable, then just return
//            if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
//                return;
//            }
//
//            float x = event.values[0];
//            float y = event.values[1];
//            float z = event.values[2];
//
//            // Calculating something
//            float gX = x / SensorManager.GRAVITY_EARTH;
//            float gY = y / SensorManager.GRAVITY_EARTH;
//            float gZ = z / SensorManager.GRAVITY_EARTH;
//
//            Log.d(TAG, "onSensorChanged is triggered, isMeasuring");
//            // Show this on the watch face
//            mTextValues.setText(
//                    "x = " + Float.toString(x) + "\n" +
//                            "y = " + Float.toString(y) + "\n" +
//                            "z = " + Float.toString(z) + "\n"
//            );
//            accelerometerData.add(System.currentTimeMillis() + "," + gX + "," + gY + "," + gZ);
//
//            // TO DO: send data to server
//        }
//        else{
//            Log.d(TAG, "onSensorChange is triggered, !isMeasuring");
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }



}
