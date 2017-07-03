package com.emprendesoft.madridshops.domain.shops.interactors;

import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.shops.model.Shops;

public interface GetAllShopsInteractorCompletion {

    void completion(@NonNull final Shops shops);
}
