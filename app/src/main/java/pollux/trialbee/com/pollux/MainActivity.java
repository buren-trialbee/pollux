package pollux.trialbee.com.pollux;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    // Request codes
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private WebViewDataSender webViewDataSender;
    // Log tag
    private static final String TAG = "MainActivity";
    // Private member fields
    private File photoFile;
    private HashMap<String, String> bluetoothDevices;
    private HardwareInterface hw;

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {  // When discovery finds a device
//                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!bluetoothDevices.containsKey(device.getAddress())) {
                    String deviceName = device.getName() == null ? "Unnamed" : device.getName();

                    bluetoothDevices.put(device.getAddress(), deviceName);
                    JSONObject newBluetoothDevice = new JSONObject();
                    try {
//                        newBluetoothDevice.put(device.getAddress(), deviceName);

                        newBluetoothDevice.put(device.getAddress(), deviceName);
                        Log.i(TAG, "device name is: " + deviceName);
                        Log.i(TAG, "device adress is: " + device.getAddress());
                        Log.i(TAG, "Sending string: " + newBluetoothDevice.toString());
                        webViewDataSender.sendData("foundBluetoothDevices", newBluetoothDevice.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i(TAG, device.getAddress() + " " + device.getName());
                    }
                }
//                Log.i(TAG, device.getName() + " " + device.getAddress());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webViewDataSender = new WebViewDataSender(this);

        hw = new AndroidHardware(this);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically h  ndle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Call on javascript function uploadPicture() in webView
     *
     * @param view //
     */
    public void uploadPicture(View view) {
        requestImage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) { // If a picture was taken and saved due to a request from webView
            sendImageToWebview();
        } else if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            hw.discoverBluetoothDevices();
        }
    }

    private void sendImageToWebview() {
        String image = hw.getImageBase64();
        webViewDataSender.sendData("addImgBase64", image);
    }

    public void requestImage() {
        hw.requestImage(REQUEST_IMAGE_CAPTURE);
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
        Log.i(TAG, "Paired devices:");
        JSONObject pairedBluetoothDevice = new JSONObject();
        for (String address : pairedBluetoothDevices.keySet()) {
            try {
                pairedBluetoothDevice.put(address, pairedBluetoothDevices.get(address));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i(TAG, address + " " + pairedBluetoothDevices.get(address));
            }
        }
        webViewDataSender.sendData("showPairedBluetoothDevices", pairedBluetoothDevice.toString());
    }

    public void discoverBluetoothDevices() {
        bluetoothDevices = new HashMap<String, String>();
        if (!hw.isBluetoothActivated()) {
            hw.requestStartBluetooth(REQUEST_ENABLE_BT);
        } else {
            hw.discoverBluetoothDevices();
        }
    }
    
}