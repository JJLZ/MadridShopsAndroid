package com.emprendesoft.madridshops.domain.activities.network.mappers;

import android.util.Log;

import com.emprendesoft.madridshops.domain.activities.model.Activities;
import com.emprendesoft.madridshops.domain.activities.model.Activity;
import com.emprendesoft.madridshops.domain.activities.network.entities.ActivityEntity;

import java.util.List;

public class ActivitiesEntityIntoActivitiesMapper {

    public static Activities map(final List<ActivityEntity> activityEntities) {

        Activities activities = new Activities();

        for (ActivityEntity activityEntity : activityEntities) {

            Activity activity = Activity.of(activityEntity.getId(), activityEntity.getName());

            activity.setDescription(activityEntity.getDescription_es());
            activity.setLatitude(getCorrectCoordinateComponent(activityEntity.getGps_lat()));
            activity.setLongitude(getCorrectCoordinateComponent(activityEntity.getGps_lon()));
            activity.setAddress(activityEntity.getAddress());
            activity.setUrl(activityEntity.getUrl());
            activity.setImageUrl(activityEntity.getImg());
            activity.setLogoUrl(activityEntity.getLogo_img());
            
            activities.add(activity);
        }

        return activities;
    }

    public static float getCorrectCoordinateComponent(String coordinateComponent) {

        // problem: JSON has this errors "-3.9018104,275"
        float coordinate = 0.0f;

        String s = coordinateComponent.replace(",", "");
        try {
            coordinate = Float.parseFloat(s);
        } catch (Exception e) {
            Log.d("ERROR CONVERTING", "Can't convert " + coordinateComponent);
        }

        return coordinate;
    }
}
