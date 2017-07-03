package com.emprendesoft.madridshops.domain.shops.managers.cache;

import android.content.Context;

import com.emprendesoft.madridshops.domain.shops.managers.db.ShopDAO;
import com.emprendesoft.madridshops.domain.shops.model.Shop;
import com.emprendesoft.madridshops.domain.shops.model.Shops;

import java.lang.ref.WeakReference;

public class SaveAllShopsIntoCacheManagerDAOImp implements SaveAllShopsIntoCacheManager {

    private WeakReference<Context> mContextWeakReference;

    public SaveAllShopsIntoCacheManagerDAOImp(Context contextWeakReference) {
        mContextWeakReference = new WeakReference<Context>(contextWeakReference);
    }

    @Override
    public void execute(Shops shops, Runnable completion) {

        ShopDAO dao = new ShopDAO(mContextWeakReference.get());

        for (Shop shop : shops.allShops()) {

            dao.insert(shop);
        }

        completion.run();
    }
}
