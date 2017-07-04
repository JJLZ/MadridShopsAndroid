package com.emprendesoft.madridshops.domain.activities.network;

import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.activities.network.entities.ActivityEntity;

import java.util.List;


public interface GetAllActivitiesManagerCompletion {

    void completion(@NonNull final List<ActivityEntity> activitiesEntities);
}
