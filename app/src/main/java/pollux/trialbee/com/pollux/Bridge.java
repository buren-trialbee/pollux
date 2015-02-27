package pollux.trialbee.com.pollux;

import android.content.Context;
import android.widget.Toast;

import java.io.File;

/**
 * Created by dauvid on 2015-02-26.
 */
public class Bridge {
    private Context context;
    private MainActivity mActivity;
    private File photoFile;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_ENABLE_BT = 2;
    private WebViewDataSender webViewDataSender;

    public Bridge(Context context) {
        this.context = context;
        mActivity = (MainActivity) context;
        webViewDataSender = new WebViewDataSender(context);
    }

    public void requestImage() {
        mActivity.processIntent(IntentFactory.createImageIntent(context), REQUEST_IMAGE_CAPTURE, new ImageCallback(this));
        // Create the intent for capturing an image
       /* Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = AndroidHardware.requestImageFile();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(photoFile));

            // Start camera activity and store resulting image in external storage
            ((Activity) context).startActivityForResult(takePictureIntent, requestCode);
        }*/
    }

    public void sendImageBase64(String image){
        webViewDataSender.addImageBase64(image);
    }

    public void showToast(String toast) {
        Toast.makeText(mActivity, toast, Toast.LENGTH_SHORT).show();
    }
}
