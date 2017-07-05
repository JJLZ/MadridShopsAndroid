package com.emprendesoft.madridshops.domain.activities.managers.cache;

import android.support.annotation.NonNull;

public interface GetAllActivitiesFromCacheManager {

    void execute(@NonNull final GetAllActivitiesFromCacheManagerCompletion completion);
}
