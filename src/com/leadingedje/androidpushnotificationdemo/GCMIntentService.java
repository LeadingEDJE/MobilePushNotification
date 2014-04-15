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
     * 
     * @param intent Intent containing notification information
     */
    @Override
    protected void onHandleIntent( Intent intent ) {
        Log.d( TAG, "onHandleIntent(): Started" );
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance( this );
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType( intent );

        if ( !extras.isEmpty() ) { // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that
             * GCM will be extended in the future with new message types, just
             * ignore any message types you're not interested in, or that you
             * don't recognize.
             */
            if ( GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals( messageType ) ) {
                Log.d( TAG, "onHandleIntent(): Received type send error: " + extras.toString() );
            } else if ( GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals( messageType ) ) {
                Log.d( TAG, "onHandleIntent(): Received type deleted: " + extras.toString() );
            } else if ( GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals( messageType ) ) {
                // Post notification of received message.
                Log.d( TAG, "onHandleIntent(): Received type message: " + extras.toString() );
                sendNotification( intent );
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GCMBroadcastReceiver.completeWakefulIntent( intent );
    }

    /**
     * Override handling of intent service creation
     */
    @Override
    public void onCreate() {
        Log.d( TAG, "onCreate(): Started" );
        super.onCreate();
    }

    /**
     * Override handling of intent service removal
     */
    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy(): Started" );
        super.onDestroy();
    }

    /**
     * Display a notification using the received message. The message contains a
     * JSON object with the notification data.
     * 
     * @param intent - Intent containing the push notification data
     */
    private void sendNotification( Intent intent ) {
        Log.d( TAG, "sendNotification(): Started" );
        notificationManager = (NotificationManager)this.getSystemService( Context.NOTIFICATION_SERVICE );

        
        // TODO: Need to parse and display the notification here
        // Bundle bundle = intent.getExtras();
        Notification.Builder mBuilder = new Notification.Builder( this );
        mBuilder.setSmallIcon( R.drawable.ic_launcher )
                .setContentTitle( "Title string goes here" )
                .setStyle( new Notification.BigTextStyle().bigText( "Big text goes here" ) )
                .setContentText( "Content text goes here" )
                .setLights( android.R.color.holo_green_light, 300, 1000 )
                .setDefaults( Notification.DEFAULT_ALL )
                .setTicker( "Ticker text goes here" );

        // TODO: Set up the intent for the notification
        Intent backIntent = new Intent( this, MainActivity.class );
        backIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        Intent resultIntent = new Intent( this, MainActivity.class );
        
        // TODO: Need to choose one of these
        mBuilder.setContentIntent( PendingIntent.getActivity( this, 0,
                                   resultIntent, PendingIntent.FLAG_ONE_SHOT ) );
        mBuilder.setContentIntent( PendingIntent.getActivities( this, 0,
                                   new Intent[] { backIntent, resultIntent },
                                   PendingIntent.FLAG_ONE_SHOT ) );
        
        Log.d( TAG, "sendNotification(): Calling notify() to send the notification" );
        Notification n = mBuilder.build();
        notificationManager.notify( DEMO_NOTIFICATION_ID, n );
    }
}
