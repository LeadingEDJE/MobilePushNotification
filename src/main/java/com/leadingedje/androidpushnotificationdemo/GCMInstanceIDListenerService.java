package com.leadingedje.androidpushnotificationdemo;

import com.google.android.gms.iid.InstanceIDListenerService;

import android.content.Intent;
import android.util.Log;

/**
 * This service handles the creation, rotation and updating of
 * GCM registration tokens.
 */
public class GCMInstanceIDListenerService extends InstanceIDListenerService {
    private static final String TAG = GCMInstanceIDListenerService.class.getSimpleName();

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    @Override
    public void onTokenRefresh() {
        Log.d(TAG, "onTokenRefresh(): Fetching updated instance ID token");
        Intent intent = new Intent(this, PushNotificationRegistration.class);
        startService(intent);
    }
}
