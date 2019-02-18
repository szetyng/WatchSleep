# WatchSleep
## Contents
- `app` folder: for the mobile phone application
- `WatchSleepOnWear` folder: for the WearOS application

## Notes
The WearOS application for CaterPillar. It constantly monitors the accelerometer readings from the user's smartwatch and sends it to the phone application to be processed. Note: to receive notifications, can just extend it from the phone app.

Todo:
- monitor readings in ambient mode
- automatically start the monitoring near bedtime, rather than fully depend on users to press the `Sleep` button
- when in getting data mode, which should be in ambient mode, allow user to click on `Wake Up` button. What about automatic way?

Soon:
- would heart rate be useful?

## Progress Update
- click a button on the wearable to start listening for accelerometer data
- sends accelerometer data as an array to the phone with every 200 readings, and phone will display it on the screen

## Development
On Mac:
- Enable Developer Mode in both phone and watch
- On watch: enable debugging via Bluetooth and ADB
- On phone: enable debugging via Bluetooth in the WearOS app
- To get ADB on Mac: `brew cask install android-platform-tools`
```
adb forward tcp:4444 localabstract:/adb-hub
adb connect 127.0.0.1:4444
adb devices
```


