package com.emprendesoft.madridshops.domain.managers.network.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopsResponseEntity {

    @SerializedName("result") private List<ShopEntity> result;

    public List<ShopEntity> getResult() {
        return result;
    }
}
