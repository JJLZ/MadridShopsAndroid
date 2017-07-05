package com.emprendesoft.madridshops.domain.activities.interactors;

import com.emprendesoft.madridshops.domain.activities.model.Activities;

public interface SaveAllActivitiesIntoCacheInteractor {

    void execute(Activities activities, Runnable completion);
}
