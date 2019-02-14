package com.example.watchsleep;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;


import java.util.ArrayList;

public class SensorReader implements SensorEventListener {
    private static final String TAG = SensorReader.class.getSimpleName();
    private ArrayList<String> accelerometerData = new ArrayList<String>();

    private boolean isMeasuring = true;
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//
//        accelerometerData = new ArrayList<String>();
//        //Bundle args = getArguments();
//
//        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
//        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//
//    }

//    private void initButtons(){
//        startButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                isMeasuring = true;
//            }
//        });
//
//        stopButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                isMeasuring = false;
//            }
//        });
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState){
//        mView = inflater.inflate(R.layout.sensor, container, false);
//
//        mTextTitle = mView.findViewById(R.id.readings_title);
//        // mTextTitle.setText(mSensor.getStringType());
//        mTextValues = mView.findViewById(R.id.text_values);
//
//        startButton = mView.findViewById(R.id.startButton);
//        stopButton = mView.findViewById(R.id.stopButton);
//        initButtons();
//
//        return mView;
//    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "onSensorChanged is triggered");
        if (isMeasuring) {
            Log.d(TAG, "isMeasuring");
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

            Log.d(TAG, "x is " + Float.toString(x));


            // Show this on the watch face
//            mTextValues.setText(
//                    "x = " + Float.toString(x) + "\n" +
//                            "y = " + Float.toString(y) + "\n" +
//                            "z = " + Float.toString(z) + "\n"
//            );
            // MUST INITIALISE IT WITH SOMETHING FIRST, SEE ON CREATE VIEW
            accelerometerData.add(System.currentTimeMillis() + "," + gX + "," + gY + "," + gZ);

            if(accelerometerData.size() == 200) {
                WearActivity.setAccelerometerData(accelerometerData);
                WearActivity wear = new WearActivity();
                wear.sendData();

                accelerometerData.clear();
            }


        }
        else{

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

//    @Override
//    public void onResume(){
//        super.onResume();
//    }
}
