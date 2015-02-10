package pollux.trialbee.com.pollux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by dauvid on 2015-02-03.
 */
public class JsInterface {
    MainActivity mActivity;


    /**
     * Instantiate the interface and set the context
     */
    JsInterface(Context context) {
        mActivity = (MainActivity) context;
        // Instantiate a camera handler
//        cameraHandler = new CameraHandler(context);
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {
        // The code that's supposed to be here
//        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();

        // Using this function as requestImage instead, until javaScript-function is added
        requestImage();
    }

    @JavascriptInterface
    public void requestImage() {

        mActivity.requestImage();

    }
}




