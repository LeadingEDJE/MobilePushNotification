package com.leadingedje.androidpushnotificationdemo;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * This {@link GCMListenerService} implementation does the actual handling of the GCM message.
 */
public class GCMListenerService extends GcmListenerService {
    private static final String TAG = GCMListenerService.class.getSimpleName();

    public static final int DEMO_NOTIFICATION_ID = 1;

    /**
     * This handler is triggered when a push notification arrives
     * @param senderId {@link String} SenderID of the message sender
     * @param messageBundle {@link Bundle} containing the incoming GCM message
     */
    @Override
    public void onMessageReceived(String senderId, Bundle messageBundle) {
        Log.d(TAG, "onMessageReceived(): Received message from " + senderId);

        // Send notification of received message.
        sendNotification(messageBundle);
    }

    /**
     * Display a notification using the received message. The message contains a
     * JSON object with the notification data.
     * @param messageBundle {@link Bundle} containing the incoming GCM message
     */
    private void sendNotification(Bundle messageBundle) {
        Log.d(TAG, "sendNotification(): Started");
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // Get notification content, use default values if keys don't exist
        String title = messageBundle.getString("Title", "Default Title");
        String bigText = messageBundle.getString("BigText", "Default Big Text");
        String contentText = messageBundle.getString("ContentText", "Default Content Text");
        String tickerText = messageBundle.getString("TickerText", "Default Ticker Text");

        // Build the notification.
        // See http://developer.android.com/reference/android/app/Notification.Builder.html
        Notification.Builder notificationBuilder = new Notification.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.lelogo).setContentTitle(title)
                .setStyle(new Notification.BigTextStyle().bigText(bigText)).setContentText(contentText)
                .setLights(android.R.color.holo_green_light, 300, 1000).setDefaults(Notification.DEFAULT_ALL)
                .setTicker(tickerText);

        // Create the intent to go to when the notification is tapped.
        Intent resultIntent = new Intent(this, MainActivity.class);

        // Put the notification data into the intent as extras
        resultIntent.putExtra(Constants.BIGTEXT_INTENT_EXTRA_KEY, bigText);
        resultIntent.putExtra(Constants.CONTENT_INTENT_EXTRA_KEY, contentText);
        resultIntent.putExtra(Constants.TICKER_INTENT_EXTRA_KEY, tickerText);
        resultIntent.putExtra(Constants.TITLE_INTENT_EXTRA_KEY, title);

        // This activity will become the start of a new task on this history stack
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Setting SINGLE_TOP flag will cause onNewIntent to be called when a notification is tapped 
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // Set the notification content into the notification builder using the
        // result intent set up above
        notificationBuilder
                .setContentIntent(PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT));

        // Send the notification to display it on the device
        Log.d(TAG, "sendNotification(): Calling notify() to send the notification");
        notificationManager.notify(DEMO_NOTIFICATION_ID, notificationBuilder.build());
    }
}
