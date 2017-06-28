package com.emprendesoft.madridshops.domain.managers.cache;

import android.content.Context;

import com.emprendesoft.madridshops.domain.managers.db.ShopDAO;

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
        completion.run();
    }
}
