package com.emprendesoft.madridshops.domain.shops.interactors;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;

public class SetAllShopsAreCachedInteractorImpl implements SetAllShopsAreCachedInteractor {

    private WeakReference<Context> context;

    public SetAllShopsAreCachedInteractorImpl(Context context) {

        this.context = new WeakReference<Context>(context);
    }

    @Override
    public void execute(boolean shopsSaved) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.get());
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(SetAllShopsAreCachedInteractorImpl.SHOP_SAVED, shopsSaved);

        editor.commit();
    }
}
