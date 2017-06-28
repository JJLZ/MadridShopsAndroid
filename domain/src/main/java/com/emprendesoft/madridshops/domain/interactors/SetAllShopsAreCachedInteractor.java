package com.emprendesoft.madridshops.domain.interactors;

public interface SetAllShopsAreCachedInteractor {

    public static final String SHOP_SAVED = "SHOP_SAVED";

    void execute(boolean shopsSaved);
}
