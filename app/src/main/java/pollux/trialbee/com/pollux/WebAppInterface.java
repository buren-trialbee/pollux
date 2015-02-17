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

    @JavascriptInterface
    public String[] requestDeviceInformation();

    @JavascriptInterface
    public String requestApplicationVersion();

    @JavascriptInterface
    public void requestOSVersion();
}
