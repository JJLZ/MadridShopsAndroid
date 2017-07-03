package com.emprendesoft.madridshops.domain.shops.managers.cache;

import android.content.Context;
import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.shops.managers.db.ShopDAO;
import com.emprendesoft.madridshops.domain.shops.model.Shop;
import com.emprendesoft.madridshops.domain.shops.model.Shops;

import java.lang.ref.WeakReference;
import java.util.List;

public class GetAllShopsFromCacheManagerDAOImp implements GetAllShopsFromCacheManager {

    private WeakReference<Context> mContextWeakReference;

    public GetAllShopsFromCacheManagerDAOImp(Context contextWeakReference) {
        mContextWeakReference = new WeakReference<Context>(contextWeakReference);
    }

    @Override
    public void execute(@NonNull GetAllShopsFromCacheManagerCompletion completion) {

        ShopDAO dao = new ShopDAO(mContextWeakReference.get());
        List<Shop> shopList = dao.query();

        if (shopList == null) {
            return;
        }

        Shops shops = Shops.from(shopList);
        completion.completion(shops);
    }
}
