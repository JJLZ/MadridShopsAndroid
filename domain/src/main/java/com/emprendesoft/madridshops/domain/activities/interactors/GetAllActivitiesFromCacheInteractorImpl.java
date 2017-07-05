package com.emprendesoft.madridshops.domain.activities.interactors;

import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.activities.managers.cache.GetAllActivitiesFromCacheManager;
import com.emprendesoft.madridshops.domain.activities.managers.cache.GetAllActivitiesFromCacheManagerCompletion;
import com.emprendesoft.madridshops.domain.activities.model.Activities;

public class GetAllActivitiesFromCacheInteractorImpl implements GetAllActivitiesFromCacheInteractor {

    private GetAllActivitiesFromCacheManager cacheManager;

    public GetAllActivitiesFromCacheInteractorImpl(final GetAllActivitiesFromCacheManager cacheManager)
    {
        this.cacheManager = cacheManager;
    }

    @Override
    public void execute(@NonNull final GetAllActivitiesInteractorCompletion completion) {

        cacheManager.execute(new GetAllActivitiesFromCacheManagerCompletion() {
            @Override
            public void completion(@NonNull Activities activities) {
                completion.completion(activities);
            }
        });
    }
}
