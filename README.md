MobilePushNotification
======================

This is the demo app for my presentation, "Implementing Push Notification Support in your Android App". The presentation slidedeck is inside the file, Implementing Push Notification in Android.pptx.

Use the steps below to import, build and run this project after cloning this repository. 

**NOTE:** Google Play services must be installed in your device or emulator to run this project. See the presentation slidedeck for additional details.

After cloning this repo and checking out the ThatConference2015 branch, do the following:

## Android Studio
1. Start Android Studio
2. Select Open an existing Android Studio project from Quick Start
3. Browse to the directory where you cloned the repository and select build.gradle
4. Click on OK when prompted to Import Project from Gradle
5. If prompted to Open Project, click on Delete Existing Project and Import to complete the import
6. If prompted to about Language Level Change, click on Yes to reload the project
7. Click on the Run button in the toolbar or select Run 'MobilePushNotification' from the Run menu to build and run the app

## IntelliJ IDEA
1. Start IntelliJ IDEA
2. Select Import Project from Welcome to IntelliJ IDEA
3. Browse to the directory where you cloned the repository and select build.gradle
4. Click on OK when prompted to Import Project from Gradle
5. Click on the Run button in the toolbar or select Run 'MobilePushNotification' from the Run menu to build and run the app

## Sending a Push Notification to the this App ##
The following JSON below can be used within your REST API client (HDC, Postman) to send a notification to this app.
Replace the device registration token below with your device registration token.

    {
        "registration_ids": [
            "fYvmQ9dDHZE:APA91bEXnLShdJ8NnQT7CoIK7Q0ZBJadBspRg7hs1Q6EfIDMWBTdFsLMo9SHuRnQ9RuTzRAyl6cYlxS5PT7D6m8o_JXAPWYMO6_nv1eXBpbAT7tSgfOOs7CQUSkmHwvVOyTzRpe83dUM"
        ],
        "data": {
            "Title": "Phone Title",
            "BigText": "Phone Big",
            "ContentText": "Phone Content",
            "TickerText": "Phone Ticker"
        }
    }
    
The URL to use is: https://android.googleapis.com/gcm/send

The HTTP headers to use are below:
 - Authorization: key=Your Google Server API key
 - Content-Type: application/json