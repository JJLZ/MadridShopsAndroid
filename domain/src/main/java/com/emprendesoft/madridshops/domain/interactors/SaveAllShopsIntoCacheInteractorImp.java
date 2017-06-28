package com.emprendesoft.madridshops.domain.interactors;

import com.emprendesoft.madridshops.domain.managers.cache.SaveAllShopsIntoCacheManager;
import com.emprendesoft.madridshops.domain.model.Shops;

public class SaveAllShopsIntoCacheInteractorImp implements SaveAllShopsIntoCacheInteractor {

    private SaveAllShopsIntoCacheManager mManager;

    public SaveAllShopsIntoCacheInteractorImp(SaveAllShopsIntoCacheManager manager) {
        mManager = manager;
    }

    @Override
    public void execute(Shops shops, Runnable completion) {

        mManager.execute(shops, completion);
    }
}
