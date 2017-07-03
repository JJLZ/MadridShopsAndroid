package com.emprendesoft.madridshops.domain.shops.model;

import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

public class Shops implements ShopsIterable, ShopsUpdatable {

    private List<Shop> shops;

    public Shops() {

    }

    // lazy getter
    private List<Shop> getShops() {

        if (shops == null) {
            shops = new LinkedList<>();
        }

        return shops;
    }

    public static Shops from(@NonNull final List<Shop> shopList) {

        final Shops shops = new Shops();

        for (final Shop shop : shopList) {
            shops.add(shop);
        }

        return shops;
    }

    @Override
    public void add(Shop shop) {

        getShops().add(shop);
    }

    @Override
    public void delete(Shop shop) {

        getShops().remove(shop);
    }

    @Override
    public void update(Shop newShop, long index) {

        getShops().set((int) index, newShop);
    }

    @Override
    public long size() {

        return getShops().size();
    }

    @Override
    public Shop get(long index) {

        return getShops().get((int) index);
    }

    @Override
    public List<Shop> allShops() {

        List<Shop> listCopy = new LinkedList<>();

        for (Shop shop : getShops()) {

            listCopy.add(shop);
        }

        return listCopy;
    }
}
