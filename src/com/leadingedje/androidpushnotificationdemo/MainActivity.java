package com.leadingedje.androidpushnotificationdemo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {
    
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        
        if ( PushNotificationRegistration.isGooglePlayServicesAvailable( this ) ) {
            // If Google Play Services are available, attempt to register
            // for push notifications
            Log.d( TAG, "onCreate(): Google Play Services are available" );
            PushNotificationRegistration.register( getApplicationContext(), this );
        } else {
            Log.e( TAG, "onCreate(): Google Play Services not available" );
        }        
        
        setContentView( R.layout.activity_main );

        if ( savedInstanceState == null ) {
            getFragmentManager().beginTransaction().add( R.id.container, new PlaceholderFragment() ).commit();
        }
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
