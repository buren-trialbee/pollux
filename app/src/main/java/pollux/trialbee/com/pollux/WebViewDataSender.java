package pollux.trialbee.com.pollux;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by dauvid on 2015-02-20.
 */
public class WebViewDataSender {
    private WebView webView;

    public WebViewDataSender(Context context) {
        webView = (WebView) ((Activity) context).findViewById(R.id.webView);
//        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        // Enable javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

//        // Initialize webView with a zoomed out view (to get room for image)
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setUseWideViewPort(true);

        // Add javascript interface
        webView.addJavascriptInterface(new JsInterface(context), "Android");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Load pollux server page on "http://pollux-server.heroku.com"
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("http://pollux-server.heroku.com");
            }
        });
    }

    public synchronized void sendData(final String javascriptFunction, final String arg) {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:" + javascriptFunction + "('" + arg + "')");
            }
        });
    }
}
