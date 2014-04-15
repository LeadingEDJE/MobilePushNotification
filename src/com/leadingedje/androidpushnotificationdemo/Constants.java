package com.leadingedje.androidpushnotificationdemo;

/**
 * Shared constants
 */
public class Constants {
    /**
     * Prevent construction of this class
     */
    private Constants() {
    }

    /**
     * Substitute you own sender ID here. This is the project number you got from the Google API Console
     */
    public static final String SENDER_ID = "378047010653";
    
    /**
     * Shared preferences name
     */
    public static final String SHARED_PREF_NAME = "androidpushnotificationdemo";
    
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
}
