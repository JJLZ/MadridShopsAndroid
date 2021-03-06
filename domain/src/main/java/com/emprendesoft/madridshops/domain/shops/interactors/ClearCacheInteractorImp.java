package com.emprendesoft.madridshops.domain.shops.interactors;

import com.emprendesoft.madridshops.domain.shops.managers.cache.ClearCacheManager;

public class ClearCacheInteractorImp implements ClearCacheInteractor {

    private ClearCacheManager mManager;

    public ClearCacheInteractorImp(ClearCacheManager manager) {
        mManager = manager;
    }

    @Override
    public void execute(Runnable completion) {
        mManager.execute(completion);
    }
}
