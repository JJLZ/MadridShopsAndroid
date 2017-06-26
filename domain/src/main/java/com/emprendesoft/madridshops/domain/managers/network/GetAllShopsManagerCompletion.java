package com.emprendesoft.madridshops.domain.managers.network;

import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.managers.network.entities.ShopEntity;

import java.util.List;

public interface GetAllShopsManagerCompletion {

    void completion(@NonNull final List<ShopEntity> shopEntities);
}
