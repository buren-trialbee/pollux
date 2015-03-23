package pollux.trialbee.com.pollux;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    private HashMap<Integer, Callback> callbacks;
    private BroadcastReceiver broadcastReceiver;
    private UniqueInteger uniqueInteger;
    private static final String TAG = "MainActivity";
    private Bridge bridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bridge = new Bridge(this);
        uniqueInteger = new UniqueInteger();
        callbacks = new HashMap<>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "OnActivityResult");
        Callback cb = callbacks.get(requestCode);
        cb.done(requestCode, resultCode, data);
    }

    public void processIntent(Intent intent, Callback callback) {
        Log.d(TAG, "processIntent");
        int requestCode = uniqueInteger.getUniqueInteger();
        callbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

    public void addBroadcastReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        unregisterReceiver(this.broadcastReceiver);
        registerReceiver(broadcastReceiver, intentFilter);
        this.broadcastReceiver = broadcastReceiver;
    }

    public void addBroadcastReciver(DiscoveredBluetoothDevice discoveredBluetoothDevice, IntentFilter intentFilter) {
    }
//    public String getAPIVersion() {
//        return hw.getAPIVersion();
//    }
//
//    public String hasSystemFeature(String feature) {
//        return String.valueOf(hw.hasSystemFeature(feature));
//    }

//    public void getPairedBluetoothDevices() {
//        HashMap<String, String> pairedBluetoothDevices = hw.getPairedBluetoothDevices();
//        JSONObject pairedBluetoothDevice = new JSONObject();
//        for (String address : pairedBluetoothDevices.keySet()) {
//            try {
//                pairedBluetoothDevice.put(address, pairedBluetoothDevices.get(address));
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Log.i(TAG, address + " " + pairedBluetoothDevices.get(address));
//            }
//        }
//        webViewDataSender.sendPairedBluetoothDevices(pairedBluetoothDevice.toString());
//    }

//
//    public void requestPairBluetoothDevice(String macAddress) {
//        hw.requestPairBluetoothDevice(macAddress);
//    }
}