package com.emprendesoft.madridshops.domain.shops.managers.cache;

import com.emprendesoft.madridshops.domain.shops.model.Shops;

public interface SaveAllShopsIntoCacheManager {

    void execute(Shops shops, Runnable completion);
}
