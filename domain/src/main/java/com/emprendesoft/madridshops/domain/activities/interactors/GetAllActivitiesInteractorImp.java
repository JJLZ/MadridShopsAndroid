package com.emprendesoft.madridshops.domain.activities.interactors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.emprendesoft.madridshops.domain.activities.model.Activities;
import com.emprendesoft.madridshops.domain.activities.network.ANetworkManager;
import com.emprendesoft.madridshops.domain.activities.network.GetAllActivitiesManagerCompletion;
import com.emprendesoft.madridshops.domain.activities.network.entities.ActivityEntity;
import com.emprendesoft.madridshops.domain.activities.network.mappers.ActivitiesEntityIntoActivitiesMapper;
import com.emprendesoft.madridshops.domain.shops.interactors.InteractorErrorCompletion;
import com.emprendesoft.madridshops.domain.shops.managers.network.ManagerErrorCompletion;

import java.util.List;

public class GetAllActivitiesInteractorImp implements GetAllActivitesInteractor {

    private ANetworkManager mANetworkManager;

    public GetAllActivitiesInteractorImp(ANetworkManager ANetworkManager) {

        mANetworkManager = ANetworkManager;
    }

    @Override
    public void execute(@NonNull final GetAllActivitiesInteractorCompletion completion, @Nullable final InteractorErrorCompletion onError) {

        if (mANetworkManager == null) {

            if (onError == null) {
                throw new IllegalStateException("Network manager can't be null");
            } else {
                onError.onError("");
            }
        }

        mANetworkManager.getActivitiesFromServer(new GetAllActivitiesManagerCompletion() {
                                                     @Override
                                                     public void completion(@NonNull List<ActivityEntity> activityEntities) {

                                                         if (completion != null) {
                                                             Activities activities = ActivitiesEntityIntoActivitiesMapper.map(activityEntities);
                                                             completion.completion(activities);
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
