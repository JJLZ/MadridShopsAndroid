package com.emprendesoft.madridshops.domain.interactors;

public interface GetIfAllShopsAreCachedInteractor {

    void execute(Runnable onAllShopsAreCached, Runnable onAllShopsAreNotCached);
}
