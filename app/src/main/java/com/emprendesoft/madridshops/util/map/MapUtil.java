package com.emprendesoft.madridshops.util.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.emprendesoft.madridshops.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MapUtil
{
    public static void centerMapInPosition(GoogleMap googleMap, double latitude, double longitude)
    {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(latitude, longitude)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public static void addPins(final List<MapPinnable> mapPinnables, final GoogleMap googleMap, final Context context)
    {
        if (mapPinnables == null || googleMap == null || context == null)
        {
            return;
        }

        for (final MapPinnable pinnable : mapPinnables)
        {
            final LatLng position = new LatLng(pinnable.getLatitude(), pinnable.getLongitude());
            final String imageUrl = pinnable.getPinImageUrl();

            final MarkerOptions marker = new MarkerOptions().position(position).title(pinnable.getPinDescription()).snippet(imageUrl);

            Marker m = googleMap.addMarker(marker);
            m.setTag(pinnable.getRelatedModelObject());
        }

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
        {
            @Override
            public View getInfoWindow(Marker marker)
            {
                return null;    // Use default InfoWindow frame
            }

            @Override
            public View getInfoContents(Marker marker)
            {
                LayoutInflater inflater = LayoutInflater.from(context);

                View view = inflater.inflate(R.layout.callout_layout, null);

                TextView textView = (TextView) view.findViewById(R.id.callout_layout__title);
                textView.setText(marker.getTitle());

                final ImageView imageView = (ImageView) view.findViewById(R.id.callout_layout__logo);
                final String url = marker.getSnippet();

                //-- TODO: cached images --
                Picasso.with(context).load(url).placeholder(R.drawable.shop_placeholder).networkPolicy(NetworkPolicy.OFFLINE).into(imageView);
                //--

                return view;
            }
        });
    }
}






























