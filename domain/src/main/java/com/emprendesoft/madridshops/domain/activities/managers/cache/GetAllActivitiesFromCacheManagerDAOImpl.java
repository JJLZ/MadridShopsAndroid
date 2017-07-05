package com.emprendesoft.madridshops.domain.activities.managers.cache;

import android.content.Context;
import android.support.annotation.NonNull;

import com.emprendesoft.madridshops.domain.activities.managers.db.ActivityDAO;
import com.emprendesoft.madridshops.domain.activities.model.Activities;
import com.emprendesoft.madridshops.domain.activities.model.Activity;

import java.lang.ref.WeakReference;
import java.util.List;

public class GetAllActivitiesFromCacheManagerDAOImpl implements GetAllActivitiesFromCacheManager {

    private WeakReference<Context> mContextWeakReference;

    public GetAllActivitiesFromCacheManagerDAOImpl(Context contextWeakReference) {

        mContextWeakReference = new WeakReference<Context>(contextWeakReference);
    }

    @Override
    public void execute(@NonNull GetAllActivitiesFromCacheManagerCompletion completion)
    {
        ActivityDAO dao = new ActivityDAO(mContextWeakReference.get());
        List<Activity> activityList = dao.query();

        if (activityList == null) {
            return;
        }

        Activities activities = Activities.from(activityList);
        completion.completion(activities);
    }
}
