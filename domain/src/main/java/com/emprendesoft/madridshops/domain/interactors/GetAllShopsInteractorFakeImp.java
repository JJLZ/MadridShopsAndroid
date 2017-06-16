package com.emprendesoft.madridshops.domain.interactors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.emprendesoft.madridshops.domain.model.Shop;
import com.emprendesoft.madridshops.domain.model.Shops;

public class GetAllShopsInteractorFakeImp implements GetAllShopsInteractor {

    @Override
    public void execute(@NonNull GetAllShopsInteractorCompletion completion, @Nullable InteractorErrorCompletion onError) {

        Shops shops = new Shops();

        for (int i = 0; i < 10; i++) {

            Shop shop = Shop.of(i, "My shop " + i).setLogoUrl("https://www.jumblebee.co.uk/site/wp-content/uploads/2014/06/JB-FE-Shop_10.png");
            shops.add(shop);
        }

        if (completion != null) {
            completion.completion(shops);
        }
    }
}
