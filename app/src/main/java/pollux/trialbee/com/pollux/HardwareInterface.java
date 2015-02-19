package pollux.trialbee.com.pollux;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dauvid on 2015-02-17.
 */
public interface HardwareInterface {
    public void requestImage(int requestCode);
    public String getImageBase64();
    public String getAPIVersion();
    public Boolean hasSystemFeature(String feature);
    public String getDeviceInfo() throws JSONException;
}
