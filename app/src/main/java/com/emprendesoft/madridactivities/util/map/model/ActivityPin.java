package com.emprendesoft.madridactivities.util.map.model;

import com.emprendesoft.madridshops.domain.activities.model.Activities;
import com.emprendesoft.madridshops.domain.activities.model.Activity;
import com.emprendesoft.madridshops.util.map.MapPinnable;

import java.util.ArrayList;
import java.util.List;

public class ActivityPin implements MapPinnable<Activity> {

    private Activity mActivity;

    public static List<MapPinnable> activityPinsFromActivities(Activities activities) {

        List<MapPinnable> activityPinList = new ArrayList<>();
        List<Activity> activityList = activities.allActivities();

        for (Activity activity : activityList) {
            ActivityPin ap = new ActivityPin(activity);
            activityPinList.add(ap);
        }

        return activityPinList;
    }

    public ActivityPin(Activity activity) {
        mActivity = activity;
    }

    @Override
    public float getLatitude() {
        return mActivity.getLatitude();
    }

    @Override
    public float getLongitude() {
        return mActivity.getLongitude();
    }

    @Override
    public String getPinDescription() {
        return mActivity.getDescription();
    }

    @Override
    public String getPinImageUrl() {
        return mActivity.getLogoUrl();
    }

    @Override
    public Activity getRelatedModelObject() {
        return mActivity;
    }
}
