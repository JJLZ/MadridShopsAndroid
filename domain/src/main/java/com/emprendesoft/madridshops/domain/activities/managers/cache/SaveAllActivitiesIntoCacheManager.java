package com.emprendesoft.madridshops.domain.activities.managers.cache;

import com.emprendesoft.madridshops.domain.activities.model.Activities;

public interface SaveAllActivitiesIntoCacheManager {

    void execute(Activities activities, Runnable completion);
}
