package pollux.trialbee.com.pollux;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by dauvid on 2015-02-17.
 */
public interface HardwareInterface {
    public void requestImage(int requestCode);

    public String getImageBase64();

    public String getAPIVersion();

    public Boolean hasSystemFeature(String feature);

    public String getDeviceInfo() throws JSONException;

    public void discoverBluetoothDevices();

    public boolean isBluetoothActivated();

    public void requestStartBluetooth(int requestEnableBt);

    public HashMap<String, String> getPairedBluetoothDevices();

    public void requestPairBluetoothDevice(String macAddress);
}
