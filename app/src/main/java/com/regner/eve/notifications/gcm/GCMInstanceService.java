package com.regner.eve.notifications.gcm;

import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;
import com.regner.eve.notifications.util.Log;

import java.io.IOException;

public class GCMInstanceService extends InstanceIDListenerService {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        Log.i("onTokenRefresh");

        try {
            InstanceID.getInstance(this).deleteInstanceID();
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
        }
    }
}
