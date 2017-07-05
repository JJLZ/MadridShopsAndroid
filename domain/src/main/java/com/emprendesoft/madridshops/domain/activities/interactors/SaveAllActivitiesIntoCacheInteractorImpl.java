package com.emprendesoft.madridshops.domain.activities.interactors;

import com.emprendesoft.madridshops.domain.activities.managers.cache.SaveAllActivitiesIntoCacheManager;
import com.emprendesoft.madridshops.domain.activities.model.Activities;

public class SaveAllActivitiesIntoCacheInteractorImpl implements SaveAllActivitiesIntoCacheInteractor {

    private SaveAllActivitiesIntoCacheManager manager;

    public SaveAllActivitiesIntoCacheInteractorImpl(SaveAllActivitiesIntoCacheManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(Activities activities, Runnable completion) {
        manager.execute(activities, completion);
    }
}
