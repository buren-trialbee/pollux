package pollux.trialbee.com.pollux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;



/**
 * Created by dauvid on 2015-02-17.
 */
public class AndroidHardware implements HardwareInterface {
    public File photoFile;


    public void requestImage(Context context, int requestCode) {
        // Create the intent for capturing an image
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            String fileName = "tempPhoto";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            try {
                photoFile = File.createTempFile(fileName, ".jpg", storageDir);
//                Toast.makeText(mContext, photoFile.toString(), Toast.LENGTH_SHORT).show();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));

                // Start camera activity and store resulting image in external storage
                ((Activity) context).startActivityForResult(takePictureIntent, requestCode);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }
    public String getImage() {
        // Get photo file as bitMap
        Bitmap bm = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

        // Convert bitmap to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        // Convert byte array to base64
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encodedImage;
    }
}
