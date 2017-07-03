package com.emprendesoft.madridshops.domain.shops.interactors;

import android.support.annotation.NonNull;

public interface GetAllShopsFromCacheInteractor {

    void execute(@NonNull final GetAllShopsInteractorCompletion completion);
}
