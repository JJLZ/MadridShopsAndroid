package com.emprendesoft.madridshops.domain.activities.model;

import java.util.List;

public interface ActivitiesIterable {

    long size();
    Activity get(long index);
    List<Activity> allActivities();
}
