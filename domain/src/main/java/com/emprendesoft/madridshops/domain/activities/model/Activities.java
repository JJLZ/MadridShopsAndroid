package com.emprendesoft.madridshops.domain.activities.model;

import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

public class Activities implements ActivitiesIterable, ActivitiesUpdatable {

    private List<Activity> activities;

    public Activities() {

    }

    private List<Activity> getActivities() {

        if (activities == null) {
            activities = new LinkedList<>();
        }

        return activities;
    }

    public static Activities from(@NonNull final List<Activity> activityList) {

        final Activities activities = new Activities();

        for (final Activity activity : activityList) {
            activities.add(activity);
        }

        return activities;
    }

    @Override
    public void add(Activity activity) {
        getActivities().add(activity);
    }

    @Override
    public void delete(Activity activity) {
        getActivities().remove(activity);
    }

    @Override
    public void update(Activity newActivity, long index) {
        getActivities().set((int) index, newActivity);
    }

    @Override
    public long size() {
        return getActivities().size();
    }

    @Override
    public Activity get(long index) {
        return getActivities().get((int) index);
    }

    @Override
    public List<Activity> allActivities() {

        List<Activity> listCopy = new LinkedList<>();

        for (Activity activity : getActivities()) {

            listCopy.add(activity);
        }

        return listCopy;
    }
}






























