package com.emprendesoft.madridshops.domain.activities.interactors;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;

public class SetAllActivitesCachedInteractorImpl implements SetAllActivitiesCachedInteractor {

    private WeakReference<Context> context;

    public SetAllActivitesCachedInteractorImpl(Context context) {

        this.context = new WeakReference<>(context);
    }

    @Override
    public void execute(boolean activitiesSaved) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.get());
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(SetAllActivitesCachedInteractorImpl.ACTIVITIES_SAVED, activitiesSaved);

        editor.commit();
    }
}
