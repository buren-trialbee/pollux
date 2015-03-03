package pollux.trialbee.com.pollux;

import android.content.Intent;

/**
 * Created by philip on 2015-03-03.
 */
public class StartBluetoothCallback implements Callback {
    private Bridge bridge;
    public StartBluetoothCallback(Bridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public void done(int requestCode, int resultCode, Intent data) {
        bridge.discoverBluetoothDevices();
    }
}
