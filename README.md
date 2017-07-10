# MadridShops For Android
### Presentation:
This is the practice for Advanced Android Programming Course of the [KeepCoding® Startup Bootcamp Engineering Master IV](https://keepcoding.io/en/).

### Objective:
The objective is for the student to put into practice the knowledge acquired during the course Advanced Android. The main objective is to put into practice the knowledge acquired during the course such as: persistence of data using SQLite, downloading and decoding information contained in JSON format asynchronously using Volley and related classes. Produce high quality, maintainable and reliable code. Clean architecture would be valued: interactors, managers, etc.

![](https://github.com/JJLZ/MadridShopsAndroid/blob/master/01.png)
![](https://github.com/JJLZ/MadridShopsAndroid/blob/master/02.png)
![](https://github.com/JJLZ/MadridShopsAndroid/blob/master/03.png)

### App Description:
Create a mobile application to display information of activities and shops in Madrid, even when the user has no Internet connection. Activities and shops should be shown in a Google Maps.

### Requirements:
1. When starting the App for the first time, if there's Internet connection it will download all information from the activities and shops access point including all images.
2. The App will cache everything locally: images, data, etc. Even images of the maps.
3. Next time the App is started, if more than 7 days has passed the cache will be invalidated (deleted) and everything will be downloaded again.
4. If there's no Internet connection a message will be shown to the user.
5. The app will have a main menu screen where we'll add two buttons & a logo. The buttons takes us to the list of activities and shops.
7. The list of shops/activities will show in the upper 50% screen a map with one pin for each shop/activity.
8. The list of shops/activities will show in the lower 50% screen. Logo to the left and name in the front. Tapping a row takes us to the detail shop screen.
9. If you tap on a pin in the map a callout will open with the logo & name of the shop/activity. Taping the callout takes us to the detail screen.
10. The map will be always centered in madrid, showing also the user location.
11. All data is at least in Spanish & English: should cache all and display in Spanish (if that's our phone's language) or English otherwise.
12. Shop/Activity detail screen should show name, description, address and a map showing the location without any pin.

### Used tools:
* Android Stuido 2.3.3
* Java
* Dependencies: Picasso v2.5.2, ButterKnife v8.6.0, Multidex v1.0.1, Volley v1.0.0, Gson v2.8.1, Google Maps (play-services-maps v11.0.1), okhttp v2.4.0
* Git
* Sketch v45.1
* Web Services:
GET http://madrid-shops.com/json_new/getShops.php
GET http://madrid-shops.com/json_new/getActivities.php

