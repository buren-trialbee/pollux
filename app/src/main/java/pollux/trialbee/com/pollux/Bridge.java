package pollux.trialbee.com.pollux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by dauvid on 2015-02-26.
 */
public class Bridge {
    private Context context;
    private MainActivity mActivity;
    private WebViewDataSender webViewDataSender;

    public Bridge(Context context) {
        this.context = context;
        mActivity = (MainActivity) context;
        webViewDataSender = new WebViewDataSender(this, (WebView) mActivity.findViewById(R.id.webView));
    }

    public void requestImage() {
        Intent imageIntent = IntentFactory.createImageIntent(context);
        Uri photoFileUri = (Uri) imageIntent.getExtras().get(MediaStore.EXTRA_OUTPUT);
        ImageCallback imageCallback = new ImageCallback(this, photoFileUri);
        mActivity.processIntent(imageIntent, imageCallback);
    }

    public void sendImageBase64(String image){
        webViewDataSender.addImageBase64(image);
    }

    public void showToast(String toast) {
        Toast.makeText(mActivity, toast, Toast.LENGTH_SHORT).show();
    }
}
