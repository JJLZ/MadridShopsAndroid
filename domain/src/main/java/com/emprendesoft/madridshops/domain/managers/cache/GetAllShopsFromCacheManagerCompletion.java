package com.emprendesoft.madridshops.domain.managers.cache;

import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.model.Shops;

public interface GetAllShopsFromCacheManagerCompletion {

    void completion(@NonNull final Shops shops);
}
