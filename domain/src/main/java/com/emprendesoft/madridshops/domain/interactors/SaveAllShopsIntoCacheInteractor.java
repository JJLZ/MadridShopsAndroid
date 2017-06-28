package com.emprendesoft.madridshops.domain.interactors;

import com.emprendesoft.madridshops.domain.model.Shops;

public interface SaveAllShopsIntoCacheInteractor {

    void execute(Shops shops, Runnable completion);
}
