package com.emprendesoft.madridshops.domain.activities.interactors;

public interface SetAllActivitiesCachedInteractor {

    String ACTIVITIES_SAVED = "ACTIVITIES_SAVED";

    void execute(boolean ActivitiesSaved);
}
