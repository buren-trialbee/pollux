package pollux.trialbee.com.pollux;

import android.webkit.JavascriptInterface;

/**
 * Created by dauvid on 2015-02-17.
 */
public interface WebAppInterface {
    @JavascriptInterface
    public void showToast(String toast);

    //Request an image from the device
    @JavascriptInterface
    public void requestImage();

    // Asks android system for hardware functionality.
    // Feature string maps to android feature as defined in class implementing hardware interface
    @JavascriptInterface
    public String hasSystemFeature(String feature);

    // Get current version of the android-application
    @JavascriptInterface
    public String getApplicationVersion();

    // Get current API version unit is running on
    @JavascriptInterface
    public String getAPIVersion();
}
