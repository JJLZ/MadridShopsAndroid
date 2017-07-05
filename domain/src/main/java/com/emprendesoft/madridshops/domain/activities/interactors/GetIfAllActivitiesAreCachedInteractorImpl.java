package com.emprendesoft.madridshops.domain.activities.interactors;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;

public class GetIfAllActivitiesAreCachedInteractorImpl implements GetIfAllActivitiesAreCachedInteractor {

    private WeakReference<Context> context;

    public GetIfAllActivitiesAreCachedInteractorImpl(Context context) {

        this.context = new WeakReference<>(context);
    }

    @Override
    public void execute(Runnable onAllActiviesAreCached, Runnable onAllActivitiesAreNotCached) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.get());
        boolean activitySaved = preferences.getBoolean(SetAllActivitiesCachedInteractor.ACTIVITIES_SAVED, false);

        if (activitySaved) {
            onAllActiviesAreCached.run();
        } else {
            onAllActivitiesAreNotCached.run();
        }
    }
}
