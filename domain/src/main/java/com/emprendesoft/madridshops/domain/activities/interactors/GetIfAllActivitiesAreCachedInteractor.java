package com.emprendesoft.madridshops.domain.activities.interactors;

public interface GetIfAllActivitiesAreCachedInteractor {

    void execute(Runnable onAllActiviesAreCached, Runnable onAllActivitiesAreNotCached);
}
