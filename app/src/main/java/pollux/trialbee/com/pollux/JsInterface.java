package pollux.trialbee.com.pollux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by dauvid on 2015-02-03.
 */
public class JsInterface {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Context mContext;
    Activity mActivity;
    private File photoFile;

    /**
     * Instantiate the interface and set the context
     */
    JsInterface(Context context) {
        mContext = context;
        mActivity = (Activity) context;

        // Instantiate a camera handler
//        cameraHandler = new CameraHandler(context);
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {
        // The code that's supposed to be here
//        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {
//           mActivity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }

        // Using this function as requestImage instead, until javaScript-function is added
        requestImage();
    }

    @JavascriptInterface
    public void requestImage() {
        // Create the intent for capturing an image
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {

            // Create the File where the photo should go
            String fileName = "tempPhoto";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            try {
                photoFile = File.createTempFile(fileName, ".jpg", storageDir);
                Toast.makeText(mContext, photoFile.toString(), Toast.LENGTH_SHORT).show();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));

                // Start camera activity and store resulting image in external storage
                mActivity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }
}




