package pollux.trialbee.com.pollux;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONException;

/**
 * Created by dauvid on 2015-02-26.
 */
public class Bridge {
    private Context context;
    private MainActivity mActivity;
    private WebViewDataSender webViewDataSender;
    private static final String TAG = "Bridge";

    public Bridge(Context context) {
        this.context = context;
        mActivity = (MainActivity) context;
        webViewDataSender = new WebViewDataSender(this, (WebView) mActivity.findViewById(R.id.webView));
    }

    public void requestCamera(String callback) {
        Log.d(TAG, "requestCamera");

        // Create a new intent
        Intent imageIntent = IntentFactory.createCameraIntent(context);

        // Retrieve filepath to photo being captured
        Uri photoFileUri = (Uri) imageIntent.getExtras().get(MediaStore.EXTRA_OUTPUT);

        // Create callback method
        ImageCallback imageCallback = new ImageCallback(this, photoFileUri, callback);

        // Send intent to mainActivity for processing
        mActivity.processIntent(imageIntent, imageCallback);
    }

    public void requestImage(String callback) {
        Log.d(TAG, "requestImage");

        // Create a new intent
        Intent imageIntent = IntentFactory.createImageIntent(context);

        Uri photoFileUri = (Uri) imageIntent.getExtras().get(MediaStore.EXTRA_OUTPUT);
        ImageCallback imageCallback = new ImageCallback(this, photoFileUri, callback);
        mActivity.processIntent(imageIntent, imageCallback);
    }

    public void sendImageBase64(String image) {
        webViewDataSender.addImageBase64(image);
    }

    public void processCallback(String callback, String argument) {
        webViewDataSender.sendData(callback, argument);
    }












    public void showToast(String toast) {
        Toast.makeText(mActivity, toast, Toast.LENGTH_SHORT).show();
    }

    public String getDeviceInfo() {
        try {
            return AndroidHardware.getDeviceInfo(context);
        } catch (JSONException exc) {
            Log.e(TAG, exc.getMessage());
            return null;
        }
    }

    public void discoverBluetoothDevices() {
        if (!AndroidHardware.isBluetoothActivated()) {
            mActivity.processIntent(IntentFactory.createStartBluetoothIntent(), new StartBluetoothCallback(this));
        } else {
            DiscoveredBluetoothDevice discoveredBluetoothDevice = new DiscoveredBluetoothDevice(this, context);
            mActivity.addBroadcastReciver(discoveredBluetoothDevice, discoveredBluetoothDevice.getIntentFilter());

            AndroidHardware.discoverBluetoothDevices();
        }
    }

    public void foundBluetoothDevice(String bluetoothDevice) {
        webViewDataSender.sendFoundBluetoothDevices(bluetoothDevice);
    }
}
