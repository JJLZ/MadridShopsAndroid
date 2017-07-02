package com.emprendesoft.madridshops.navigator;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.emprendesoft.madridactivities.activities.ActivityListActivity;
import com.emprendesoft.madridshops.activities.MainActivity;
import com.emprendesoft.madridshops.activities.ShopDetailActivity;
import com.emprendesoft.madridshops.activities.ShopListActivity;
import com.emprendesoft.madridshops.domain.model.Shop;

import static com.emprendesoft.madridshops.util.Constants.INTENT_SHOP_DETAIL;

public class Navigator {

    public static Intent navigateFromMainActivityToShopListActivity(@NonNull final MainActivity mainActivity) {

        assert(mainActivity != null);

        final Intent i = new Intent(mainActivity, ShopListActivity.class);
        mainActivity.startActivity(i);

        return i;
    }

    public static Intent navigateFromShopListActivityToShopDetailActivity(@NonNull final ShopListActivity shopListActivity, final Shop shop, final int position) {

        final Intent i = new Intent(shopListActivity, ShopDetailActivity.class);

        i.putExtra(INTENT_SHOP_DETAIL, shop);

        shopListActivity.startActivity(i);

        return i;
    }

    public static Intent navigateFromMainActivityToActivityListActivity(@NonNull final MainActivity mainActivity) {

        assert (mainActivity != null);

        final Intent intent = new Intent(mainActivity, ActivityListActivity.class);
        mainActivity.startActivity(intent);

        return intent;
    }
}
