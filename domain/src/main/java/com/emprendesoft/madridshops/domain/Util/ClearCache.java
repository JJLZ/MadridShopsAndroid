package com.emprendesoft.madridshops.domain.Util;

import android.content.Context;

import com.emprendesoft.madridshops.domain.activities.interactors.SetAllActivitesCachedInteractorImpl;
import com.emprendesoft.madridshops.domain.activities.interactors.SetAllActivitiesCachedInteractor;
import com.emprendesoft.madridshops.domain.shops.interactors.ClearCacheInteractor;
import com.emprendesoft.madridshops.domain.shops.interactors.ClearCacheInteractorImp;
import com.emprendesoft.madridshops.domain.shops.interactors.SetAllShopsAreCachedInteractor;
import com.emprendesoft.madridshops.domain.shops.interactors.SetAllShopsAreCachedInteractorImpl;
import com.emprendesoft.madridshops.domain.shops.managers.cache.ClearCacheManager;
import com.emprendesoft.madridshops.domain.shops.managers.cache.ClearCacheManagerDAOImp;

public class ClearCache
{
    public static void clearAllCache(final Context context)
    {
        ClearCacheManager clearCacheManager = new ClearCacheManagerDAOImp(context);
        ClearCacheInteractor clearCacheInteractor = new ClearCacheInteractorImp(clearCacheManager);
        clearCacheInteractor.execute(new Runnable() {
            @Override
            public void run() {
                SetAllShopsAreCachedInteractor setAllShopsAreCachedInteractor = new SetAllShopsAreCachedInteractorImpl(context);
                setAllShopsAreCachedInteractor.execute(false);

                SetAllActivitiesCachedInteractor setAllActivitiesCachedInteractor = new SetAllActivitesCachedInteractorImpl(context);
                setAllActivitiesCachedInteractor.execute(false);
            }
        });
    }
}
