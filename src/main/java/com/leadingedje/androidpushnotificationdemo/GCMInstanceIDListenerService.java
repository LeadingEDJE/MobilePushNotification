package com.leadingedje.androidpushnotificationdemo;

import com.google.android.gms.iid.InstanceIDListenerService;

import android.content.Intent;
import android.util.Log;

/**
 * This service handles GCM registration token updates
 */
public class GCMInstanceIDListenerService extends InstanceIDListenerService {
    private static final String TAG = GCMInstanceIDListenerService.class.getSimpleName();

    /**
     * Called when the registration token is refreshed.
     * Calls the push notification registration service to get
     * a new registration token.
     */
    @Override
    public void onTokenRefresh() {
        Log.d(TAG, "onTokenRefresh(): Fetching updated GCM registration token");
        Intent intent = new Intent(this, PushNotificationRegistration.class);
        startService(intent);
    }
}
