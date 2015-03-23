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

        // Create callback for the intent
        ImagePickCallback imagePickCallback = new ImagePickCallback(this, context, callback);

        // Send intent to mainActivity for processing
        mActivity.processIntent(imageIntent, imagePickCallback);
    }

    public void getGeolocation(String callback) {

    }

    public void processCallback(String callback, String argument) {
        webViewDataSender.sendData(callback, argument);
    }
}
