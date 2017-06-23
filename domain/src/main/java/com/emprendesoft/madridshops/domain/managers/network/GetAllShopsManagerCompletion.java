package com.emprendesoft.madridshops.domain.managers.network;

import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.model.Shops;

public interface GetAllShopsManagerCompletion {

    // TODO: quitar shops de aquí
    void completion(@NonNull final Shops shops);
}
