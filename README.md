MobilePushNotification
======================

This is the demo app for my presentation, "Implementing Push Notification Support in your Android App". The presentation slidedeck is inside the file, Implementing Push Notification in Android.pptx.

Use the steps below to import, build and run this project into your IDE after cloning this repository. 

**NOTE:** Google Play services must be installed in your device or emulator to run this project. See the presentation slidedeck for additional details.

## Eclipse
1. From the File menu, select Import...
2. Select "Existing Projects into Workspace"
3. Under Options, select "Search for nested projects".
4. Select "Select root directory:" and browse to the directory where you cloned the repository. The following two projects should be listed under Projects: AndroidPushNotificationDemo and google-play-services_lib
5. Click on Finish to complete the import
6. If you get "The project cannot be built until build path errors are resolved" errors after the import, select Clean... from the Project menu and clean the imported projects. This will force a rebuild and will resolve the errors.
7. To run the app, select Run Configurations... from the Run menu and select AndroidPushNotificationDemo underneath Android Application

## Android Studio and IntelliJ IDEA
1. Checkout the branch, gradle_migration_2014-10-08.
2. Select Import Project... from Quick Start or from the File menu.
2. Browse to the directory where you cloned the repository and select build.gradle
3. To build and run the app, select Run 'MobilePushNotification' from the Run menu 
