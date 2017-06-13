package com.emprendesoft.madridshops.domain.model;

import java.util.List;

public interface ShopsIterable {

    long size();
    Shop get(long index);
    List<Shop> allShops();
}
