package pollux.trialbee.com.pollux;

import android.app.ActivityManager;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    // Request codes
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_ENABLE_BT = 2;
    private WebViewDataSender webViewDataSender;

    // Log tag
    private static final String TAG = "MainActivity";

    // Private member fields
    private HardwareInterface hw;
    private Bridge bridge;

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = createBluetoothBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webViewDataSender = new WebViewDataSender(this);

        hw = new AndroidHardware(this);
        bridge = new Bridge(this);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        IntentFilter bondStateFilter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        registerReceiver(mReceiver, bondStateFilter); // Don't forget to unregister during onDestroy
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) { // If a picture was taken and saved due to a request from webView
            sendImageToWebView();
        } else if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            hw.discoverBluetoothDevices();
        }
    }

    /**
     * Call on javascript function uploadPicture() in webView
     *
     * @param view //
     */
    public void uploadPicture(View view) {
        requestImage();
    }


    private void sendImageToWebView() {
        webViewDataSender.addImageBase64(bridge.getImageBase64());
    }

    public void requestImage() {
        bridge.requestImage(REQUEST_IMAGE_CAPTURE);
        Log.i(TAG, "Image request to hw sent");
    }

    public String getAPIVersion() {
        return hw.getAPIVersion();
    }

    public String hasSystemFeature(String feature) {
        return String.valueOf(hw.hasSystemFeature(feature));
    }

    public String getDeviceInfo() {
        try {
            return hw.getDeviceInfo();
        } catch (JSONException exc) {
            Log.e(TAG, exc.getMessage());
            return null;
        }
    }

    public void getPairedBluetoothDevices() {
        HashMap<String, String> pairedBluetoothDevices = hw.getPairedBluetoothDevices();
        JSONObject pairedBluetoothDevice = new JSONObject();
        for (String address : pairedBluetoothDevices.keySet()) {
            try {
                pairedBluetoothDevice.put(address, pairedBluetoothDevices.get(address));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i(TAG, address + " " + pairedBluetoothDevices.get(address));
            }
        }
        webViewDataSender.sendPairedBluetoothDevices(pairedBluetoothDevice.toString());
    }

    public void discoverBluetoothDevices() {
        if (!hw.isBluetoothActivated()) {
            hw.requestStartBluetooth(REQUEST_ENABLE_BT);
        } else {
            hw.discoverBluetoothDevices();
        }
    }

    public void requestPairBluetoothDevice(String macAddress) {
        hw.requestPairBluetoothDevice(macAddress);
    }

    public File requestImageFile() {
        return hw.requestImageFile();
    }

    private BroadcastReceiver createBluetoothBroadcastReceiver() {
        return new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {  // When discovery finds a device
//                // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String deviceName = device.getName() == null ? "Unnamed" : device.getName();
                    JSONObject newBluetoothDevice = new JSONObject();
                    try {
                        newBluetoothDevice.put(device.getAddress(), deviceName);
                        webViewDataSender.sendFoundBluetoothDevices(newBluetoothDevice.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i(TAG, device.getAddress() + " " + device.getName());
                    }
                }
            }
        };
    }
}