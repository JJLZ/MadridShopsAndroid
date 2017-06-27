package com.emprendesoft.madridshops.util;

import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.model.Shop;

public class StaticMapImage {

    public static String getMapImageUrl(@NonNull final Shop shop) {

        String strCoordinates = shop.getLatitude() + "," + shop.getLongitude();

        String url = "http://maps.googleapis.com/maps/api/staticmap?center=" + strCoordinates + "&zoom=17&size=320x220&scale=2&markers=%7Ccolor:0x9C7B14%7C" + strCoordinates;

        return url;
    }
}
