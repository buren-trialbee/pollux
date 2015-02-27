package pollux.trialbee.com.pollux;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by philip on 2015-02-26.
 */
public class ImageCallback implements Callback {
    private Bridge bridge;

    public ImageCallback(Bridge bridge){
        this.bridge = bridge;
    }

    @Override
    public void done(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        Uri photoFileUri = (Uri) extras.get(MediaStore.EXTRA_OUTPUT);
        bridge.sendImageBase64(getImageBase64(photoFileUri));
    }

    private String getImageBase64(Uri uri) {
        // Get photo file as bitMap
        Bitmap bm = BitmapFactory.decodeFile(String.valueOf(uri));

        // Convert bitmap to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}
