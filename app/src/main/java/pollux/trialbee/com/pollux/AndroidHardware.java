package pollux.trialbee.com.pollux;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


/**
 * Created by dauvid on 2015-02-17.
 */
public class AndroidHardware implements HardwareInterface {
    public File photoFile;
    private Context context;
    private BluetoothAdapter mBluetoothAdapter;

    public AndroidHardware(Context context) {
        this.context = context;
        // Initialize member variable for default bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    public void requestImage(int requestCode) {
        // Create the intent for capturing an image
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            String fileName = "tempPhoto";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            try {
                photoFile = File.createTempFile(fileName, ".jpg", storageDir);
//                Toast.makeText(mContext, photoFile.toString(), Toast.LENGTH_SHORT).show();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));

                // Start camera activity and store resulting image in external storage
                ((Activity) context).startActivityForResult(takePictureIntent, requestCode);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }

    public String getImageBase64() {
        // Get photo file as bitMap
        Bitmap bm = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

        // Convert bitmap to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public Boolean hasSystemFeature(String feature) {
        switch (feature) {
            case "camera":
                return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
            case "accelerometer":
                return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);
            case "bluetooth":
                return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);
            default:
                return null;
        }
    }

    public String getAPIVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    public String getDeviceInfo() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("camera", context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY));
        jsonObject.put("bluetooth", context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH));
        jsonObject.put("accelerometer", context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER));
        return jsonObject.toString();
    }

    public boolean isBluetoothActivated() {
        return mBluetoothAdapter.isEnabled();
    }

    public void requestStartBluetooth(int requestCode) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        ((Activity) context).startActivityForResult(enableBtIntent, requestCode);
    }

    @Override
    public HashMap<String, String> getPairedBluetoothDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        HashMap<String, String> pairedDevicesMap = new HashMap<String, String>();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesMap.put(device.getAddress(), device.getName());
            }
        }
        return pairedDevicesMap;
    }

    public void discoverBluetoothDevices() {
        mBluetoothAdapter.startDiscovery();
    }

    public void requestPairBluetoothDevice(String macAddress) {
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);
        device.createBond();
    }
}
