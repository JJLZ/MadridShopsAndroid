package com.emprendesoft.madridshops.domain.shops.managers.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface NetworkManager {

    void getShopsFromServer(@NonNull final GetAllShopsManagerCompletion completion,
                            @Nullable final ManagerErrorCompletion errorCompletion);
}
