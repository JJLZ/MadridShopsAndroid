package com.emprendesoft.madridshops.domain.interactors;

import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.model.Shops;

public interface GetAllShopsInteractorCompletion {

    void completion(@NonNull final Shops shops);
}
