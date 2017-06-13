package com.emprendesoft.madridshops.domain.model;

public interface ShopsUpdatable {

    void add(Shop shop);
    void delete(Shop shop);
    void update(Shop newShop, long index);
}
