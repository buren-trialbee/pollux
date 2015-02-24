package pollux.trialbee.com.pollux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by dauvid on 2015-02-03.
 */
public class JsInterface implements WebAppInterface {
    //    MainActivity mActivity;
    private MainActivity mActivity;

    /**
     * Instantiate the interface and set the context
     */
    JsInterface(Context context) {
        mActivity = (MainActivity) context;
    }

    /**
     * Show a toast, with text sent from web application
     */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mActivity, toast, Toast.LENGTH_SHORT).show();
    }

    /**
     * Request an image from the device
     */
    @JavascriptInterface
    public void requestImage() {
        mActivity.requestImage();
    }

    @JavascriptInterface
    public String hasSystemFeature(String feature) {
        return mActivity.hasSystemFeature(feature);
    }

    @JavascriptInterface
    public String getApplicationVersion() {
        return mActivity.getAPIVersion();
    }

    @JavascriptInterface
    public String getAPIVersion() {
        return mActivity.getAPIVersion();
    }

    @JavascriptInterface
    public void discoverBluetoothDevices() {
        mActivity.discoverBluetoothDevices();
    }

    @JavascriptInterface
    public String getDeviceInfo() {
        return mActivity.getDeviceInfo();
    }

    @JavascriptInterface
    public void getPairedBluetoothDevices() {
        mActivity.getPairedBluetoothDevices();
    }

    @JavascriptInterface
    public void requestPairBluetoothDevice(String macAddress) {
        mActivity.requestPairBluetoothDevice(macAddress);
    }

    @JavascriptInterface
    public void sleepFiveSecAndLog() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Log.i("JsInterface", "sleepFiveSecAndLog(), Current time: " + System.currentTimeMillis());
    }

    @JavascriptInterface
    public void log() {
        Log.i("JsInterface", "log(), Current time: " + System.currentTimeMillis());
    }
}