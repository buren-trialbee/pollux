package pollux.trialbee.com.pollux;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends ActionBarActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "MainActivity";
    private JsInterface jsInterface;

    private File photoFile;

    private HardwareInterface hw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createWebView();
        hw = new AndroidHardware();
    }

    private void createWebView(){
        // Set webview client and webchrome client
        WebView webView = (WebView) findViewById(R.id.webView);
//        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        // Enable javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Initialize webView with a zoomed out view (to get room for image)
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        // Add hardware interface
        hw = new AndroidHardware();

        // Add javascript interface
        jsInterface = new JsInterface(this);
        webView.addJavascriptInterface(jsInterface, "Android");


        // Load pollux server page on "http://pollux-server.heroku.com"
        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl("http://pollux-server.heroku.com");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically h  ndle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Call on javascript function uploadPicture() in webView
     *
     * @param view //
     */
    public void uploadPicture(View view) {
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("javascript:showAndroidToast(\"Hej\")");
        //jsInterface.showToast("hej");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If a picture was taken and saved due to a request from webView
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            String image = hw.getImage();
            WebView wv = (WebView) findViewById(R.id.webView);
            wv.loadUrl("javascript:addImgBase64(\"" + image + "\")");
        }
    }


    public void requestImage() {
        hw.requestImage(this, REQUEST_IMAGE_CAPTURE);
        Log.i(TAG, "Image request to hw sent");
    }

}
