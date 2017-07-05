package com.emprendesoft.madridshops.domain.activities.managers.cache;

import android.content.Context;

import com.emprendesoft.madridshops.domain.activities.managers.db.ActivityDAO;
import com.emprendesoft.madridshops.domain.activities.model.Activities;
import com.emprendesoft.madridshops.domain.activities.model.Activity;

import java.lang.ref.WeakReference;

public class SaveAllActivitiesIntoCacheManagerDAOImpl implements SaveAllActivitiesIntoCacheManager {

    private WeakReference<Context> mContextWeakReference;

    public SaveAllActivitiesIntoCacheManagerDAOImpl(Context contextWeakReference)
    {
        mContextWeakReference = new WeakReference<Context>(contextWeakReference);
    }

    @Override
    public void execute(Activities activities, Runnable completion) {
        ActivityDAO dao = new ActivityDAO(mContextWeakReference.get());

        for (Activity activity : activities.allActivities()) {
            dao.insert(activity);
        }

        completion.run();
    }
}
