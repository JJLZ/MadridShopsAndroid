package com.emprendesoft.madridshops.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.interactors.GetIfAllShopsAreCachedInteractor;
import com.emprendesoft.madridshops.domain.interactors.GetIfAllShopsAreCachedInteractorImpl;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractor;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractorCompletion;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractorImp;
import com.emprendesoft.madridshops.domain.interactors.InteractorErrorCompletion;
import com.emprendesoft.madridshops.domain.interactors.SetAllShopsAreCachedInteractor;
import com.emprendesoft.madridshops.domain.interactors.SetAllShopsAreCachedInteractorImpl;
import com.emprendesoft.madridshops.domain.managers.network.GetAllShopsManagerImpl;
import com.emprendesoft.madridshops.domain.managers.network.NetworkManager;
import com.emprendesoft.madridshops.domain.model.Shop;
import com.emprendesoft.madridshops.domain.model.Shops;
import com.emprendesoft.madridshops.fragments.ShopsFragment;
import com.emprendesoft.madridshops.navigator.Navigator;
import com.emprendesoft.madridshops.views.OnElementClick;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopListActivity extends AppCompatActivity {

    @BindView(R.id.activity_shop_list__progress_bar)
    ProgressBar mProgressBar;


    ShopsFragment shopsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        ButterKnife.bind(this);

        shopsFragment = (ShopsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_shop_list__fragment_shops);

        GetIfAllShopsAreCachedInteractor getIfAllShopsAreCachedInteractor = new GetIfAllShopsAreCachedInteractorImpl(this);
        getIfAllShopsAreCachedInteractor.execute(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // all cached already, no need to download things, just read from DB
                                                }
                                            }, new Runnable() {
                                                @Override
                                                public void run() {
                                                    // nothing cached yet
                                                    obtainShopList();
                                                }
                                            }

        );
    }

    private void obtainShopList() {

        mProgressBar.setVisibility(View.VISIBLE);

        NetworkManager manager = new GetAllShopsManagerImpl(this);
        GetAllShopsInteractor getAllShopsInteractor = new GetAllShopsInteractorImp(manager);
        getAllShopsInteractor.execute(
                new GetAllShopsInteractorCompletion() {
                    @Override
                    public void completion(Shops shops) {

                        mProgressBar.setVisibility(View.GONE);

                        // TODO: persist in cache all shops

                        SetAllShopsAreCachedInteractor setAllShopsAreCachedInteractor = new SetAllShopsAreCachedInteractorImpl(getBaseContext());
                        setAllShopsAreCachedInteractor.execute(true);

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
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
        );
    }
}