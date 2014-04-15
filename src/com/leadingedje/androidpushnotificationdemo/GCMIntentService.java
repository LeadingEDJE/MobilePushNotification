package com.leadingedje.androidpushnotificationdemo;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GCMBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GCMIntentService extends IntentService {
    private static final String TAG = GCMIntentService.class.getSimpleName();

    public static final int DEMO_NOTIFICATION_ID = 1;

    private NotificationManager notificationManager;

    NotificationCompat.Builder builder;

    public GCMIntentService() {
        super( "GCMIntentService" );
        Log.d( TAG, "GCMIntentService(): Started" );
    }

    /**
     * This handler is triggered when a push notification arrives
     * @param intent Intent containing notification information
     */
    @Override
    protected void onHandleIntent( Intent intent ) {
        Log.d( TAG, "onHandleIntent(): Started" );
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance( this );
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver 
        String messageType = gcm.getMessageType( intent );

        if ( !extras.isEmpty() ) { 
            /*
             * Filter messages based on message type. Since it is likely that
             * GCM will be extended in the future with new message types, just
             * ignore any message types you're not interested in, or that you
             * don't recognize.
             */
            if ( GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals( messageType ) ) {
                // Send notification of received message.
                Log.d( TAG, "onHandleIntent(): Received GCM message: " + extras.toString() );
                sendNotification( intent );
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GCMBroadcastReceiver.completeWakefulIntent( intent );
    }

    /**
     * Display a notification using the received message. The message contains a
     * JSON object with the notification data.
     * @param intent - Intent containing the push notification data
     */
    private void sendNotification( Intent intent ) {
        Log.d( TAG, "sendNotification(): Started" );
        notificationManager = (NotificationManager)this.getSystemService( Context.NOTIFICATION_SERVICE );
        
        // Bundle will contain contents of data section inside 
        // notification JSON data
        Bundle bundle = intent.getExtras();
        
        // Get notification content, use default values if keys don't exist
        String title = bundle.getString( "Title", "Default Title" );
        String bigText = bundle.getString( "BigText", "Default Big Text" );
        String contentText = bundle.getString( "ContentText", "Default Content Text" );
        String tickerText = bundle.getString( "TickerText", "Default Ticker Text" );

        // Build the notification
        Notification.Builder notificationBuilder = new Notification.Builder( this );
        notificationBuilder.setSmallIcon( R.drawable.ic_launcher )
                           .setContentTitle( title )
                           .setStyle( new Notification.BigTextStyle().bigText( bigText ) )
                           .setContentText( contentText )
                           .setLights( android.R.color.holo_green_light, 300, 1000 )
                           .setDefaults( Notification.DEFAULT_ALL )
                           .setTicker( tickerText );

        // Set the intent to go to when the notification is tapped
        Intent resultIntent = new Intent( this, MainActivity.class );
        notificationBuilder.setContentIntent( PendingIntent.getActivity( this, 0,
                                              resultIntent, PendingIntent.FLAG_ONE_SHOT ) );
        
        Log.d( TAG, "sendNotification(): Calling notify() to send the notification" );
        notificationManager.notify( DEMO_NOTIFICATION_ID, notificationBuilder.build() );
    }
}
