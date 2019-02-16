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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

public class WearActivity extends WearableActivity {
    private static final String TAG = WearActivity.class.getSimpleName();

    private static final String COUNT_KEY = "com.example.watchsleep.count";
    private DataClient mDataClient; // = Wearable.getDataClient(this);

    private GoogleApiClient mGoogleApiClient;


    private Button sleepButton;
    private Button wakeButton;
    private boolean isMeasuring;
    private SensorReader mSensorReader; // SensorEventListener
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;


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
        // https://developer.android.com/training/wearables/data-layer/accessing
//        mDataClient = Wearable.getDataClient(this);
//        if (mDataClient == null) {
//            Log.d(TAG, "in onCreate, null");
//        }
//        else {
//            Log.d(TAG, "in onCreate, not null");
//        }

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
//                    @Override
//                    public void onConnected(Bundle connectionHint) {
//                        Log.d(TAG, "onConnected: " + connectionHint);
//                        // Now you can use the Data Layer API
//                    }
//
//                    @Override
//                    public void onConnectionSuspended(int cause) {
//                        Log.d(TAG, "onConnectionSuspended: " + cause);
//                    }
//                })
//                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
//                    @Override
//                    public void onConnectionFailed(ConnectionResult result) {
//                        Log.d(TAG, "onConnectionFailed: " + result);
//                    }
//                })
//                // Request access only to the Wearable API
//                .addApi(Wearable.API)
//                .build();
//
//        mGoogleApiClient.connect();


    }


    // Create a data map and put data in it
    public void sendData(ArrayList<String> accelerometerData) {
        Log.d(TAG, "sending data");
        //Log.d(TAG, "Connected? " + mGoogleApiClient.isConnected());
        mDataClient = Wearable.getDataClient(this);

        if (mDataClient == null) {
            Log.d(TAG, "what is going on");
        }
        else{
            Log.d(TAG, "YAYYY");
        }

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/count"); // create data map
        putDataMapReq.getDataMap().putStringArrayList(COUNT_KEY, accelerometerData); // put data in map

        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        // https://github.com/googlesamples/android-DataLayer/blob/master/Application/src/main/java/com/example/android/wearable/datalayer/MainActivity.java
        //Task<DataItem> putDataTask = mDataClient.putDataItem(putDataReq);
//        putDataTask.addOnSuccessListener(
//                new OnSuccessListener<DataItem>() {
//                    @Override
//                    public void onSuccess(DataItem dataItem) {
//                        Log.d(TAG, "Sending image was successful: " + dataItem);
//                    }
//                });


//            PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
//            Log.d(TAG, pendingResult.toString());


    }

    // make onSensorChanged call this
    // https://developer.android.com/training/wearables/data-layer/data-items
//    public void sendData(ArrayList<String> accelerometerData) {
//        Log.d(TAG, "sending data");
//        Log.d(TAG, accelerometerData.get(0) + " size is " + accelerometerData.size());
//
//
//
//
//    }

//    public void sendData() {
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
//    }




    // https://android.okhelp.cz/onsensorchanged-android-example/
}
