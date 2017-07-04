package com.emprendesoft.madridshops.domain.activities.interactors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.emprendesoft.madridshops.domain.shops.interactors.InteractorErrorCompletion;

public interface GetAllActivitesInteractor {

    public void execute(@NonNull final GetAllActivitiesInteractorCompletion completion, @Nullable final InteractorErrorCompletion onError);
}
