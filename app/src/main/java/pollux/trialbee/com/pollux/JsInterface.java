package pollux.trialbee.com.pollux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

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
    JsInterface(Context context)
    {
        mActivity = (MainActivity) context;
    }
    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {
        // The code that's supposed to be here
//        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();

        // Using this function as requestImage instead, until javaScript-function is added
//        requestImage();
        requestOSVersion();
    }
    /**
     * Request an image from the device
     */
    @JavascriptInterface
    public void requestImage() {
        mActivity.requestImage();
    }
    @JavascriptInterface
    public String[] requestDeviceInformation() {
        return null;
    }
    @JavascriptInterface
    public String requestApplicationVersion() {
        return null;
    }
    @JavascriptInterface
    public void requestOSVersion() {
        Toast.makeText(mActivity, String.valueOf(Build.VERSION.SDK_INT), Toast.LENGTH_SHORT).show();
    }
}