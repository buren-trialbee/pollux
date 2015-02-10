package pollux.trialbee.com.pollux;

import android.view.Menu;

        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.webkit.JavascriptInterface;
        import android.webkit.WebChromeClient;
        import android.webkit.WebSettings;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.EditText;
        import android.widget.TextView;

        import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    private JsInterface jsInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set webview client and webchrome client
        WebView webView = (WebView) findViewById(R.id.webView);
//        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        // Enable javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Add javascript interface
        webView.addJavascriptInterface(new JsInterface(this), "Android");
        jsInterface = new JsInterface(this);

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
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load pollux server page on "http://pollux-server.heroku.com"
        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl("http://pollux-server.heroku.com");
    }

    /**
     * Call on javascript function sayHello() in webView
     *
     * @param view //
     */
    public void buttonSayHello(View view) {
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("javascript:showAndroidToast(\"Hej\")");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Hej");
        jsInterface.showToast("hej");
    }
}
