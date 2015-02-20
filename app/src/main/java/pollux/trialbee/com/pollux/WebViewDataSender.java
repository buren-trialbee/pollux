package pollux.trialbee.com.pollux;

import android.webkit.WebView;

/**
 * Created by dauvid on 2015-02-20.
 */
public class WebViewDataSender {

    public synchronized void sendData(String str, WebView webview){
        webview.loadUrl("javascript:foundBluetoothDevices('" + str + "')");
    }
}
