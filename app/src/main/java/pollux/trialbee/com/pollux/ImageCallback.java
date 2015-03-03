package pollux.trialbee.com.pollux;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by philip on 2015-02-26.
 */
public class ImageCallback implements Callback {
    private Uri photoFileUri;
    private Bridge bridge;

    public ImageCallback(Bridge bridge, Uri photoFileUri){
        this.bridge = bridge;
        this.photoFileUri = photoFileUri;
    }

    @Override
    public void done(int requestCode, int resultCode, Intent data) {
        bridge.sendImageBase64(getImageBase64(photoFileUri));
    }

    private String getImageBase64(Uri uri) {
        // Get photo file as bitMap
        File photoFile = new File(uri.toString());
        String filePath = photoFile.getAbsolutePath();
        Bitmap bm = BitmapFactory.decodeFile(filePath);

        // Convert bitmap to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}
