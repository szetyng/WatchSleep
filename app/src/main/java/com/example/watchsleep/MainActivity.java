package com.example.watchsleep;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

public class MainActivity extends Activity implements DataClient.OnDataChangedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String COUNT_KEY = "com.example.watchsleep.count";
    private int count = 0;
    private ArrayList<String> output;

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.text_values);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Wearable.getDataClient(this).addListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.getDataClient(this).removeListener(this);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents){
        Log.d(TAG, "onDataChanged(): " + dataEvents);
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                Log.d(TAG, "DataItem TYPE_CHANGED");
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/count") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    updateCount(dataMap.getStringArrayList(COUNT_KEY));
                }
            }
            else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
                Log.d(TAG, "DataItem TYPE_DELETED");

            }
        }
    }

    // Update the count and print on phone
    private void updateCount(ArrayList<String> c ) {
        Log.i(TAG, "UPDATE COUNT " + c);

        String s = c.get(0);

        String time = s.split(",")[0];
        String x = s.split(",")[1];
        String y = s.split(",")[2];
        String z = s.split(",")[3];

        // Show this on the phone
        mTextView.setText(
                "x = " + x + "\n" +
                        "y = " + y + "\n" +
                        "z = " + z + "\n"
        );



    }
}
