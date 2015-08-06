package com.leadingedje.androidpushnotificationdemo;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Push notification registration support
 */
public class PushNotificationRegistration extends IntentService {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private static final String TAG = PushNotificationRegistration.class.getSimpleName();

    private static GoogleCloudMessaging gcm;

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    /**
     * Default constructor
     */
    public PushNotificationRegistration() {
        super(TAG);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If it
     * doesn't, display a dialog that allows users to download the APK from the
     * Google Play Store or enable it in the device's system settings.
     *
     * @param activity {@link Activity} checking for availability of Google Play
     *                 Services
     * @return boolean true if Google Play Services are available, false otherwise
     */
    public static boolean isGooglePlayServicesAvailable(Activity activity) {
        boolean success = true;
        try {
            int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
            if(resultCode != ConnectionResult.SUCCESS) {
                //---------------------------------------------------------------------
                // If error is recoverable, display the error dialog that will
                // give the user the opportunity to install or update Google Play Services
                if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    Log.e(TAG,
                          "isGooglePlayServicesAvailable(): Google Play Services not availabe.  Prompting user to recover");
                    GooglePlayServicesUtil.getErrorDialog(resultCode, activity, PLAY_SERVICES_RESOLUTION_REQUEST)
                            .show();
                }
                else {
                    //---------------------------------------------------------------------
                    // Google Play Services not supported...finish the activity
                    Log.e(TAG,
                          "isGooglePlayServicesAvailable(): Google Play Services are not supported on this device.");
                    activity.finish();
                }
                success = false;
            }
        }
        catch(Exception exc) {
            Log.e(TAG, "isGooglePlayServicesAvailable(): isGooglePlayServicesAvailable exception: " + exc);
            success = false;
        }
        return success;
    }

    /**
     * Handle requests to register with GCM.
     * @param intent {@link Intent}
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            //---------------------------------------------------------------------
            // Handle multiple registration requests sequentially
            synchronized (TAG) {
                //---------------------------------------------------------------------
                // Initially this call goes out to the network to retrieve the token, subsequent calls
                // are local.
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                                                   GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.i(TAG, "onHandleIntent(): GCM Registration Token: " + token);

                //---------------------------------------------------------------------
                // TODO: Implement this method to send any registration to your app's servers.
                // sendRegistrationToServer(token);

                //---------------------------------------------------------------------
                // Store a boolean to indicate that registration token was sent to the server
                sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
            }
        } catch (Exception e) {
            Log.d(TAG, "onHandleIntent(): Failed to complete GCM registration", e);
            //---------------------------------------------------------------------
            // Setting this flag to flag can be used to retry sending registration token
            sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
        }
        //---------------------------------------------------------------------
        // Notify UI that registration has completed
        Log.d(TAG, "onHandleIntent(): Notifying UI that GCM registration is complete");
        Intent registrationComplete = new Intent(REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Register for GCM
     * @param activity {@link Activity} that is registering
     */
    public static void register(Activity activity) {
        //---------------------------------------------------------------------
        // Start IntentService to register this application with GCM.
        Intent intent = new Intent(activity, PushNotificationRegistration.class);
        activity.startService(intent);
    }
}
