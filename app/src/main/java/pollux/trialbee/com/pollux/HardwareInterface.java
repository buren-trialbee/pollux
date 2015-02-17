package pollux.trialbee.com.pollux;

import android.content.Context;

/**
 * Created by dauvid on 2015-02-17.
 */
public interface HardwareInterface {
    public void requestImage(Context context, int requestCode);
    public String getImage();

}
