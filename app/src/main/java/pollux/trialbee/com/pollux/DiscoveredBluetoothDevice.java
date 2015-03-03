package pollux.trialbee.com.pollux;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by philip on 2015-03-03.
 */
public class DiscoveredBluetoothDevice extends BroadcastReceiver{
    private static final String TAG = "DiscoveredBluetoothDevice";
    private Bridge bridge;
    private IntentFilter filter;

    public DiscoveredBluetoothDevice(Bridge bridge, Context context){
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.bridge = bridge;
    }

    public IntentFilter getIntentFilter(){
        return filter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {  // When discovery finds a device
//                // Get the BluetoothDevice object from the Intent
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String deviceName = device.getName() == null ? "Unnamed" : device.getName();
            JSONObject newBluetoothDevice = new JSONObject();
            try {
                newBluetoothDevice.put(device.getAddress(), deviceName);
                bridge.foundBluetoothDevice(newBluetoothDevice.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, device.getAddress() + " " + device.getName());
            }
        }
    }
}
