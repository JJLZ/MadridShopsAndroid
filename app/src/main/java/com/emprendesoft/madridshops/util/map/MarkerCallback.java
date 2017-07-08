package com.emprendesoft.madridshops.util.map;

import android.content.Context;
import android.widget.ImageView;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MarkerCallback implements Callback
{
    Marker marker = null;
    String URL;
    ImageView userPhoto;
    Context context;

    MarkerCallback(Marker marker, String URL, ImageView userPhoto, Context context)
    {
        this.marker = marker;
        this.URL = URL;
        this.userPhoto = userPhoto;
        this.context = context;
    }

    @Override
    public void onError() {}

    @Override
    public void onSuccess()
    {
        if (marker != null && marker.isInfoWindowShown())
        {
            marker.hideInfoWindow();

            Picasso.with(context)
                    .load(URL)
                    .into(userPhoto);

            marker.showInfoWindow();
        }
    }
}