package com.emprendesoft.madridshops.domain.shops.managers.cache;

import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.shops.model.Shops;

public interface GetAllShopsFromCacheManagerCompletion {

    void completion(@NonNull final Shops shops);
}
