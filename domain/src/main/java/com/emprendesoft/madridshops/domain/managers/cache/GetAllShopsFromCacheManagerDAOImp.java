package com.emprendesoft.madridshops.domain.managers.cache;

import android.content.Context;
import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.managers.db.ShopDAO;
import com.emprendesoft.madridshops.domain.model.Shop;
import com.emprendesoft.madridshops.domain.model.Shops;

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
