package com.emprendesoft.madridshops.domain.activities.interactors;

import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.activities.model.Activities;

public interface GetAllActivitiesInteractorCompletion {

    void completion(@NonNull final Activities activities);
}
