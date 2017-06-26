package com.emprendesoft.madridshops.domain.interactors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.emprendesoft.madridshops.domain.managers.network.GetAllShopsManagerCompletion;
import com.emprendesoft.madridshops.domain.managers.network.ManagerErrorCompletion;
import com.emprendesoft.madridshops.domain.managers.network.NetworkManager;
import com.emprendesoft.madridshops.domain.managers.network.entities.ShopEntity;
import com.emprendesoft.madridshops.domain.managers.network.mappers.ShopsEntityIntoShopsMapper;
import com.emprendesoft.madridshops.domain.model.Shops;

import java.util.List;

public class GetAllShopsInteractorImp implements GetAllShopsInteractor {

    private NetworkManager networkManager;

    public GetAllShopsInteractorImp(NetworkManager networkManager) {

        this.networkManager = networkManager;
    }

    @Override
    public void execute(@NonNull final GetAllShopsInteractorCompletion completion, @Nullable final InteractorErrorCompletion onError) {

        if (this.networkManager == null) {

            if (onError == null) {
                throw new IllegalStateException("Network manager can't be null");
            } else {
                onError.onError("");
            }
        }

        this.networkManager.getShopsFromServer(new GetAllShopsManagerCompletion() {
                                                   @Override
                                                   public void completion(@NonNull List<ShopEntity> shopEntities) {
                                                       Log.d("SHOP", shopEntities.toString());
                                                       if (completion != null) {
                                                           Shops shops = ShopsEntityIntoShopsMapper.map(shopEntities);
                                                           completion.completion(shops);
                                                       }

                                                   }
                                               }, new ManagerErrorCompletion() {
                                                   @Override
                                                   public void onError(String errorDescription) {

                                                       if (onError != null) {
                                                           onError.onError(errorDescription);
                                                       }
                                                   }
                                               }

        );
    }

}
