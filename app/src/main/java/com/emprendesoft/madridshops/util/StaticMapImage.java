package com.emprendesoft.madridshops.util;

import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.activities.model.Activity;
import com.emprendesoft.madridshops.domain.shops.model.Shop;

public class StaticMapImage {

    public static String getMapImageUrl(@NonNull final Shop shop) {

        String strCoordinates = shop.getLatitude() + "," + shop.getLongitude();

        String url = "http://maps.googleapis.com/maps/api/staticmap?center=" + strCoordinates + "&zoom=17&size=320x220&scale=2&markers=%7Ccolor:0x9C7B14%7C" + strCoordinates;

        return url;
    }

    public static String getMapImageUrlFromActivity(@NonNull final Activity activity) {

        String strCoordinates = activity.getLatitude() + "," + activity.getLongitude();

        String url = "http://maps.googleapis.com/maps/api/staticmap?center=" + strCoordinates + "&zoom=17&size=320x220&scale=2&markers=%7Ccolor:0x9C7B14%7C" + strCoordinates;

        return url;
    }
}
