package com.emprendesoft.madridshops.domain.activities.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.emprendesoft.madridshops.domain.shops.managers.network.ManagerErrorCompletion;

public interface ANetworkManager {

    void getActivitiesFromServer(@NonNull final GetAllActivitiesManagerCompletion completion, @Nullable final ManagerErrorCompletion errorCompletion);
}
