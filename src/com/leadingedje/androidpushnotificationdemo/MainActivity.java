package com.leadingedje.androidpushnotificationdemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
//        if ( savedInstanceState == null ) {
//            getFragmentManager().beginTransaction().add( R.id.container, new PlaceholderFragment() ).commit();
//        }
        if ( PushNotificationRegistration.isGooglePlayServicesAvailable( this ) ) {
            // If Google Play Services are available, attempt to register
            // for push notifications
            Log.d( TAG, "onCreate(): Google Play Services are available" );
            PushNotificationRegistration.register( getApplicationContext(), this );
        } else {
            Log.e( TAG, "onCreate(): Google Play Services not available" );
        }
        getAndDisplayNotificationContent( getIntent() );
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if ( PushNotificationRegistration.isGooglePlayServicesAvailable( this ) ) {
            Log.d( TAG, "onResume(): Google Play Services are available" );
        } else {
            Log.e( TAG, "onResume(): Google Play Services not available" );
        }        
    }
    
    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy(): Started" );
        super.onDestroy();
    }    

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if ( id == R.id.action_settings ) {
            return true;
        }
        return super.onOptionsItemSelected( item );
    }
    
    /**
     * Overriding this allows the mobile app to start a fragment
     * when a multiple alarm or message notificaiton is tapped
     * @param intent Intent with action set to the fragment to load
     */
    @Override
    protected void onNewIntent( Intent intent ) {
        getAndDisplayNotificationContent( intent );
    }
    
    /**
     * Get the notification content and display it
     */
    private void getAndDisplayNotificationContent( Intent intent ) {
        Bundle extras = intent.getExtras();
        if ( extras != null && !extras.isEmpty() ) {
            TextView tv = (TextView)findViewById(R.id.titleTextView);
            tv.setText( extras.getString( Constants.TITLE_INTENT_EXTRA_KEY ) );
            tv = (TextView)findViewById(R.id.tickerTextView);
            tv.setText( extras.getString( Constants.TICKER_INTENT_EXTRA_KEY ) );
            tv = (TextView)findViewById(R.id.bigtextTextView);
            tv.setText( extras.getString( Constants.BIGTEXT_INTENT_EXTRA_KEY ) );
            tv = (TextView)findViewById(R.id.contentTextView);
            tv.setText( extras.getString( Constants.CONTENT_INTENT_EXTRA_KEY ) );
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
            View rootView = inflater.inflate( R.layout.fragment_main, container, false );
            return rootView;
        }
    }

}
