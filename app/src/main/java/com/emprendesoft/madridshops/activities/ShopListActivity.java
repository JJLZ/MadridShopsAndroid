package com.emprendesoft.madridshops.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractor;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractorCompletion;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractorFakeImp;
import com.emprendesoft.madridshops.domain.interactors.InteractorErrorCompletion;
import com.emprendesoft.madridshops.domain.model.Shops;
import com.emprendesoft.madridshops.fragments.ShopsFragment;

public class ShopListActivity extends AppCompatActivity {

    ShopsFragment shopsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        shopsFragment = (ShopsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_shop_list__fragment_shops);

        // obtain shop list
        GetAllShopsInteractor getAllShopsInteractor = new GetAllShopsInteractorFakeImp();
        getAllShopsInteractor.execute(
                new GetAllShopsInteractorCompletion() {
                    @Override
                    public void completion(Shops shops) {

                        shopsFragment.setShops(shops);
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