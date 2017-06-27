package com.emprendesoft.madridshops.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractor;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractorCompletion;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractorImp;
import com.emprendesoft.madridshops.domain.interactors.InteractorErrorCompletion;
import com.emprendesoft.madridshops.domain.managers.network.GetAllShopsManagerImpl;
import com.emprendesoft.madridshops.domain.managers.network.NetworkManager;
import com.emprendesoft.madridshops.domain.model.Shop;
import com.emprendesoft.madridshops.domain.model.Shops;
import com.emprendesoft.madridshops.fragments.ShopsFragment;
import com.emprendesoft.madridshops.navigator.Navigator;
import com.emprendesoft.madridshops.views.OnElementClick;

public class ShopListActivity extends AppCompatActivity {

    ShopsFragment shopsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        shopsFragment = (ShopsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_shop_list__fragment_shops);

        // obtain shop list
        NetworkManager manager = new GetAllShopsManagerImpl(this);
        GetAllShopsInteractor getAllShopsInteractor = new GetAllShopsInteractorImp(manager);
        getAllShopsInteractor.execute(
                new GetAllShopsInteractorCompletion() {
                    @Override
                    public void completion(Shops shops) {

                        shopsFragment.setShops(shops);

                        shopsFragment.setOnElementClickListener(new OnElementClick<Shop>() {
                            @Override
                            public void clickedOn(@NonNull Shop element, int position) {

                                Navigator.navigateFromShopListActivityToShopDetailActivity(ShopListActivity.this, element, position);
                            }
                        });
                    }
                },
                new InteractorErrorCompletion() {
                    @Override
                    public void onError(String errorDescription) {

                    }
                }
        );
    }
}