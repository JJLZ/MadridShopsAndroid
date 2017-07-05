package com.emprendesoft.madridshops.domain.activities.managers.cache;

import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.activities.model.Activities;

public interface GetAllActivitiesFromCacheManagerCompletion {

    void completion(@NonNull final Activities activities);
}
