package com.leadingedje.androidpushnotificationdemo;

/**
 * Shared constants
 */
class Constants {
    /**
     * Prevent construction of this class
     */
    private Constants() {
    }

    /**
     * GCM registration constants
     */
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    /**
     * Intent extra keys
     */
    public static final String BIGTEXT_INTENT_EXTRA_KEY = "BigText";
    public static final String CONTENT_INTENT_EXTRA_KEY = "Content";
    public static final String TICKER_INTENT_EXTRA_KEY  = "Ticker";
    public static final String TITLE_INTENT_EXTRA_KEY   = "Title";
}
