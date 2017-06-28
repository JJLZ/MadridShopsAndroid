package com.emprendesoft.madridshops.domain.managers.cache;

import com.emprendesoft.madridshops.domain.model.Shops;

public interface SaveAllShopsIntoCacheManager {

    void execute(Shops shops, Runnable completion);
}
