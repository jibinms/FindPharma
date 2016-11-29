# FindPharma

FindPharma  is an app for finding pharmacies on a location. It will list out registered pharmacies and app will provide an en-route to the pharmacy location.
App also can provide all the medicines available. User can search through this medicine list. And find out the pharmacies where it is available.

##Firebase Setup

Create a new Firebase Project with Package Name as wear.sunshine.android.example.com.capstone_1.
Replace the google-services.json file obtained after creation of project in the root directory of the app module.

##Build Instructions (Mac OS X)

1. Clone the repository 
2. Install Java
3. Install Android Studio with Android SDK Tools
4. Setup Firebase keys using this [link] (https://firebase.google.com/docs/cloud-messaging/android/client)
5. Build the app using ./gradlew clean build
6. In order to install the debug version of the app, run ./gradlew installDebug
7. In order to install the release version of the app, run ./gradlew installRelease
