package com.emprendesoft.madridshops.domain.shops.interactors;

import com.emprendesoft.madridshops.domain.shops.model.Shops;

public interface SaveAllShopsIntoCacheInteractor {

    void execute(Shops shops, Runnable completion);
}
