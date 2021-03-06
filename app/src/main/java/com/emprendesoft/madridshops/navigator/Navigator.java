package com.emprendesoft.madridshops.navigator;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.emprendesoft.madridactivities.activities.ActivityDetailActivity;
import com.emprendesoft.madridactivities.activities.ActivityListActivity;
import com.emprendesoft.madridshops.activities.MainActivity;
import com.emprendesoft.madridshops.activities.ShopDetailActivity;
import com.emprendesoft.madridshops.activities.ShopListActivity;
import com.emprendesoft.madridshops.domain.activities.model.Activity;
import com.emprendesoft.madridshops.domain.shops.model.Shop;

import static com.emprendesoft.madridshops.util.Constants.INTENT_ACTIVITY_DETAIL;
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

    public static Intent navigateFromActivityListActivityToActivityDetailActivity(@NonNull final ActivityListActivity activityListActivity, final Activity activity, final int position) {

        final Intent i = new Intent(activityListActivity, ActivityDetailActivity.class);

        i.putExtra(INTENT_ACTIVITY_DETAIL, activity);

        activityListActivity.startActivity(i);

        return i;
    }
}
