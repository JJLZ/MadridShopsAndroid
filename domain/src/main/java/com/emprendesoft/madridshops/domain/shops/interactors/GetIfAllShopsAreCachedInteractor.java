package com.emprendesoft.madridshops.domain.shops.interactors;

public interface GetIfAllShopsAreCachedInteractor {

    void execute(Runnable onAllShopsAreCached, Runnable onAllShopsAreNotCached);
}
