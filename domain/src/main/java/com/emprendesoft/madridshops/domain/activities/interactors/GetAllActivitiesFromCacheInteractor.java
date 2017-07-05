package com.emprendesoft.madridshops.domain.activities.interactors;

import android.support.annotation.NonNull;

public interface GetAllActivitiesFromCacheInteractor {
    void execute(@NonNull final GetAllActivitiesInteractorCompletion completion);
}
