package com.emprendesoft.madridshops.domain.shops.interactors;

import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.shops.managers.cache.GetAllShopsFromCacheManager;
import com.emprendesoft.madridshops.domain.shops.managers.cache.GetAllShopsFromCacheManagerCompletion;
import com.emprendesoft.madridshops.domain.shops.model.Shops;

public class GetAllShopsFromCacheInteractorImpl implements GetAllShopsFromCacheInteractor {

    private GetAllShopsFromCacheManager mCacheManager;

    public GetAllShopsFromCacheInteractorImpl(final GetAllShopsFromCacheManager cacheManager) {

        this.mCacheManager = cacheManager;
    }

    @Override
    public void execute(@NonNull final GetAllShopsInteractorCompletion completion) {

        mCacheManager.execute(new GetAllShopsFromCacheManagerCompletion() {
            @Override
            public void completion(@NonNull Shops shops) {
                completion.completion(shops);
            }
        });

    }
}
