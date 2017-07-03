package com.emprendesoft.madridshops.domain.shops.managers.cache;

import android.support.annotation.NonNull;

public interface GetAllShopsFromCacheManager {

    void execute(@NonNull final GetAllShopsFromCacheManagerCompletion completion);
}
