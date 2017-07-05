package com.emprendesoft.madridshops.domain.shops.managers.cache;

import android.content.Context;

import com.emprendesoft.madridshops.domain.activities.managers.db.ActivityDAO;
import com.emprendesoft.madridshops.domain.shops.managers.db.ShopDAO;

import java.lang.ref.WeakReference;

public class ClearCacheManagerDAOImp implements ClearCacheManager {

    private WeakReference<Context> mContextWeakReference;

    public ClearCacheManagerDAOImp(Context contextWeakReference) {
        mContextWeakReference = new WeakReference<Context>(contextWeakReference);
    }

    @Override
    public void execute(Runnable completion) {
        ShopDAO dao = new ShopDAO(mContextWeakReference.get());
        dao.deleteAll();

        ActivityDAO activityDAO = new ActivityDAO(mContextWeakReference.get());
        activityDAO.deleteAll();

        completion.run();
    }
}
