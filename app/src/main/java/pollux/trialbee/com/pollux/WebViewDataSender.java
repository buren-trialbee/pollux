package pollux.trialbee.com.pollux;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.content.DialogInterface;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by dauvid on 2015-02-20.
 */
public class WebViewDataSender {
    private WebView webView;
    private final Context context;

    public WebViewDataSender(Context c) {
        webView = (WebView) ((Activity) c).findViewById(R.id.webView);
        this.context = c;

        webView.setWebViewClient(new WebViewClient());

        // Enable javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Enable remote debugging if OS version is high enough
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        // Add javascript interface
        webView.addJavascriptInterface(new JsInterface(context), "Android");

        // Load pollux server page on "http://pollux-server.heroku.com"
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(context.getString(R.string.webpage_url));
            }
        });
    }

    public void sendData(final String javascriptFunction, final String arg) {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:" + javascriptFunction + "('" + arg + "')");
            }
        });
    }

    public void addImageBase64(String base64) {
        sendData("addImgBase64", base64);
    }

    public void overload() {
        webView.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public void sendPairedBluetoothDevices(String pairedBluetoothDevices) {
        sendData("showPairedBluetoothDevices", pairedBluetoothDevices);
    }

    public void sendFoundBluetoothDevices(String foundDevice) {
        sendData("foundBluetoothDevices", foundDevice);
    }
}
