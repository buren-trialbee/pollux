package pollux.trialbee.com.pollux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
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
public class JsInterface {
    //    MainActivity mActivity;
    private Bridge bridge;
    private static final String TAG = "JsInterface";

    /**
     * Instantiate the interface and set the context
     */
    JsInterface(Bridge bridge) {
        this.bridge = bridge;
    }

    /**
     * Show a toast, with text sent from web application
     */
    @JavascriptInterface
    public void showToast(String toast) {
        bridge.showToast(toast);

    }

    @JavascriptInterface
    public void requestCamera(String callback) {
        Log.d(TAG, "requestImage");
        bridge.requestCamera(callback);
    }

    /**
     * Request an image from the device
     */
    @JavascriptInterface
    public void requestImage(String callback) {
        Log.d(TAG, "requestImage");
        bridge.requestImage(callback);
    }

    @JavascriptInterface
    public String getDeviceInfo() {
        return bridge.getDeviceInfo();
    }


    @JavascriptInterface
    public void getGeoLocation(String callback) {

    }
//    @JavascriptInterface
//    public String hasSystemFeature(String feature) {
//        return mActivity.hasSystemFeature(feature);
//    }
//
//    @JavascriptInterface
//    public String getApplicationVersion() {
//        return mActivity.getAPIVersion();
//    }
//
//    @JavascriptInterface
//    public String getAPIVersion() {
//        return mActivity.getAPIVersion();
//    }
//
    @JavascriptInterface
    public void discoverBluetoothDevices() {
        bridge.discoverBluetoothDevices();
    }


//
//    @JavascriptInterface
//    public void getPairedBluetoothDevices() {
//        mActivity.getPairedBluetoothDevices();
//    }
//
//    @JavascriptInterface
//    public void requestPairBluetoothDevice(String macAddress) {
//        mActivity.requestPairBluetoothDevice(macAddress);
//    }
//
//    @JavascriptInterface
//    public void sleepFiveSecAndLog() {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @JavascriptInterface
//    public void log() {
//        Log.i("JsInterface", "log(), Current time: " + System.currentTimeMillis());
//    }
}